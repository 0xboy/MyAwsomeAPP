package com.MyAwsomeComp.MyAwsomeAPP.controller;

import com.MyAwsomeComp.MyAwsomeAPP.auth.JwtUtil;
import com.MyAwsomeComp.MyAwsomeAPP.model.User;
import com.MyAwsomeComp.MyAwsomeAPP.model.request.LoginReq;
import com.MyAwsomeComp.MyAwsomeAPP.model.response.ErrorRes;
import com.MyAwsomeComp.MyAwsomeAPP.model.response.LoginRes;
import com.MyAwsomeComp.MyAwsomeAPP.model.response.UserRes;
import com.MyAwsomeComp.MyAwsomeAPP.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/auth")
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq){
        try{

            User user = userRepository.findUserByUsername(loginReq.getUsername());

            if (user == null)
            {
                user = new User();
                user.setUsername(loginReq.getUsername());
                user.setPassword(passwordEncoder.encode(loginReq.getPassword()));
                user.setEmail(user.getUsername()+"@acb.com");
                userRepository.addUser(user);
            }

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));

            UserRes userRes = new UserRes();

            userRes.setEmail(user.getEmail());
            userRes.setToken(jwtUtil.createToken(user));
            userRes.setUsername(user.getUsername());

            LoginRes loginRes = new LoginRes();
            loginRes.setSuccess(true);
            loginRes.setMessage("Access granted!");
            loginRes.setUser(userRes);

            return ResponseEntity.ok(loginRes);

        }
        catch(BadCredentialsException ex){
            ErrorRes errorRes = new ErrorRes(false,"Something went wrong!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorRes);
        }
        catch (Exception e){
            ErrorRes errorRes = new ErrorRes(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorRes);
        }
    }
}
