
package com.example.demo;

import java.util.Map;




public interface GeneralProduct {
    Map<String, String> getIngredients();
    double getCleanPickScore();
    void setIngredients(Map<String, String> ingredients);
    void setCleanPickScore(double cleanPickScore);
}
