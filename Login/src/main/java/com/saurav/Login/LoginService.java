package com.saurav.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    LoginRepo repo;
    public List<User> getAll()
    {
        return repo.findAll();
    }

    public void addUser(User user) {
        repo.save(user);
    }

    public void deleteUser(int id) {
        repo.deleteById(id);
    }

    public User updateUser(User user, int id) {
        return repo.save(user);
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public List<User> searchUser(String variable) {
        return repo.searchUser(variable);
    }

    public User getUserById(int id) {
        return repo.findById(id).orElse(new User());
    }
    public User findUserByJwt(String jwt)
    {
        String email=JwtProvider.getEmailFromJwtToken(jwt);
        User user=repo.findByEmail(email);
        return user;
    }
}
