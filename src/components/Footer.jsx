import React from 'react';
import './Footer.css';

const Footer = () => {
  return (
    <footer>
      <div className='footer'>
        <span className="social-icons">
          {/* Add your social media icons here, you can use font awesome or custom images */}
          <a href="#" className="social-icon"><i className="fab fa-facebook"></i></a>
          <a href="#" className="social-icon"><i className="fab fa-twitter"></i></a>
          <a href="#" className="social-icon"><i className="fab fa-instagram"></i></a>
          {/* Add more social media icons as needed */}
        </span>
        <span className="footer-text">
          <p className="all-rights">
          <i class="far fa-copyright"></i> 2024 cleanpick</p>
          <p className="made-with-love">
            Made with <i className="fas fa-heart heart-icon"></i> by Shreya Gandhi
          </p>
        </span>
      </div>
    </footer>
  );
};

export default Footer;
