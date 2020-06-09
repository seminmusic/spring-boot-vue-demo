package ba.sema.springbootvuedemo.services;

import ba.sema.springbootvuedemo.entities.Car;
import ba.sema.springbootvuedemo.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService
{
    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars()
    {
        return carRepository.findAll();
    }
}
