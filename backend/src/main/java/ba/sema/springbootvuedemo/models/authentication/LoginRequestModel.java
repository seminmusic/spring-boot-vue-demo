package ba.sema.springbootvuedemo.models.authentication;

import lombok.Data;

// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequestModel
{
    // @NotBlank - not working on WildFly, more info how to resolve this:
    // https://stackoverflow.com/questions/49706795/notblank-not-recognised-in-wildfly-hibernate
    // https://in.relation.to/2017/04/04/testing-bean-validation-2-0-on-wildfly-10/

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 1)
    private String password;
}
