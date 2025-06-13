package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.model.User;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import io.github.kitrinaludex.file_uploader.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        return new SecurityUser(user);
    }
}
