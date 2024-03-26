// ScoreCircle.js

import React from 'react';
import './ScoreCircle.css';


const ScoreCircle = ({ score, color, className }) => {
  return (
    <div className= {className}style={{
    
      backgroundColor: color,
     
    }}>
      {score}
    </div>
  );
};

export default ScoreCircle;
