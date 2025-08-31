package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByUsername(String username) {

        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            return jdbcTemplate.queryForObject(sql,new UserMapper(),username);
        }catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User does not exist");
        }
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
