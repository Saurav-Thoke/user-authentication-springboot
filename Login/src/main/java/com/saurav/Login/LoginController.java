package com.saurav.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    LoginService service;
    @GetMapping("/api/getAll")
    public List<User> getAll()
    {
        return service.getAll();
    }
    @GetMapping("/api/getById/{id}")
    public User getUserById(@PathVariable int id)
    {
        return service.getUserById(id);
    }

    @GetMapping("/getByEmail/{email}")
    public User getByEmail(@PathVariable String email){
        return service.getByEmail(email);
    }

    @DeleteMapping("/api/deleteUser")
    public void deleteUser(@RequestHeader("Authorization") String jwt)
    {
        User reqUser=service.findUserByJwt(jwt);
        service.deleteUser(reqUser.getId());
    }
    @PutMapping("/api/updateUser")
    public  User updateUser(@RequestHeader("Authorization") String jwt,@RequestBody User user)
    {
        User reqUser=service.findUserByJwt(jwt);
        return service.updateUser(user,reqUser.getId());
    }
    @GetMapping("/api/searchUser/{variable}")
    public ResponseEntity<List<User>> searchUser(@PathVariable String variable)
    {

        List<User> users= service.searchUser(variable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/api/user/profile")
    public User getUserToken(@RequestHeader("Authorization") String jwt)
    {
        User user=service.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }
}
