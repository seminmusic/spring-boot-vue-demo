package ba.sema.springbootvuedemo.configurations;

import ba.sema.springbootvuedemo.entities.SecurityUser;
import ba.sema.springbootvuedemo.repositories.SecurityUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityUserDetailsService implements UserDetailsService
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    private final SecurityUserRepository securityUserRepository;

    @Autowired
    public SecurityUserDetailsService(SecurityUserRepository securityUserRepository)
    {
        this.securityUserRepository = securityUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SecurityUser securityUser = securityUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' not found."));
        return SecurityUserDetails.build(securityUser);
    }
}
