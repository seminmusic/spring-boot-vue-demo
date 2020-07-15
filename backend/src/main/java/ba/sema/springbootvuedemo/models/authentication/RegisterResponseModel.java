package ba.sema.springbootvuedemo.models.authentication;

import lombok.Data;

@Data
public class RegisterResponseModel
{
    private String message;

    public RegisterResponseModel(String message)
    {
        this.message = message;
    }
}
