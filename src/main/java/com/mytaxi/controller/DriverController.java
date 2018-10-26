package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.exception.driver.DriverException;
import com.mytaxi.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException, DriverException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/location/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException, DriverException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @PutMapping("/status/{driverId}")
    public void updateStatus(
        @PathVariable long driverId, @RequestParam OnlineStatus status)
        throws EntityNotFoundException, DriverException, CarException
    {
        driverService.updateStatus(driverId, status);
    }

    @PutMapping("/selectCar/{driverId}")
    public void selectCar(
        @PathVariable long driverId, @RequestParam int carId)
        throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException
    {
        driverService.updateDriverCar(driverId, carId);
    }

    @PutMapping("/deselectCar/{driverId}")
    public void deselectCar(
        @PathVariable long driverId)
        throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException
    {
        driverService.removeDriverCar(driverId);
    }
    
    @GetMapping("/status")
    public List<DriverDTO> findDriversByUsernameLike(@RequestParam String name)
    {
        return DriverMapper.makeDriverDTOList(driverService.findByUsernameLike(name));
    }
    
    @GetMapping("/carLicensePlate")
    public DriverDTO findDriversByCarLicensePlate(@RequestParam String licensePlate) throws EntityNotFoundException, CarException
    {
        return DriverMapper.makeDriverDTO(driverService.findByCar(licensePlate));
    }
    
    @GetMapping("/driver/carRatingGT")
    public List<DriverDTO> findDriversByCarRatingGT(@RequestParam Float rating) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTOList(driverService.findByCarRatingGT(rating));
    }

    @GetMapping
    private List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.findByOnlineStatus(onlineStatus));
    }
}
