package ba.sema.springbootvuedemo.controllers;

import ba.sema.springbootvuedemo.entities.Car;
import ba.sema.springbootvuedemo.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Car> getCars()
    {
        return carService.getCars();
    }

    @GetMapping("{id}")
    public Car getCar(@PathVariable int id)
    {
        return carService.getCar(id);
    }

    @DeleteMapping("{id}")
    public void deleteCar(@PathVariable int id)
    {
        carService.deleteCar(id);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car)
    {
        return carService.createCar(car);
    }

    @PutMapping("{id}")
    public Car updateCar(@PathVariable int id, @RequestBody Car car)
    {
        return carService.updateCar(id, car);
    }
}
