package org.cibertec.edu.pe.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private Usuario user;
    private Collection<? extends GrantedAuthority> authorities;

    public static AuthUser instance(Usuario user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //authorities.add((GrantedAuthority) new SimpleGrantedAuthority(user.getRol().getName()));
        return new AuthUser(user, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
