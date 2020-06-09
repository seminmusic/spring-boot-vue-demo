package ba.sema.springbootvuedemo.controllers;

import ba.sema.springbootvuedemo.entities.Car;
import ba.sema.springbootvuedemo.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController
{
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    @Autowired
    public CarController(CarService carService)
    {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAll()
    {
        return carService.getAllCars();
    }
}
