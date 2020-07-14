package ba.sema.springbootvuedemo.repositories;

import ba.sema.springbootvuedemo.entities.SecurityRole;
import ba.sema.springbootvuedemo.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Integer>
{
    Optional<SecurityRole> findByName(RoleEnum name);
}
