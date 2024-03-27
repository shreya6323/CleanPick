import React from 'react';
import './Loader.css'; // Import your CSS file for styling
import loaderGif from '../assets/Loader-unscreen.gif'; // Import your GIF file

const Loader = () => {
  return (

    <div className="loader-container">
      <img src={loaderGif} alt="Loader" className="loader-image" />
    </div>

  );
}

export default Loader;
