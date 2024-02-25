// package com.example.demo;
// import java.util.Map;

// public class Product {
//     private Map<String, String> ingredients;
//     private double cleanPickScore;

//     public Map<String,String> getIngredients() {
//         return ingredients;
//     }

//     public void setIngredients(Map<String, String> ingredients) {
//         this.ingredients = ingredients;
//     }

//     public double getCleanPickScore() {
//         return cleanPickScore;
//     }

//     public void setCleanPickScore(double cleanPickScore) {
//         this.cleanPickScore = cleanPickScore;
//     }
// }
package com.example.demo;

import java.util.Map;
import org.springframework.stereotype.Component;



@Component
public class Product implements GeneralProduct{
    private Map<String, String> ingredients;
    private double cleanPickScore;

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCleanPickScore() {
        return cleanPickScore;
    }

    public void setCleanPickScore(double cleanPickScore) {
        this.cleanPickScore = cleanPickScore;
    }

   
}
