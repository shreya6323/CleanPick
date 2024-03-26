import React from 'react';
import './ProductCard.css'; // Import the CSS file
import ScoreCircle from './ScoreCircle';




function getColors(score) {
  const hue = ((10 - score) * 120) / 9;
  const color = `hsl(${hue}, 100%, 50%)`;
  return color;
}

const ProductCard = ({ product }) => {
  return (

<><div className="card-container">
      <div className="name">{product.productName}</div>
    <div className='score'> <ScoreCircle score={product.overallScore} color={getColors(product.overallScore)}  className={"big"} /></div> 
     
        
      
    { Object.entries(product.ingredientScores).map(([ingredient, score]) => (
        <div key={ingredient} className="ingredient-card">
          <div className="ingredient-info">
            <ScoreCircle score={score} color={getColors(score)} className={"small"} />
            <strong>{ingredient}</strong> 
          </div>
        </div>
      ))}
      
        </div>
      {/* Add more product details as needed */}
      </>  
  );
};

export default ProductCard;
