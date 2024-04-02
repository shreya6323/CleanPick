// package com.example.demo;

// import net.sourceforge.tess4j.ITesseract;
// import net.sourceforge.tess4j.Tesseract;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.File;

// @RestController

// public class WebScrapingController {

//     @RequestMapping("/performWebScraping")
//     public ResponseEntity<Product> performWebScraping(@RequestParam("image") MultipartFile imageFile) {
//         try {
//             // Save the uploaded image to a temporary file
//             File tempFile = File.createTempFile("uploaded_image", ".png,.jpg,.jpeg");
//             imageFile.transferTo(tempFile);

//             // Perform OCR using Tesseract
//             ITesseract tesseract = new Tesseract();
//             tesseract.setDatapath("E:\\Tesseract-OCR\\tessdata");
//           //  String result = tesseract.doOCR(new File("E:\\serum.jpg"));
//          //   C:\Users\shreya gandhi\Desktop\b.jpg
//            String result = tesseract.doOCR(tempFile);

//             // Log the result
//             System.out.println("OCR Result: " + result);
//              // Split the OCR result by commas to get individual ingredients
//             String[] ingredientsArray = result.split(",|and|\\(and\\)|&");
//           //String[] ingredientsArray = result.split("[,\\s]|(and)|(\\(and\\))|&");

//              // Log individual ingredients
//              for (String ingredient : ingredientsArray) {
//                  System.out.println("Ingredient: " + ingredient);
//                  EWGScraper  EWGScraper = new EWGScraper();
//                  EWGScraper.findScore(ingredient.trim());
//              }

//             // Delete the temporary file
//             tempFile.delete();
//             EWGScraper EWGScraper = new EWGScraper();
// double score = EWGScraper.getScore();
// Product p = new Product();
// p =  EWGScraper.getProduct();
//             // Return the OCR result as a success response
//             return ResponseEntity.ok(p);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                          .body(null);
//             // Return an error response with a custom message and status code
         
//         }
//     }
// }
package com.example.demo;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController

@CrossOrigin(origins = {"http://localhost:3000", "https://clean-pick.vercel.app"})
public class WebScrapingController {

   private Scraper scraper;

 @Autowired
    public WebScrapingController(Scraper scraper) {
        this.scraper = scraper;
    }

    public String requestMethodName(@RequestParam String param) {
        return new String();
    }

    @RequestMapping("/api/performWebScraping")
    public ResponseEntity<GeneralProduct> performWebScraping(@RequestParam("image") MultipartFile imageFile) {
        try {
           
            File tempFile = File.createTempFile("uploaded_image", ".png,.jpg,.jpeg");
            imageFile.transferTo(tempFile);

            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("../Tesseract-OCR/tessdata");
            String result = tesseract.doOCR(tempFile);
            String[] ingredientsArray = result.split(",|and|\\(and\\)|&");
          
            for (String ingredient : ingredientsArray) {
              
                scraper.findScore(ingredient.trim());
            }

           
            GeneralProduct p = scraper.getProductInfo();
            tempFile.delete();
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
