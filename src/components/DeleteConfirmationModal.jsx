import React from 'react';
import './ModalForm.css';

const DeleteConfirmationModal = ({ isOpen, onClose, onConfirm }) => {
  return (
    <>
      {isOpen && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-content">
            <button className="close-button" onClick={onClose}>
                &times;
              </button>
             
              <p> Are you sure you want to delete this product?</p>

              <button onClick={onConfirm}>Delete</button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default DeleteConfirmationModal;
