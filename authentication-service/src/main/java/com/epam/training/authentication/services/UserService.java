package com.epam.training.authentication.services;

import com.epam.training.authentication.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ConfigurationProperties(prefix = "user-store")
public class UserService implements UserDetailsService {

    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> getUserByEmail(String email) {
        return users.stream().filter(user -> email.equals(user.getEmail()))
                .findFirst();
    }
}
