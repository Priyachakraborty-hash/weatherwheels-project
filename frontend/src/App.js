import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { 
  FiSun, FiCloud, FiCloudRain, FiWind, FiDroplet,
  FiShoppingCart, FiStar, FiTruck, FiFilter,
  FiChevronRight, FiMapPin, FiThermometer
} from 'react-icons/fi';

import axios from 'axios';
import toast, { Toaster } from 'react-hot-toast';
import './App.css';

// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// Weather Widget Component
const WeatherWidget = ({ location = "New York" }) => {
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchWeather();
  }, [location]);

  const fetchWeather = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/weather/current/${location}`);
      setWeather(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Weather fetch error:', error);
      // Mock data for demo
      setWeather({
        temperature: 22,
        condition: "Sunny",
        humidity: 65,
        windSpeed: 12,
        precipitation: 0,
        agricultureAdvice: "Perfect day for planting! Consider seeders and tillers."
      });
      setLoading(false);
    }
  };

  const getWeatherIcon = (condition) => {
    switch(condition?.toLowerCase()) {
      case 'sunny': return <FiSun className="weather-icon sun" />;
      case 'cloudy': return <FiCloud className="weather-icon cloud" />;
      case 'rainy': return <FiCloudRain className="weather-icon rain" />;
      default: return <FiSun className="weather-icon sun" />;
    }
  };

  if (loading) return <div className="weather-skeleton"></div>;

  return (
    <motion.div 
      className="weather-widget"
      initial={{ opacity: 0, y: -20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="weather-header">
        <div className="weather-location">
          <FiMapPin className="location-icon" />
          <span>{location}</span>
        </div>
        <div className="weather-main">
          {getWeatherIcon(weather?.condition)}
          <div className="weather-temp">
            <span className="temp-value">{Math.round(weather?.temperature || 22)}</span>
            <span className="temp-unit">°C</span>
          </div>
        </div>
      </div>
      
      <div className="weather-stats">
        <div className="weather-stat">
          <FiDroplet className="stat-icon" />
          <div>
            <span className="stat-value">{weather?.humidity || 65}%</span>
            <span className="stat-label">Humidity</span>
          </div>
        </div>
        <div className="weather-stat">
          <FiWind className="stat-icon" />
          <div>
            <span className="stat-value">{weather?.windSpeed || 12} km/h</span>
            <span className="stat-label">Wind</span>
          </div>
        </div>
      </div>
      
      <div className="weather-advice">
        <p>{weather?.agricultureAdvice || "Good conditions for farming!"}</p>
      </div>
    </motion.div>
  );
};

// Product Card Component
const ProductCard = ({ product, onAddToCart }) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <motion.div
      className="product-card"
      whileHover={{ y: -5, scale: 1.02 }}
      onHoverStart={() => setIsHovered(true)}
      onHoverEnd={() => setIsHovered(false)}
    >
      <div className="product-image-container">
        <img 
          src={product.imageUrl || `https://source.unsplash.com/400x300/?tractor,${product.category}`} 
          alt={product.n}
          className="product-image"
        />
        {product.recommendedSeasons && (
          <div className="product-seasons">
            {product.recommendedSeasons.map(season => (
              <span key={season} className={`season-tag season-${season.toLowerCase()}`}>
                {season}
              </span>
            ))}
          </div>
        )}
      </div>
      
      <div className="product-content">
        <h3 className="product-n">{product.n}</h3>
        <p className="product-description">{product.description}</p>
        
        <div className="product-features">
          {product.features?.slice(0, 3).map((feature, index) => (
            <span key={index} className="feature-tag">
              {feature}
            </span>
          ))}
        </div>
        
        <div className="product-footer">
          <div className="product-price">
            <span className="price-currency">$</span>
            <span className="price-value">{product.price?.toLocaleString() || '45,000'}</span>
          </div>
          
          <div className="product-rating">
            <FiStar className="star-icon filled" />
            <span>{product.rating || 4.5}</span>
          </div>
        </div>
        
        <motion.button
          className="add-to-cart-btn"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
          onClick={() => onAddToCart(product)}
        >
          <FiShoppingCart />
          <span>Add to Cart</span>
        </motion.button>
      </div>
    </motion.div>
  );
};

