package ba.sema.springbootvuedemo.services;

import ba.sema.springbootvuedemo.entities.Car;

import java.util.List;

public interface CarService
{
    List<Car> getCars();
    Car getCar(int id);
    void deleteCar(int id);
    Car createCar(Car car);
    Car updateCar(int id, Car car);
}
