package com.saurav.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    LoginService service;
    @Autowired
    LoginRepo loginRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/signup")  //"/auth/signup"
    public AuthResponse addUser(@RequestBody User user) throws Exception {
        User isExist=loginRepo.findByEmail(user.getEmail());
        if(isExist!=null)
            throw new Exception("User already exists with this email");

        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser=loginRepo.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        String token=JwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse(token,"Register Sucess");

        return res;
    }
    @PostMapping("/signin")  //"/auth/signin"
    public AuthResponse signIn(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication=authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token=JwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse(token,"Login Sucess");

        return res;
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails=customerUserDetailService.loadUserByUsername(email);
        if(userDetails==null)
            throw new BadCredentialsException("Invalid username");
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw new BadCredentialsException("invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
