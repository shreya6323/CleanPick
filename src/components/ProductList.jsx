
import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import DeleteConfirmationModal from './DeleteConfirmationModal';
import UpdateProductModal from './UpdateProductModal';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SearchBar from './SearchBar';
import './ProductList.css';
import jwt_decode from 'jwt-decode';
import ScrollToTopButton from './ScrollToTopButton';


const error = (error_msg) => toast.error(error_msg);
const success = (success_msg) => toast.success(success_msg);

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [sortOption, setSortOption] = useState('');



  const handleSortChange = (event) => {
    setSortOption(event.target.value);
  };

  
  useEffect(() => {
    const sendToken = async () => {
      try {
        let url = '/api/products/search/token';
        console.log(url);
        const storedToken = localStorage.getItem('token');
        console.log(storedToken);
        const response = await fetch(url, {
          headers: {
            Authorization: `Bearer ${storedToken}` // Include token in request header
          }
        });
  
        if (response.ok) {
          console.log("Token sent successfully!");
          fetchProducts();
        } else {
          console.error('Failed to send token');
        }
      } catch (error) {
        console.error('Error sending token:', error);
      }
    };
  
    const fetchProducts = async () => {
      try {
        let url = '/api/products/search';
        if (searchTerm) {
          url += `/${searchTerm}`;
        }
        if (sortOption && sortOption !== 'sort_by') {
          if (searchTerm) {
            url += `/${sortOption}`;
          } else {
            url += `/all/${sortOption}`;
          }
        }
        console.log(url);
        const response = await fetch(url);
  
        if (response.ok) {
          const data = await response.json();
          setProducts(data.map(productInfo => ({
            id: productInfo.id,
            productName: productInfo.productName,
            ingredientScores: productInfo.ingredientScores,
            overallScore: productInfo.overallScore
          })));
        } else {
          const errorMessage = await response.text();
          error(`Product does not exist !`);
          setSearchTerm('');
          setSortOption('');
          console.error('Failed to fetch products:', errorMessage);
        }
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    };
  
    sendToken(); // Call sendToken function
   
  }, [searchTerm, sortOption]);
  

  const handleDeleteProduct = async (productId) => {
    try {
      const response = await fetch(`/api/products/${productId}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        setProducts(products.filter((product) => product.id !== productId));
        setShowDeleteModal(false);
        success('Product has been deleted successfully !');
      } else {
        error('Product deletion failed !');
        console.error('Failed to delete product:', response.statusText);
      }
    } catch (error) {
      error('Error occurred !');
      console.error('Error deleting product:', error);
    }
  };

  const handleUpdateProduct = async (productId, updatedProductData) => {
    try {
      const response = await fetch(`/api/products/${productId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedProductData),
      });
      if (response.ok) {
        const updatedProduct = await response.json();
        setProducts(products.map((product) => (product.id === updatedProduct.id ? updatedProduct : product)));
        setSelectedProduct(null);
        setShowUpdateModal(false);
        success("Product updated successfully !");
      } else {
        error("Failed to update product !");
        console.error('Failed to update product:', response.statusText);
      }
    } catch (error) {
      error("Error occurred in updation!");
      console.error('Error updating product:', error);
    }
  };
// <option value=''>Sort by</option>
  return (
      <div className='containers'>
        <ScrollToTopButton scrollThreshold={1200}/>
      <ToastContainer />
      <p className='headings'>Products List</p>
      <SearchBar onSearch={setSearchTerm} />
      <div class='sort-dropdown'>
  <select value={sortOption}  onChange={handleSortChange}>
    <option value='sort_by'>Sort By</option>
    <option value='name_asc'>Name (A-Z)</option>
    <option value='name_des'>Name (Z-A)</option>
    <option value='score_asc'>Score (1-10)</option>
    <option value='score_des'>Score (10-1)</option>
  </select>
</div>

      {products.map((product) => (
        <div key={product.id} className="product-card-container">
          <ProductCard product={product} />
          <div className="button-container">
            <button onClick={() => { setSelectedProduct(product); setShowDeleteModal(true); }}>Delete</button>
            <button onClick={() => { setSelectedProduct(product); setShowUpdateModal(true); }}>Update</button>
          </div>
        </div>
      ))}

      {showDeleteModal && (
        <DeleteConfirmationModal 
          isOpen={showDeleteModal} 
          onClose={() => setShowDeleteModal(false)} 
          onConfirm={() => handleDeleteProduct(selectedProduct.id)} 
        />
      )}

      {showUpdateModal && (
        <UpdateProductModal
          isOpen={showUpdateModal} 
          product={selectedProduct}
          onClose={() => setShowUpdateModal(false)}
          onUpdate={(updatedProductData) => handleUpdateProduct(selectedProduct.id, updatedProductData)}
        />
      )}
    </div>
  );
};

export default ProductList;




