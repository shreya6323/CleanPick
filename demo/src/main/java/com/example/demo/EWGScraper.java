// package com.example.demo;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

// import java.io.IOException;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Scanner;

// public class EWGScraper {
//     Product p ;
//    static int a;
//    static int b;
//     public void findScore(String product_name) {
//         // Specify the URL and the search query
//         /*
//          * Scanner scanner = new Scanner(System.in);
//          * System.out.print("Enter something: ");
//          * 
//          * // Print the input
//          * System.out.println("You entered: " + userInput);
//          */
//         Map<String, String> m= new HashMap<>();
//         product_name = product_name.toUpperCase();
//         product_name = product_name.replace("!", "l");
//         product_name = product_name.trim();
       
//         // Replace newlines with spaces
//         product_name = product_name.replace("\n", " ");
//         // Modify the string and construct the URL
//          String modifiedString = product_name.replace(" ", "+");
//         String url = "https://www.ewg.org/skindeep/search/?search=" + modifiedString;
//         System.out.println(url);
//         // Set up the headers for the GET request
//         Map<String, String> headers = new HashMap<>();
//         headers.put("User-Agent",
//                 "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

//         try {
//             // Send a GET request to the search page
//             String htmlContent = sendGetRequest(url, headers);
          
//             // Parse the HTML content of the search results page
//             Document document = Jsoup.parse(htmlContent);

//             // Extract information from all the product-tile elements
//             Elements resultItems = document.select("div.product-tile");

//             // Check if resultItems is not empty
//             if (!resultItems.isEmpty()) {
//                 for (Element item : resultItems) {

//                     // Use select within each item to get the product name
//                     Element productNameElement = item.selectFirst("div.product-name");
//                     String productName = productNameElement.text().trim();

//                     if (productName.equals(product_name)) {
//                         // Extract the hazard score
//                         // Element productScoreElement = item.selectFirst("div.product-score");
//                         // String hazardScore = (productScoreElement != null) ?
//                         // productScoreElement.selectFirst("img").attr("src").split("=")[1].split("&")[0]
//                         // :
//                         // "N/A";
//                         // Assuming productScoreElement is the element representing the product score
//                         // image
//                         Element productScoreElement = item.selectFirst("div.product-score");
//                          String hazardScore = (productScoreElement != null) ?
//                                 productScoreElement.selectFirst("img").attr("src").split("=")[1].split("&")[0] :
//                                 "N/A";
//                       b+=Integer.parseInt(hazardScore);
//                         // Print or process the extracted information
//                         System.out.println("Product Name: " + productName);
//                         a++;
//                         m.put(productName,hazardScore);
//                         System.out.println("Hazard Score: " + hazardScore);
//                         System.out.println("---");
//                         p.setCleanPickScore(b/(a*1.0));
//                         p.setIngredients(m);

//                     }
//                 }
//                 System.out.println(b);
//                 System.out.println(a);
//                 System.out.println(b/a);
//             } else {
//                 System.out.println("No results found!");
//             }

        
//         } catch (IOException e) {
//             System.out.println("Error: Unable to retrieve the page. " + e.getMessage());
//         }

//        // System.out.println(b/a*10);
      
//     }

//        public static double getScore()
//        {
       
//         return b/(a*1.0);
//        }

//        public  Product getProduct()
//        {
//         return p;
//        }


//     private static String sendGetRequest(String urlString, Map<String, String> headers) throws IOException {
//         URL url = new URL(urlString);
//         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

//         // Set headers
//         for (Map.Entry<String, String> entry : headers.entrySet()) {
//             connection.setRequestProperty(entry.getKey(), entry.getValue());
//         }

//         // Get HTML content
//         try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
//             scanner.useDelimiter("\\A");
//             return scanner.hasNext() ? scanner.next() : "";
//         } finally {
//             connection.disconnect();
//         }
//     }

//     public static void main(String args[]) {

//     }
// }

package com.example.demo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class EWGScraper implements Scraper{
        private GeneralProduct product;
        private int total_cleanpick_score = 0;
        private int total_recognised_products = 0;
        private Map<String, String> m = new HashMap<>();

    @Autowired
    public EWGScraper(GeneralProduct product) {
        this.product = product;
    }

        public void findScore(String ingredient) {
       
            ingredient = ingredient.toUpperCase();
            ingredient = ingredient.replace("!", "l");
            ingredient= ingredient.trim();
            ingredient = ingredient.replace("\n", " ");
        String modifiedString = ingredient.replace(" ", "+");
        String url = "https://www.ewg.org/skindeep/search/?search=" + modifiedString;
       
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        try {
            String htmlContent = sendGetRequest(url, headers);
            Document document = Jsoup.parse(htmlContent);
            Elements resultItems = document.select("div.product-tile");

            if (!resultItems.isEmpty()) {
                for (Element item : resultItems) {
                    Element ingredientNameElement = item.selectFirst("div.product-name");
                    String ingredientName = ingredientNameElement.text().trim();
                    if (ingredientName.equals(ingredient)) {
                        Element ingredientScoreElement = item.selectFirst("div.product-score");
                        String hazardScore = (ingredientScoreElement != null) ?
                                ingredientScoreElement.selectFirst("img").attr("src").split("=")[1].split("&")[0] :
                                "N/A";
                                total_cleanpick_score += Integer.parseInt(hazardScore);
                                total_recognised_products++;
                        m.put(ingredientName, hazardScore);
                        System.out.println(ingredientName);
                    }
                }
               
            } else {
                System.out.println("No results found!");
            }

        } catch (IOException e) {
            System.out.println("Error: Unable to retrieve the page. " + e.getMessage());
        }

        
    }

   

    public GeneralProduct getProductInfo() {
        double calculatedScore = total_cleanpick_score / (total_recognised_products * 1.0);
        double truncatedScore = Double.parseDouble(String.format("%.1f", calculatedScore));
        product.setCleanPickScore(truncatedScore);
        product.setIngredients(m);
        return product;
    }
    

    private static String sendGetRequest(String urlString, Map<String, String> headers) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            connection.disconnect();
        }
    }
}
