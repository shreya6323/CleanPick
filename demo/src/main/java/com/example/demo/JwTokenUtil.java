// package com.example.demo;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Service;
// import java.util.Date;
// import javax.crypto.SecretKey;

// // @Service
// // public class JwTokenUtil {

// //     private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
// //     private static final long EXPIRATION_TIME = 86_400_000; // 10 days

// //     @SuppressWarnings("deprecation")
// //     public String generateToken(User user) {
// //         System.out.println("hi i am  in jwt");
// //         String token = "";     
// //         try{
// //          token =  Jwts.builder()
// //                 .setSubject(user.getUsername())
// //                 .setIssuedAt(new Date())
// //                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
// //                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
// //                 .compact();
// //     }
// //     catch(Exception e)
// //     {
// //         System.out.println(e);
// //     }
// // System.out.println(token);
// //                 return token;
// //     }

// // import com.auth0.jwt.JWT;
// // import com.auth0.jwt.algorithms.Algorithm;
// // import com.auth0.jwt.interfaces.DecodedJWT;

// @Service
// public class JwTokenUtil {

//     // private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//     // private static final long EXPIRATION_TIME = 86_400_000; // 10 days

//     public String generateToken(User user) {
//         try {
//             // // Build JWT token with custom claims
//             // String token = Jwts.builder()
//             //         .setSubject(user.getUsername())
//             //         .setIssuedAt(new Date())
//             //         .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//             //         .claim("id", user.getId())  // Custom claim for user ID
                   
//             //         // You can add more custom claims as needed
//             //         .signWith(SECRET_KEY)
//             //         .compact();
//             String token = JWT.create()
//     .withSubject("user123")
//     .sign(Algorithm.HMAC256("shreya"));
//             return token;
//         } catch (Exception e) {
//             e.printStackTrace(); // Handle exception properly
//             return null;
//         }
//     }


//     // public  SecretKey getKey()
//     // {
//     //           return this.SECRET_KEY;
//     // }
//     // Other methods...
// }


//     // Method to validate and extract user information from JWT token
//     // You may need to implement this as per your requirements




