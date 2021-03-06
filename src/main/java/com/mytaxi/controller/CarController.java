package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineTypes;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.service.car.CarService;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@Component
@RestController
@RequestMapping("v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException, CarException
    {
        carService.delete(carId);
    }


    @PutMapping("/availabile/{carId}")
    public void updateAvailabile(
        @PathVariable long carId, @RequestParam Boolean availabile)
        throws EntityNotFoundException, CarException
    {
        carService.updateAvailable(carId, availabile);
    }

    @PutMapping("/seatCount/{carId}")
    public void updateSeatCount(
        @PathVariable long carId, @RequestParam Integer seatCount)
        throws EntityNotFoundException, CarException
    {
        carService.updateSeatCount(carId, seatCount);
    }


    @GetMapping("/available")
    public List<CarDTO> findCars(@RequestParam Boolean available)
    {
        return CarMapper.makeCarDTOList(carService.find(available));
    }

    @GetMapping("/engineType")
    public List<CarDTO> findCars(@RequestParam EngineTypes engineType)
    {
        return CarMapper.makeCarDTOList(carService.find(engineType));
    }
    
}
