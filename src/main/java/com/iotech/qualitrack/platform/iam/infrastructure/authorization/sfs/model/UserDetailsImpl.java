package com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.model;

import com.iotech.qualitrack.platform.iam.domain.model.aggregates.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security adapter for IAM users.
 */
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Long laboratoryId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public UserDetailsImpl(
            Long id,
            String username,
            String password,
            Long laboratoryId,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.laboratoryId = laboratoryId;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public static UserDetailsImpl fromUser(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();

        return new UserDetailsImpl(
                user.getId(),
                user.getUsernameValue(),
                user.getPasswordValue(),
                user.getLaboratoryId(),
                authorities,
                user.isActive()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getLaboratoryId() {
        return laboratoryId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}