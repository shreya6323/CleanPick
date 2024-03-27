import React, { useState, useEffect } from 'react';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import '@fortawesome/fontawesome-free/css/all.min.css';
import axios from 'axios'; 


function Navbar() {
    const [click, setClick] = useState(false);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const handleClick = () => setClick(!click);
    const closeMobileMenu = () => setClick(false);
    const navigate = useNavigate(); 
  
  
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            console.log(token);
            setIsAuthenticated(true);
        }
    }, []);
    useEffect(() => {
        // Function to set initial click state based on window width
        const setInitialClickState = () => {
            if (window.innerWidth <= 960) {
                setClick(true); // Set click state to true if window width is less than or equal to 960 pixels
            }
        };
    
        // Call the function to set initial click state when the component mounts
        setInitialClickState();
    
        // Function to handle resizing
        const handleResize = () => {
            // Close the menu when the screen size increases
            if (click && window.innerWidth > 960) {
                setClick(false);
            }
        };
    
        // Add event listener for window resize
        window.addEventListener('resize', handleResize);
    
        // Clean up the event listener on component unmount
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []); // Empty dependency array ensures that the effect runs only once when the component mounts
    
    // Use useEffect to listen for window resize events
    // useEffect(() => {
    //     const handleResize = () => {
    //         // Close the menu when the screen size increases
    //         if (click && window.innerWidth > 960) {
    //             setClick(false);
    //         }
    //     };

    
    //   // Call the checkAuthentication function when the component mounts
    
    //     window.addEventListener('resize', handleResize);
    
    //     // Clean up the event listener on component unmount
    //     return () => {
    //         window.removeEventListener('resize', handleResize);
    //     };
    // }, [click]); // Include click in the dependency array to trigger useEffect when click changes
    
    const handleSignOut = () => {
        // Remove the JWT token from local storage
        localStorage.removeItem('token');
        
        // Make a call to the backend logout endpoint
        // axios.post('/api/logout')
        //     .then(response => {
        //         console.log("user logged out !"); // Log success message
        //         // Redirect the user to the home page after logout
        //         window.location.href = '/'; 
        //     })
        //     .catch(error => {
        //         console.error('Logout failed:', error); // Log error message
        //         // Redirect the user to the home page even if logout fails
        //         window.location.href = '/'; 
        //     });
        window.location.href = '/'; 
    };
  

    return (
        <nav className='navbar'>
            <div className='navbar-container'>
                <Link to="/" onClick={closeMobileMenu}>
                    <span className="logo">clean</span><span className="logo-second">pick</span>
                </Link>
                
                {window.innerWidth <= 960 && (
                    <div className='menu-icon' onClick={handleClick}>
                        <i className={click ? 'fas fa-times' : 'fas fa-bars'}/>
                    </div>
                )}
                
                <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                    <li className='nav-item'>
                        <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                            Home 
                        </Link>
                    </li>

                   {/* <li className='nav-item'> */}
                        {/* <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                            Discover
                        </Link>
                    </li> */}

                 {isAuthenticated  && <li className='nav-item'>
                        <Link to='/products' className='nav-links' onClick={closeMobileMenu}>
                            Products
                        </Link>
                    </li>}


                    {/* <li className='nav-item'>
                        <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                            Contact
                        </Link>
                    </li> */}


                    {/* Additional menu items */}
               {!isAuthenticated &&   <li>
                        <Link to="/auth">
                            <button className='login-button'>Login</button>
                        </Link>
                    </li>}

                    {isAuthenticated &&   <li>
                        <Link to="/logout">
                            <button className='login-button' onClick={handleSignOut} >Sign Out</button>
                        </Link>
                    </li>}
                
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;



