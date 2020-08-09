package ba.sema.springbootvuedemo.models.authentication;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterUserRequestModel
{
    @NotNull
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> roles;
}
