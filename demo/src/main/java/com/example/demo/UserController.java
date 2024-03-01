package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@RestController
@AllArgsConstructor

public class UserController {
   
  
    // @Autowired
    // private final JwTokenUtil jwtTokenUtil;
  
    // private AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;
    @SuppressWarnings("rawtypes")
    @PostMapping("/api/register")
    public ResponseEntity registerUser(@RequestBody User user){

        try {

            if (userRepository.findByUsername(user.getUsername()).isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken. Please try again");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists !");
            }

            // Authentication authentication = authenticationManager.authenticate(
            //      new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
      

            // Hash a password
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // SecurityContextHolder.getContext().setAuthentication(authentication);
            user.setPassword(hashedPassword);
            User save = userRepository.save(user);
            String token = save.getId();//need to encode it
            System.out.println(token);// Generate JWT token
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("User registered successfully");
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



     @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Authentication authentication = authenticationManager.authenticate(
            //         new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // SecurityContextHolder.getContext().setAuthentication(authentication);

            User save = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            String token = save.getId();

            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login successful !");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password !");
        }
     
            // try {
           
    
            //     User save = userRepository.findByUsername(user.getUsername())
            //         .orElseThrow(() -> new RuntimeException("User not found"));

            //     //     Authentication authentication = authenticationManager.authenticate(
            //     //         new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    
            //     // SecurityContextHolder.getContext().setAuthentication(authentication);
            //     String token = jwtTokenUtil.generateToken(save);
    
            //     return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login successful !");
            // } catch (BadCredentialsException ex) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password !");
            // }
        }
    

    // @PostMapping("/api/logout")
    // public ResponseEntity<?> logout() {
    //     try {
    //         // Clear the authentication context
    //         // SecurityContextHolder.clearContext();
    //         // SecurityContextHolder.getContext().setAuthentication(null);
    //         return ResponseEntity.ok().body("Logout successful !");
    //     } catch (Exception e) {
    //         return ResponseEntity.internalServerError().body("Error occurred during logout !");
    //     }
    // }

}
