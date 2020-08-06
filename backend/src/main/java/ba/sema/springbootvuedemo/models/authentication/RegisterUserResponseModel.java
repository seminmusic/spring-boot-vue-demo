package ba.sema.springbootvuedemo.models.authentication;

import lombok.Data;

@Data
public class RegisterUserResponseModel
{
    private String message;

    public RegisterUserResponseModel(String message)
    {
        this.message = message;
    }
}
