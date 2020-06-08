package ba.sema.springbootvuedemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TestModel
{
    private Integer id;
    private String name;
    private String address;
    private LocalDateTime activated;
}
