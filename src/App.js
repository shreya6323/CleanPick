import React from 'react';
import { useState } from 'react';
 import Navbar from './components/Navbar';
 import House from './components/House';
import Footer from './components/Footer';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Discover from './components/Discover';
import ModalForm from './components/ModalForm';
import ProductList from './components/ProductList';
import Auth from './components/Auth';
import Register from './components/Register';

function App() {
  // const [isModalOpen, setIsModalOpen] = useState(false);
  // const openModal = () => {
  //   setIsModalOpen(true);
  // };

  // const closeModal = () => {
  //   setIsModalOpen(false);
  // };
  return (
     <Router>
      <div>
     {/* <Discover /> */}
         <Navbar />
       

        <Routes>
          <Route path="/" element={<House />} />
          <Route path="/products" element={<ProductList />} />
          <Route path="/auth" element={<Auth />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Auth />} />
        </Routes>
        <Footer />  
      </div>
     </Router>


//  <ProductList />

 


    // <div className="app-container">
    //   <h1>Image Uploader</h1>
   
    //   <button onClick={openModal} className="open-modal-button">
    //     Open Modal
    //   </button>
    //   <ModalForm isOpen={isModalOpen} onClose={closeModal} />
    // </div>
  );
}

export default App;


