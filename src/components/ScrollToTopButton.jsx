import React, { useState, useEffect } from 'react';
import './Scroll.css';
function ScrollToTopButton({ scrollThreshold }) {
  const [showButton, setShowButton] = useState(false);

  // Function to handle scrolling to the top of the page
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth" // Smooth scrolling behavior
    });
  };

  // Event listener to check if the user has scrolled down to a certain point to show the button
  const handleScroll = () => {
    if (window.pageYOffset > scrollThreshold) {
      setShowButton(true);
    } else {
      setShowButton(false);
    }
  };

  // Add event listener when component mounts
  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <>
      {/* Button to navigate to the top */}
      {showButton && (
        <button className="scroll-to-top-button" onClick={scrollToTop}>
         <i class="fas fa-arrow-up"></i>
        </button>
      )}
    </>
  );
}

export default ScrollToTopButton;
