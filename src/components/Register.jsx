
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom'; 
import React, { useState } from 'react';
import './Auth.css'; // Import CSS file
import { Link } from 'react-router-dom'; 
import { FaUser, FaLock, FaEnvelope } from "react-icons/fa";

function Auth() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); 
  const error = (error_msg) => toast.error(error_msg);
  const success = (success_msg) => toast.success(success_msg);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, email, password }),
      });
      if(response.ok)
      {

        const token = response.headers.get('Authorization').split(' ')[1]; // Extract token from header
        console.log(token);
        localStorage.setItem('token', token); // Store token in local storage
        success('Successfully Registered !');//why it is not showing the pop up!!?????????
        window.location.href = '/';
   
      }
      else if(response.status === 409)
      {
  error ('Username already exists, Try another one !');
      }
      else if(response.status === 400)
      {
            error('Email already exists !');
      }
      else{
        error("An error occurred. Please try again later.");
      }
    }
      catch (err) {
       
          error("An error occurred. Please try again later.");
        
      }
  
  };

  return (
   
    <div className='container'>
       <ToastContainer />
      <div className="wrapper">
        <form onSubmit={handleSubmit}>
          <div className="h2">Register</div>
          <div className="input-box">
            <FaUser className="icon" />
            <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required />
          </div>
          <div className="input-box">
            <FaEnvelope className="icon" />
            <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div className="input-box">
            <FaLock className="icon" />
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>

       

          <button type="submit" className="button">Register</button>
          <div className="register-link">
            <p>Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Auth;
