package com.mytaxi.service.driver;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarAlreadyInUseException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.exception.car.CarNotAvailableException;
import com.mytaxi.exception.car.InvalidCarException;
import com.mytaxi.exception.driver.DriverException;
import com.mytaxi.exception.driver.InvalidDriverException;
import com.mytaxi.service.car.CarService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final DriverRepository driverRepository;
	private final CarService carService;

	public DefaultDriverService(final DriverRepository driverRepository, final CarService carService) {
		this.driverRepository = driverRepository;
		this.carService = carService;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param driverId
	 * @return found driver
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	public DriverDO find(Long driverId) throws EntityNotFoundException {
		return findDriverChecked(driverId);
	}

	/**
	 * Creates a new driver.
	 *
	 * @param driverDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a driver already exists with the given username, ... .
	 */
	@Override
	public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
		DriverDO driver;
		try {
			driver = driverRepository.save(driverDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return driver;
	}

	/**
	 * Deletes an existing driver by id.
	 *
	 * @param driverId
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long driverId) throws EntityNotFoundException,InvalidDriverException {
		DriverDO driverDO = findDriverChecked(driverId);
		if(driverDO.getDeleted())
			throw new InvalidDriverException("Driver already Deleted");
		driverDO.setOnlineStatus(OnlineStatus.OFFLINE);
		driverDO.setDeleted(true);
	}

	/**
	 * Update the location for a driver.
	 *
	 * @param driverId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException, InvalidDriverException {
		DriverDO driverDO = findDriverChecked(driverId);
		if(driverDO.getDeleted())
			throw new InvalidDriverException("Driver is Deleted");
		driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
	}

	/**
	 * Update the selected car for a driver. If already selected and new selection different then updated the selection
	 *
	 * @param driverId
	 * @param carId
	 * @throws EntityNotFoundException,
	 *             CloneNotSupportedException, CarAlreadyInUseException,
	 *             CarNotAvailableException
	 * @throws CloneNotSupportedException 
	 */
	@Override
	@Transactional
	public void updateDriverCar(long driverId, long carId) throws EntityNotFoundException, DriverException, CarException, CloneNotSupportedException {
		DriverDO driverDO = findDriverChecked(driverId);
		if(driverDO.getDeleted())
			throw new InvalidDriverException("Driver is Deleted");
		if (driverDO.getOnlineStatus().equals(OnlineStatus.ONLINE)) {
			CarDO carDO = carService.findCarForDriver(driverId, carId);
			LOG.debug("Driver with ID: " + driverDO + " requested Car: " + carDO);
			if (carDO.getDeleted())
				throw new InvalidCarException("Car is Deleted");
			if (carDO.getAvailable()) {
				// Driver does not have a Car associated
				if (driverDO.getCar() == null) {
					LOG.debug("Driver does not have a car" + carDO);
					carDO.setAvailable(Boolean.FALSE);
					carDO.setCarDriverId(driverId);
					carService.updateCar(carId, carDO);
				} 
				// Change car associated with driver
				else {
					// Update Current selected Car as Available
					CarDO currentCarDO = driverDO.getCar();
					LOG.debug("Driver already has a car" + currentCarDO);
					currentCarDO.setAvailable(Boolean.TRUE);
					currentCarDO.setCarDriverId(null);
					carService.updateCar(currentCarDO.getId(), currentCarDO);

					// Update New selected Car as UnAvailable
					carDO.setAvailable(Boolean.FALSE);
					carDO.setCarDriverId(driverId);
					carService.updateCar(carId, carDO);
				}
				driverDO.setCar(carDO);
			} else if (!carDO.getAvailable() && driverDO.getCar() != null
					&& carDO.getId().equals(driverDO.getCar().getId())) {
				LOG.debug("Requested Car Already Selected" + carDO);
				throw new CarAlreadyInUseException("Requested Car Already in USE");
			} else {
				LOG.debug("Requested Car Not Available: " + carDO);
				throw new CarNotAvailableException("Requested Car Not Available");
			}
		}
	}

	/**
	 * Remove the already selected car for a driver.
	 *
	 * @param driverId
	 * @throws EntityNotFoundException,CloneNotSupportedException
	 * @throws CarException 
	 */
	@Override
	@Transactional
	public void removeDriverCar(long driverId) throws EntityNotFoundException, InvalidDriverException, CloneNotSupportedException, CarException {
		DriverDO driverDO = findDriverChecked(driverId);
		if(driverDO.getDeleted())
			throw new InvalidDriverException("Driver is Deleted");
		CarDO currentCarDO = driverDO.getCar();
		LOG.debug("Driver with ID: " + driverDO + " has current Car: " + currentCarDO);
		if (currentCarDO == null) {
			LOG.debug("Driver with ID: " + driverDO + " does not have any car associated");
			return;
		}
		currentCarDO.setAvailable(Boolean.TRUE);
		currentCarDO.setCarDriverId(null);
		carService.updateCar(currentCarDO.getId(), currentCarDO);
		driverDO.setCar(null);
	}

	/**
	 * Update the Online state of the Driver.
	 *
	 * @param onlineStatus
	 * @throws EntityNotFoundException
	 * @throws CarException 
	 */
	@Override
	@Transactional
	public void updateStatus(long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException, InvalidDriverException, CarException {
		DriverDO driverDO = findDriverChecked(driverId);
		if(driverDO.getDeleted())
			throw new InvalidDriverException("Driver is Deleted");
		// Check for car selected and update car to Available and remove from Driver
		if(onlineStatus.equals(OnlineStatus.OFFLINE) && driverDO.getCar() != null) {
			carService.updateAvailable(driverDO.getCar().getId(), Boolean.TRUE);
			driverDO.setCar(null);
		}
		driverDO.setOnlineStatus(onlineStatus);
	}

	/**
	 * Find all drivers by online state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus) {
		return driverRepository.findByOnlineStatus(onlineStatus);
	}

	/**
	 * Find all drivers by username wild card appended.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<DriverDO> findByUsernameLike(String name) {
    	String wildcardAppended = "%" + name + "%";
		return driverRepository.findByUsernameLike(wildcardAppended);
	}

	/**
	 * Find all drivers by car attribute - LicensePlate.
	 *
	 * @param onlineStatus
	 * @throws EntityNotFoundException 
	 * @throws CarNotAvailableException 
	 */
	@Override
	public DriverDO findByCar(String licensePlate) throws EntityNotFoundException, InvalidCarException, CarNotAvailableException {
		CarDO carDO = carService.find(licensePlate);
		if(carDO == null)
			throw new CarNotAvailableException("Cannot find car with licensePlate: " + licensePlate);
		if(carDO.getDeleted())
			throw new InvalidCarException("Car is deleted");
		DriverDO driver =  driverRepository.findByCar(carDO);
		if(driver != null) {
			return driver;
		}
		throw new EntityNotFoundException("Could not find entity with id: " + carDO.getId());
	}

	/**
	 * Find all drivers by car attribute - Rating.
	 *
	 * @param onlineStatus
	 * @throws EntityNotFoundException 
	 */
	@Override
	public List<DriverDO> findByCarRatingGT(Float rating) throws EntityNotFoundException {
		List<DriverDO> drivers = new ArrayList<>();
		carService.findByRatingGreaterThanEqual(rating).forEach(car -> 
		{if(driverRepository.findByCar(car) != null)
				drivers.add(driverRepository.findByCar(car));});
		if(!drivers.isEmpty())
			return drivers;
		throw new EntityNotFoundException("Could not find entity with rating: " + rating);
	}

	
	private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
		return driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with rating: " + driverId));
	}

}
