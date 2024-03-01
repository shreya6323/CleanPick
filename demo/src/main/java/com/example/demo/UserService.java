package com.example.demo;

// import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     Optional<com.example.demo.User> authUser = userRepository.findByUsername(username.toLowerCase());
    //     if (!authUser.isPresent()) {
    //         throw new UsernameNotFoundException(username);
    //     } else {
    //         return User.builder()
    //                 .username(authUser.get().getUsername())
    //                 .password(authUser.get().getPassword())
    //                 .disabled(!authUser.get().isActive())
    //                 .build();
    //     }
    // }


    // public com.example.demo.User findByUsername(String username) {
    //     return userRepository.findByUsername(username.toLowerCase()).orElse(null);
    // }

      public com.example.demo.User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public com.example.demo.User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
}