package me.choicore.samples.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(@Nonnull final String username) throws UsernameNotFoundException {
        UserDetails userDetails = User.builder()
                .username("1")
                .password("{noop}1")
                .roles("USER")
                .build();

        return new CustomUserDetails((User) userDetails, "홍길동");
    }
}
