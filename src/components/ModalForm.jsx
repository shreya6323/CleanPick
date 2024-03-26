// import React from 'react';
// import './ModalForm.css';
// import { useState } from 'react';
// import Button from './Button';
// const ModalForm = ({ isOpen, onClose }) => {
//     const [productName, setProductName] = useState('');

//     const handleProductNameChange = (e) => {
//         setProductName(e.target.value);
//       };

//       const handleConfirmProduct = () => {
//         // Perform any necessary actions with the product name
//         console.log('Product Name:', productName);
    
//         // Close the modal
//         onClose();
//       };

//   return (
//     <>
//       {isOpen && (
//         <div className="modal-overlay">
//           <div className="modal">
        
//             <div className="modal-content">
//             <button className="close-button" onClick={onClose}>
//               &times;
//             </button>

//             <p>Enter Product Name</p>
//         <input 
//           type="text" 
//           placeholder="Enter product name"
//           value={productName}
//           onChange={handleProductNameChange}
//         />
//        <button onClick={handleConfirmProduct}>Save</button>
            
              
//             </div>
//           </div>
//         </div>
//       )}
//     </>
//   );
// };

// export default ModalForm;
import React, { useState } from 'react';
import './ModalForm.css';
import Button from './Button';

const ModalForm = ({ isOpen, onClose, saveProductData, cleanPickScore, ingredientScores }) => {
  const [productName, setProductName] = useState('');

  const handleProductNameChange = (e) => {
    setProductName(e.target.value);
  };

  const handleSaveProduct = () => {
    // Check if productName is not empty
    if (productName.trim() !== '') {
      // Call the saveProductData function with all the necessary data
      saveProductData(productName);
      // Close the modal
      onClose();
    } else {
      // Display an error message or handle the case where the product name is empty
      console.error('Product name cannot be empty');
    }
  };

  return (
    <>
      {isOpen && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-content">
              <button className="close-button" onClick={onClose}>
                &times;
              </button>
              <p>Enter Product Name</p>
              <input 
                type="text" 
                placeholder="Enter product name"
                value={productName}
                onChange={handleProductNameChange}
              />
              <button onClick={handleSaveProduct}>Save</button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ModalForm;
