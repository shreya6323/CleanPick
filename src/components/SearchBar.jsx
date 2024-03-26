import React, { useState } from 'react';
import './SearchBar.css'; // Import your CSS file for styling
import Button from './Button';



const SearchBar = ({ onSearch }) => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };


  const handleSearchSubmit = (e) => {
    e.preventDefault();
    // Pass the search term to the parent component or perform the search logic
    onSearch(searchTerm);
  };

  return (
    <div className="search-bar-container">
      <form onSubmit={handleSearchSubmit}>
        <input
          type="text"
          placeholder="Search..."
          value={searchTerm}
          onChange={handleSearchChange}
        />
        
        <Button  label="Search" />
      </form>
    </div>
  );
 };

export default SearchBar;
