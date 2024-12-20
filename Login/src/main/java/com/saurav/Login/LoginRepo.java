package com.saurav.Login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepo extends JpaRepository<User,Integer> {
    //@Query("select u from User u where u.email like (concat('%',:useremail,'%'))")
    User findByEmail(String email);

    @Query("select u from User u where "
            + "lower(u.fname) like lower(concat('%', :keyword, '%')) or "
            + "lower(u.lname) like lower(concat('%', :keyword, '%')) or "
            + "lower(u.email) like lower(concat('%', :keyword, '%')) "
           )
    List<User> searchUser(@Param("keyword")String keyword);
}
