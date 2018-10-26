package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.exception.driver.DriverException;

public interface DriverService
{

	DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

	DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO findByCar(String licensePlate) throws EntityNotFoundException, CarException;

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    List<DriverDO> findByUsernameLike(String name);

	List<DriverDO> findByCarRatingGT(Float rating) throws EntityNotFoundException;

	void delete(Long driverId) throws EntityNotFoundException, DriverException;
	
	void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException, DriverException;
	
	void updateStatus(long driverId, OnlineStatus status) throws EntityNotFoundException, DriverException, CarException;
	
	void updateDriverCar(long driverId, long carId) throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException;
	
	void removeDriverCar(long driverId) throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException;
	
}
