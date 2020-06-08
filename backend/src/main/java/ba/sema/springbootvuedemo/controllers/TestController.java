package ba.sema.springbootvuedemo.controllers;

import ba.sema.springbootvuedemo.models.TestModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TestController
{
    @GetMapping("test-data")
    public TestModel getTestModel()
    {
        return new TestModel(5, "Semin Musić", "Trg heroja 10", LocalDateTime.now());
    }
}
