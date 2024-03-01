package com.example.demo;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
@AllArgsConstructor
@Document("user")
public class User {

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ProductInfo> products = new HashSet<>();

     public String getId() {
          return id;
     }

     public Set<ProductInfo> getProducts() {
          return products;
     }

     public void setProducts(Set<ProductInfo> products) {
          this.products = products;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public boolean isActive() {
          return active;
     }

     public void setActive(boolean active) {
          this.active = active;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private String id;

    @Indexed(unique = true)
     private String username;

     private String password;

     private boolean active;

     @Indexed(unique = true)
     private String email;
}
