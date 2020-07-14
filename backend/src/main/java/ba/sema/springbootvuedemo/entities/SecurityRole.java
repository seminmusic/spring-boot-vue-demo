package ba.sema.springbootvuedemo.entities;

import ba.sema.springbootvuedemo.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table
(
    name = "security_roles",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class SecurityRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    //region Constructors
    public SecurityRole()
    {
    }

    public SecurityRole(RoleEnum name)
    {
        this.name = name;
    }
    //endregion

    //region Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
    //endregion
}
