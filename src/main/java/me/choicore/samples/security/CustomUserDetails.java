package me.choicore.samples.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.jackson.JsonMixin;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails implements UserDetails, CredentialsContainer {
    private User delegate;
    private boolean custom;

    public CustomUserDetails(User user) {
        this.delegate = user;
        this.custom = true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();

    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return delegate.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return delegate.getUsername();
    }

    @Override
    public void eraseCredentials() {
        delegate.eraseCredentials();
    }

    @JsonMixin(CustomUserDetails.class)
    public abstract static class CustomUserDetailsMixin {
    }
}
