package ba.sema.springbootvuedemo.configurations;

import ba.sema.springbootvuedemo.entities.SecurityUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUserDetails implements UserDetails
{
    private Long id;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUserDetails(Long id, String email, String username, String password, Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static SecurityUserDetails build(SecurityUser securityUser) {
        List<GrantedAuthority> authorities = securityUser.getRoles().stream()
                                                                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                                                    .collect(Collectors.toList());
        return new SecurityUserDetails(
            securityUser.getId(),
            securityUser.getEmail(),
            securityUser.getUsername(),
            securityUser.getPassword(),
            authorities
        );
    }

    public Long getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
