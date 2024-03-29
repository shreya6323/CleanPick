// import React, { useState, useRef } from 'react';
// import './ImageUploader.css'; // Import your CSS file for styling
// import Loader from './Loader'; // Import your Loader component
// import ScoreCircle from './ScoreCircle';

// const ImageUploader = () => {
//   const [selectedImage, setSelectedImage] = useState(null);
//   const [expanded, setExpanded] = useState(false);
//   const [loading, setLoading] = useState(false); // Add loading state
//   const [showForm, setShowForm] = useState(false); // Add state to control form visibility
//   const fileInputRef = useRef(null);
//   const [productName, setProductName] = useState("");
//   const [ingredientScores, setIngredientScores] = useState(null);
//   const [cleanPickScore, setCleanPickScore] = useState(null);

//   const handleImageChange = (e) => {
//     const file = e.target.files[0];
//     if (file && file.type.startsWith('image/')) {
//       const reader = new FileReader();
//       reader.onload = () => {
//         setSelectedImage(reader.result);
//       };
//       reader.readAsDataURL(file);
//     }
//   };

//   const handleClick = () => {
//     fileInputRef.current.click();
//   };

//   const handleScore = async () => {
//     if (selectedImage) {
//       setLoading(true); // Set loading to true when starting request
//       try {
//         const formData = new FormData();
//         formData.append('image', fileInputRef.current.files[0]); // Append the selected file with the key 'image'
//         const response = await fetch('http://localhost:8080/performWebScraping', {
//           method: 'POST',
//           body: formData
//         });
//         if (response.ok) {
//           const result = await response.text();
//           console.log('OCR Result:', result);
//           setCleanPickScore(JSON.parse(result).cleanPickScore);
//           setIngredientScores(JSON.parse(result).ingredients);
//            // Show the form after getting the scores
//         } else {
//           console.error('Failed to perform web scraping:', response.statusText);
//         }
//       } catch (error) {
//         console.error('Error performing web scraping:', error);
//       } finally {
//         setLoading(false); // Set loading to false when request completes
//       }
//     } else {
//       console.log('No image selected');
//     }
//   };
  
//   const getColorForScore = (score) => {
//     const hue = ((10 - score) * 120) / 9; 
//     const color = `hsl(${hue}, 100%, 50%)`;
//     return color;
//   };

//   const saveProductData = async () => {
//     setShowForm(true);
//     // Add functionality to save product data
//   };

//   return (
//     <div className="image-uploader-container">
//       <button onClick={handleClick} className="custom-file-upload">
//         Choose Image
//       </button>

//       {/* Your custom button or trigger */}
//       {selectedImage ? (
//         <div className="preview-container">
//           <img src={selectedImage} alt="Image" className="preview-image" />
//         </div>
//       ) : null}

//       {/* Hide the default file input button */}
//       <input
//         type="file"
//         id="imageInput"
//         accept="image/*"
//         onChange={handleImageChange}
//         ref={fileInputRef}
//         style={{ display: 'none' }}
//       />

//       {/* Show loader if loading state is true */}
//       {loading && <Loader />}

//       <button onClick={handleScore} className="custom-file-upload" disabled={!selectedImage}>
//         Find CleanPick Score !
//       </button>

   

//       {cleanPickScore && (
//         <div className="score-container">
//           <ScoreCircle score={cleanPickScore} color={getColorForScore(cleanPickScore)}  className={"big"} />
//         </div>
//       )}

// {cleanPickScore && (<button onClick={saveProductData}>Save Product</button>)}


// {showForm && (
//         <div className="product-form">
//           <input
//             type="text"
//             placeholder="Enter product name"
//             value={productName}
//             onChange={(e) => setProductName(e.target.value)}
//           />
//           <button onClick={saveProductData}>Save Product</button>
//         </div>
//       )}

//       {ingredientScores !== null && (
//         <button onClick={() => setExpanded(!expanded)} className="custom-file-upload">
//           {expanded ? "Hide Ingredients Score" : "View Ingredients Score"}
//         </button>
//       )}

//       {expanded && ingredientScores && Object.entries(ingredientScores).map(([ingredient, score]) => (
//         <div key={ingredient} className="ingredient-card">
//           <div className="ingredient-info">
//             <ScoreCircle score={score} color={getColorForScore(score)} className={"small"} />
//             <strong>{ingredient}</strong> 
//           </div>
//         </div>
//       ))}
//     </div>
//   );
// };

// export default ImageUploader;
import React, { useState, useRef, useEffect } from 'react';
import ScrollToTopButton from './ScrollToTopButton';
import './ImageUploader.css'; // Import your CSS file for styling
import Loader from './Loader'; // Import your Loader component
import ScoreCircle from './ScoreCircle';
import ModalForm from './ModalForm'; // Import your ModalForm component
import Button from './Button';
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';



