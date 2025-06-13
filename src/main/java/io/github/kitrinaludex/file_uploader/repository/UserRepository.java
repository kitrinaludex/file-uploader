package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
       return jdbcTemplate.queryForObject(sql,new UserMapper(),username);
    }
}
