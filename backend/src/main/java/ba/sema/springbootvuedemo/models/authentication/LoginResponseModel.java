package ba.sema.springbootvuedemo.models.authentication;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseModel
{
    private String type = "Bearer";
    private String token;
    private Long id;
    private String email;
    private String username;
    private List<String> roles;

    public LoginResponseModel(String token, Long id, String email, String username, List<String> roles)
    {
        this.token = token;
        this.id = id;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }
}
