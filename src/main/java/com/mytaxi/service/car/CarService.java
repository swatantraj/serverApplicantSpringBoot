package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineTypes;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.exception.car.InvalidCarException;

public interface CarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    CarDO findCarForDriver(Long driverId, Long carId) throws EntityNotFoundException;
    
    CarDO find(String licensePlate) throws EntityNotFoundException;
    
    void delete(Long carId) throws EntityNotFoundException, CarException;

    void updateAvailable(long carId, Boolean available) throws EntityNotFoundException, CarException;

    void updateSeatCount(long carId, Integer seatCount) throws EntityNotFoundException, CarException;

    void updateCar(long carId, CarDO car) throws EntityNotFoundException, CloneNotSupportedException, CarException;

    List<CarDO> find(Boolean available);

    List<CarDO> find(EngineTypes engineType);
    
    List<CarDO> findByRatingGreaterThanEqual(Float rating) throws EntityNotFoundException;
}
