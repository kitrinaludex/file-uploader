package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.Folder;
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

    public User getUserByUuid(String uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        return jdbcTemplate.queryForObject(sql,new UserMapper(),uuid);
    }

    public boolean exists(String username) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username));
    }

    public void save(User user) {
        String sql = "INSERT INTO users(uuid,username,password,root_folder_uuid) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql,user.getUuid(),user.getUsername(),user.getPassword(),user.getRootUuid());
    }

}