const ImageUploader = () => {
  const [selectedImage, setSelectedImage] = useState(null);
  const [expanded, setExpanded] = useState(false);
  const [saved, setSaved] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(false); // Add loading state
  // const [showModal, setShowModal] = useState(false); // Add state for modal visibility
  const fileInputRef = useRef(null);
  // const [productName, setProductName] = useState("");
  const [ingredientScores, setIngredientScores] = useState(null);
  const [cleanPickScore, setCleanPickScore] = useState(null);
const[token,setToken] = useState('');

useEffect(() => {
  const token = localStorage.getItem('token');
  if (token) {
      console.log(token);
      setToken(true);
  }
}, []);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file && file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onload = () => {
        setSelectedImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    
    setIsModalOpen(false);
  };

  
  const notify_error = (error_msg) => toast.error(error_msg);
  const notify_success = (success_msg) => toast.success(success_msg);

  const handleScore = async () => {
    if (selectedImage) {
      setCleanPickScore(null);
      setIngredientScores(null);
      setLoading(true); // Set loading to true when starting request
      try {
        const formData = new FormData();
        formData.append('image', fileInputRef.current.files[0]); // Append the selected file with the key 'image'
        const response = await fetch('http://localhost:8080/api/performWebScraping', {
          method: 'POST',
          body: formData
        });
        if (response.ok) {
          const result = await response.text();
          console.log('OCR Result:', result);
          setCleanPickScore(JSON.parse(result).cleanPickScore);
          setIngredientScores(JSON.parse(result).ingredients);
          //saveProductData();
         // setShowModal(true); // Show the modal after getting the scores
        } else {
          console.error('Failed to perform web scraping:', response.statusText);
        }
      } catch (error) {
        console.error('Error performing web scraping:', error);
      } finally {
        setLoading(false); // Set loading to false when request completes
      }
    } else {
      console.log('No image selected');
    }
  };
  
   function getColorForScore(score) {
    const hue = ((10 - score) * 120) / 9;
    const color = `hsl(${hue}, 100%, 50%)`;
    return color;
  }

  const sendToken = async () => {
    try {
      let url = 'http://localhost:8080/api/products/search/token';
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
     
      } else {
        console.error('Failed to send token');
      }
    } catch (error) {
      console.error('Error sending token:', error);
    }
  };
 
  const saveProductData = async (Name) => {
    try {
      await sendToken();
      const response = await fetch('http://localhost:8080/api/products', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          productName: Name, // Include the product name
          ingredientScores: ingredientScores,
          overallScore: cleanPickScore
        })
      });
      
      if (response.ok) {
        notify_success("Product saved successfully!");
     //   console.log('Product saved successfully !');
       
        // Reset product name or clear form fields if needed
      }
    else  {
  
      notify_error("Product already exists !");
       // Show toast if product already exists
       }
    } catch (error) {
    notify_error(error);
      console.error('Error in saving the product !');
    }
  };
  
  


  return (
  
    <div className="image-uploader-container">
      <button onClick={handleClick} className="custom-file-upload">
        Choose Image
      </button>
      <ScrollToTopButton scrollThreshold={1000}/>
      {/* Your custom button or trigger */}
      {selectedImage ? (
        <div className="preview-container">
          <img src={selectedImage} alt="Image" className="preview-image" />
        </div>
      ) : null}

      {/* Hide the default file input button */}
      <input
        type="file"
        id="imageInput"
        accept="image/*"
        onChange={handleImageChange}
        ref={fileInputRef}
        style={{ display: 'none' }}
      />

      {/* Show loader if loading state is true */}
      {loading && <Loader />}


      <button onClick={handleScore} className="custom-file-upload" disabled={!selectedImage}>
        Find CleanPick Score !
      </button>

      <ToastContainer/>
      {cleanPickScore && (
        <div className="score-container">
          <ScoreCircle score={cleanPickScore} color={getColorForScore(cleanPickScore)}  className={"big"} />
        </div>
      )}

      {token && cleanPickScore  && (<button onClick={openModal} className="open-modal-button" >Save</button>)}
   
      <ModalForm
  isOpen={isModalOpen}
  onClose={closeModal}
  saveProductData={saveProductData}
  cleanPickScore={cleanPickScore}
  ingredientScores={ingredientScores}
/>

  

     

      {ingredientScores !== null && (
        <button onClick={() => setExpanded(!expanded)} className="custom-file-upload">
          {expanded ? "Hide Ingredients Score" : "View Ingredients Score"}
        </button>
      )}

      {expanded && ingredientScores && Object.entries(ingredientScores).map(([ingredient, score]) => (
        <div key={ingredient} className="ingredient-card">
          <div className="ingredient-info">
            <ScoreCircle score={score} color={getColorForScore(score)} className={"small"} />
            <strong>{ingredient}</strong> 
          </div>
        </div>
      ))}
    </div>
  );
};


export default ImageUploader ;

