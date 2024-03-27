import React, { useState } from 'react';
import './ModalForm.css';

const UpdateProductModal = ({isOpen, product, onClose, onUpdate }) => {
  const [updatedProduct, setUpdatedProduct] = useState({ ...product });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedProduct({ ...updatedProduct, [name]: value });
  };
  

  const handleSubmit = (e) => {
    e.preventDefault();
    onUpdate(updatedProduct);
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
        
           
              <input type="text" name="productName" value={updatedProduct.productName} onChange={handleChange} />
         
           
            <button onClick={handleSubmit}>Update</button>
          
          
        </div>
      </div>
      </div>
    )}
    </>
  );
};

export default UpdateProductModal;
