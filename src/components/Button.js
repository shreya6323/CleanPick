// Button.jsx
import React from 'react';
import './Button.css';
const Button = ({ onClick, label }) => {
  return (
    <button onClick={onClick} className="button">
      {label}
    </button>
  );
};

export default Button;
