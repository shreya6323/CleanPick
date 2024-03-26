// App.js
import React from 'react';
// import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// import Navbar from './Navbar';
import SearchBar from './SearchBar';
import ImageUploader from './ImageUploader';
// Import other components
import ScrollToTopButton from './ScrollToTopButton';
function House() {
  return (
   <>

        <SearchBar />
        
        <ImageUploader />
        <ScrollToTopButton scrollThreshold={1000} /> 
   
   </>
  );
}

export default House;
