package ba.sema.springbootvuedemo.services;

import ba.sema.springbootvuedemo.entities.SecurityRole;
import ba.sema.springbootvuedemo.entities.SecurityUser;
import ba.sema.springbootvuedemo.enums.RoleEnum;
import ba.sema.springbootvuedemo.repositories.SecurityRoleRepository;
import ba.sema.springbootvuedemo.repositories.SecurityUserRepository;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityUserRoleServiceImpl implements SecurityUserRoleService
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityUserRoleServiceImpl.class);

    private final SecurityUserRepository securityUserRepository;
    private final SecurityRoleRepository securityRoleRepository;

    @Autowired
    public SecurityUserRoleServiceImpl(SecurityUserRepository securityUserRepository, SecurityRoleRepository securityRoleRepository)
    {
        this.securityUserRepository = securityUserRepository;
        this.securityRoleRepository = securityRoleRepository;
    }

    @Override
    public boolean userExistsByUsername(String username)
    {
        return securityUserRepository.existsByUsername(username);
    }

    @Override
    public boolean userExistsByEmail(String email)
    {
        return securityUserRepository.existsByEmail(email);
    }

    @Override
    public void createUser(String email, String username, String passwordHash, Collection<String> roles)
    {
        Set<SecurityRole> rolesSet = new HashSet<>();
        if (roles == null)
        {
            SecurityRole userRole = securityRoleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rolesSet.add(userRole);
        }
        else
        {
            roles.forEach(role -> {
                RoleEnum roleEnum = RoleEnum.valueOf(role);  // Enum.valueOf() throws an exception if the input is not valid
                SecurityRole securityRole = securityRoleRepository.findByName(roleEnum).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                rolesSet.add(securityRole);
            });
        }

        SecurityUser securityUser = new SecurityUser(email, username, passwordHash, rolesSet);
        securityUserRepository.save(securityUser);
    }
}
