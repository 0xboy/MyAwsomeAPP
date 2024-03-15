package com.MyAwsomeComp.MyAwsomeAPP.services;

import com.MyAwsomeComp.MyAwsomeAPP.model.User;
import com.MyAwsomeComp.MyAwsomeAPP.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private PasswordEncoder passwordEncoder;

    public CustomDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = this.passwordEncoder();
        Seed();
    }


    private void Seed(){
        userRepository.addUser(List.of(
                new User("acb", passwordEncoder.encode("123456"), "acb@acb.com"),
                new User("acv", passwordEncoder.encode("123456v"), "acv@acb.com"),
                new User("acx", passwordEncoder.encode("123456x"), "acx@acb.com"),
                new User("acz", passwordEncoder.encode("123456z"), "acz@acb.com")
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        List<String> roles  = new ArrayList<>();
        roles.add("USER");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
        return userDetails;
    }
}
