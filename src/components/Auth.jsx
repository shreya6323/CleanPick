import React, {useState}  from 'react' ;
import  './Auth.css' ; // Import CSS file
import {Link} from 'react-router-dom' ; 
import {FaUser,FaLock} from "react-icons/fa" ;
import {ToastContainer,toast} from 'react-toastify' ;
        
       
function Auth() {
const error = (error_msg) => toast.error(error_msg);
const success = (success_msg) => toast.success(success_msg);
const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch('http://localhost:8080/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });
    if(response.ok)
    {

      const token = response.headers.get('Authorization').split(' ')[1]; // Extract token from header
      console.log(token);
      localStorage.setItem('token', token); // Store token in local storage
      success('Successfully Logged In !');//why it is not showing the pop up!!
      window.location.href = '/';
 
    }
    else if(response.status === 401)
    {
      error ('Invalid username or password !');
    }
   
    else{
      console.log(response.data);
      error("An error occurred. Please try again later.");
    }
  }
    catch (err) {
     
        error("An error occurred. Please try again later.");
      
    }

};




  const [username, setUsername] = useState('');
const [password, setPassword] = useState('');
  return (
    <div className="container">
      <div className="wrapper">
    <ToastContainer />
        <form onSubmit={handleSubmit}>
          <div className="h2">Login</div>
          <div className="input-box">
            <FaUser className="icon" />
            <input type="text" placeholder="Username"  value={username} onChange={(e) => setUsername(e.target.value)} required  />
          </div>
          <div className="input-box">
            <FaLock className="icon" />
            <input type="password" placeholder="Password"  value={password} onChange={(e) => setPassword(e.target.value)} required  />
          </div>

          <div className="remember-forgot">

        <div className='forgot'> <a href="#">Forgot Password?</a></div> 
          </div>


          <  button type="submit" className="button" >Login</button>
          <div className="register-link">
          <p>Don't have an account? <Link to="/register">Register</Link></p>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Auth;
