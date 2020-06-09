package ba.sema.springbootvuedemo.repositories;

import ba.sema.springbootvuedemo.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>
{
}
