package ba.sema.springbootvuedemo.services;

import java.util.Collection;

public interface SecurityUserRoleService
{
    boolean userExistsByUsername(String username);
    boolean userExistsByEmail(String email);
    void createUser(String email, String username, String passwordHash, Collection<String> roles);
}
