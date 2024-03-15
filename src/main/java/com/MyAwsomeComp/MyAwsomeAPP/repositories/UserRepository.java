package com.MyAwsomeComp.MyAwsomeAPP.repositories;

import com.MyAwsomeComp.MyAwsomeAPP.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private List<User> _users = new ArrayList<>();

    public User findUserByUsername(String username){

        var a = _users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);

        return a;
    }

    public void addUser(User user){
        _users.add(user);
    }

    public void addUser(List<User> users){
        _users.addAll(users);
    }

    public List<User> getAllUsers(){
        return _users;
    }
}