// Main App Component
function App() {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCart, setShowCart] = useState(false);

  // Mock product data for demo
  const mockProducts = [
    {
      id: 1,
      n: "John Deere 5075E Tractor",
      description: "Heavy-duty tractor perfect for all seasons",
      category: "TRACTORS",
      price: 45000,
      rating: 4.7,
      imageUrl: "https://images.unsplash.com/photo-1516253593875-bd7ba052fbc5?w=800",
      features: ["75HP Engine", "4WD", "Power Steering"],
      recommendedSeasons: ["SPRING", "SUMMER", "AUTUMN"]
    },
    {
      id: 2,
      n: "Case IH Axial-Flow 250",
      description: "High-capacity combine harvester for maximum efficiency",
      category: "HARVESTERS",
      price: 450000,
      rating: 4.9,
      imageUrl: "https://images.unsplash.com/photo-1500937386664-56d1dfef3854?w=800",
      features: ["GPS Guidance", "Yield Monitoring", "Auto-Steering"],
      recommendedSeasons: ["SUMMER", "AUTUMN"]
    },
    {
      id: 3,
      n: "Valley 8000 Series Pivot",
      description: "Advanced center pivot irrigation system",
      category: "IRRIGATION",
      price: 150000,
      rating: 4.8,
      imageUrl: "https://images.unsplash.com/photo-1625246333195-78d9c38ad449?w=800",
      features: ["Remote Control", "Variable Rate", "GPS Positioning"],
      recommendedSeasons: ["SUMMER"]
    },
    {
      id: 4,
      n: "Great Plains 3S-4000HD",
      description: "Heavy-duty drill seeder for precision planting",
      category: "SEEDERS",
      price: 85000,
      rating: 4.6,
      imageUrl: "https://images.unsplash.com/photo-1574943320303-6e1415f7c14e?w=800",
      features: ["40ft Width", "Precision Seeding", "Variable Rate"],
      recommendedSeasons: ["SPRING"]
    },
    {
      id: 5,
      n: "Kuhn Krause Excelerator",
      description: "Vertical tillage tool for soil preparation",
      category: "TILLERS",
      price: 75000,
      rating: 4.5,
      imageUrl: "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=800",
      features: ["30ft Width", "Hydraulic Pressure", "Star Wheel"],
      recommendedSeasons: ["SPRING", "AUTUMN"]
    },
    {
      id: 6,
      n: "Apache AS1250 Sprayer",
      description: "Self-propelled sprayer for crop protection",
      category: "SPRAYERS",
      price: 280000,
      rating: 4.7,
      imageUrl: "https://images.unsplash.com/photo-1520238861346-a49993df0e69?w=800",
      features: ["1200 Gallon Tank", "120ft Boom", "GPS Guidance"],
      recommendedSeasons: ["SPRING", "SUMMER"]
    }
  ];

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    filterProducts();
  }, [selectedCategory, products]);

  const fetchProducts = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/products`);
      setProducts(response.data);
      setFilteredProducts(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Products fetch error:', error);
      // Use mock data for demo
      setProducts(mockProducts);
      setFilteredProducts(mockProducts);
      setLoading(false);
    }
  };

  const filterProducts = () => {
    if (selectedCategory === 'ALL') {
      setFilteredProducts(products);
    } else {
      const filtered = products.filter(p => p.category === selectedCategory);
      setFilteredProducts(filtered);
    }
  };

  const handleAddToCart = (product) => {
    setCart([...cart, product]);
    toast.success(`${product.n} added to cart!`, {
      style: {
        background: 'var(--gradient-field)',
        color: 'white',
        fontWeight: 'bold'
      }
    });
  };

  const categories = [
  { n: 'ALL',        icon: <FiFilter />,      color: '#FFD93D' },
  { n: 'TRACTORS',   icon: <FiTruck />,       color: '#FF6B35' },
  { n: 'HARVESTERS', icon: <FiCloudRain />,   color: '#00D9A3' },
  { n: 'SEEDERS',    icon: <FiSun />,         color: '#4285F4' },
  { n: 'IRRIGATION', icon: <FiDroplet />,     color: '#FF6EBE' },
  { n: 'TILLERS',    icon: <FiWind />,        color: '#A855F7' }
];


  return (
    <div className="app">
      <Toaster position="top-right" />
      
      {/* Animated Background Elements */}
      <div className="bg-elements">
        <div className="bg-circle circle-1"></div>
        <div className="bg-circle circle-2"></div>
        <div className="bg-circle circle-3"></div>
      </div>

      {/* Header */}
      <header className="header">
        <div className="header-content">
          <motion.div 
            className="logo"
            initial={{ rotate: -180, scale: 0 }}
            animate={{ rotate: 0, scale: 1 }}
            transition={{ duration: 0.8, type: "spring" }}
          >
            <FiTruck className="logo-icon" />
            <div className="logo-text">
              <h1>WeatherWheels</h1>
              <p>Smart Farm Equipment</p>
            </div>
          </motion.div>

          <nav className="nav">
            <a href="#products" className="nav-link">Products</a>
            <a href="#weather" className="nav-link">Weather</a>
            <a href="#recommendations" className="nav-link">AI Recommendations</a>
            <a href="#about" className="nav-link">About</a>
          </nav>

          <motion.button 
            className="cart-button"
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            onClick={() => setShowCart(!showCart)}
          >
            <FiShoppingCart />
            {cart.length > 0 && (
              <span className="cart-count">{cart.length}</span>
            )}
          </motion.button>
        </div>
      </header>

      {/* Hero Section */}
      <section className="hero">
        <motion.div 
          className="hero-content"
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
        >
          <h2 className="hero-title">
            Smart Equipment for
            <span className="gradient-text"> Modern Farming</span>
          </h2>
          <p className="hero-subtitle">
            AI-powered recommendations based on real-time weather forecasting
          </p>
          
          <div className="hero-features">
            <div className="hero-feature">
              <FiSun className="feature-icon" />
              <span>Weather-Based Selection</span>
            </div>
            <div className="hero-feature">
              <FiTruck className="feature-icon" />
              <span>Fast Delivery</span>
            </div>
            <div className="hero-feature">
              <FiStar className="feature-icon" />
              <span>Expert Reviews</span>
            </div>
          </div>
        </motion.div>

        <div className="hero-weather">
          <WeatherWidget location="Iowa" />
        </div>
      </section>

      {/* Category Filter */}
      <section className="categories">
        <h3 className="section-title">Browse by Category</h3>
        <div className="category-grid">
          {categories.map((category) => (
            <motion.button
              key={category.n}
              className={`category-card ${selectedCategory === category.n ? 'active' : ''}`}
              style={{ '--category-color': category.color }}
              whileHover={{ scale: 1.05, rotate: 2 }}
              whileTap={{ scale: 0.95 }}
              onClick={() => setSelectedCategory(category.n)}
            >
              <div className="category-icon">{category.icon}</div>
              <span className="category-n">{category.n}</span>
            </motion.button>
          ))}
        </div>
      </section>

      {/* Products Grid */}
      <section className="products" id="products">
        <div className="section-header">
          <h3 className="section-title">Featured Equipment</h3>
          <div className="filter-info">
            Showing {filteredProducts.length} products
          </div>
        </div>
        
        {loading ? (
          <div className="loading-container">
            <div className="loader"></div>
          </div>
        ) : (
          <motion.div 
            className="products-grid"
            layout
          >
            <AnimatePresence>
              {filteredProducts.map((product) => (
                <motion.div
                  key={product.id}
                  layout
                  initial={{ opacity: 0, scale: 0.8 }}
                  animate={{ opacity: 1, scale: 1 }}
                  exit={{ opacity: 0, scale: 0.8 }}
                  transition={{ duration: 0.3 }}
                >
                  <ProductCard 
                    product={product} 
                    onAddToCart={handleAddToCart}
                  />
                </motion.div>
              ))}
            </AnimatePresence>
          </motion.div>
        )}
      </section>

      {/* Cart Sidebar */}
      <AnimatePresence>
        {showCart && (
          <motion.div 
            className="cart-sidebar"
            initial={{ x: 400 }}
            animate={{ x: 0 }}
            exit={{ x: 400 }}
            transition={{ type: "spring", stiffness: 300 }}
          >
            <div className="cart-header">
              <h3>Shopping Cart ({cart.length})</h3>
              <button onClick={() => setShowCart(false)}>×</button>
            </div>
            <div className="cart-items">
              {cart.map((item, index) => (
                <div key={index} className="cart-item">
                  <span>{item.n}</span>
                  <span>${item.price?.toLocaleString()}</span>
                </div>
              ))}
            </div>
            {cart.length > 0 && (
              <div className="cart-footer">
                <div className="cart-total">
                  Total: ${cart.reduce((sum, item) => sum + (item.price || 0), 0).toLocaleString()}
                </div>
                <button className="checkout-btn">Proceed to Checkout</button>
              </div>
            )}
          </motion.div>
        )}
      </AnimatePresence>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-content">
          <div className="footer-section">
            <h4>About WeatherWheels</h4>
            <p>Your smart partner in agricultural equipment selection</p>
          </div>
          <div className="footer-section">
            <h4>Quick Links</h4>
            <a href="#products">Products</a>
            <a href="#weather">Weather</a>
            <a href="#about">About</a>
          </div>
          <div className="footer-section">
            <h4>Contact</h4>
            <p>support@weatherwheels.com</p>
            <p>1-800-FARM-NOW</p>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© 2024 WeatherWheels. Smart Farming, Better Future.</p>
        </div>
      </footer>
    </div>
  );
}

export default App;
