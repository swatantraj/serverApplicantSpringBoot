package com.mytaxi.service.car;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineTypes;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarNotAvailableException;
import com.mytaxi.exception.car.InvalidCarException;
import com.mytaxi.util.LogExecutionTime;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

	private final CarRepository carRepository;

	public DefaultCarService(final CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	/**
	 * Selects a car by id.
	 *
	 * @param carId
	 * @return found car
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 */
	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		return findCarChecked(carId);
	}

	/**
	 * Creates a new car.
	 *
	 * @param carDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a car already exists with the given licensePlate, ... .
	 */
	@Override
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {
		CarDO car;
		try {
			car = carRepository.save(carDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return car;
	}

	/**
	 * Deletes an existing car by id.
	 *
	 * @param carId
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 * @throws InvalidCarException 
	 * @throws CarNotAvailableException 
	 */
	@Override
	@Transactional
	@LogExecutionTime
	public void delete(Long carId) throws EntityNotFoundException, InvalidCarException, CarNotAvailableException {
		CarDO carDO = findCarChecked(carId);
		if(carDO.getDeleted())
			throw new InvalidCarException("Car is deleted");
		if(!carDO.getAvailable()) {
			if(carDO.getCarDriverId() != null)
				throw new CarNotAvailableException("Car currently being Used");
		}
		carDO.setAvailable(false);
		carDO.setDeleted(true);
	}

	/**
	 * Update the availability for a car.
	 *
	 * @param carId
	 * @param available
	 * @throws EntityNotFoundException
	 * @throws InvalidCarException 
	 */
	@Override
	@Transactional
	@LogExecutionTime
	public void updateAvailable(long carId, Boolean available) throws EntityNotFoundException, InvalidCarException {
		CarDO carDO = findCarChecked(carId);
		if(carDO.getDeleted())
			throw new InvalidCarException("Car is deleted");
		carDO.setAvailable(available);
	}

	/**
	 * Update the seat count for a car.
	 *
	 * @param carId
	 * @param seatCount
	 * @throws EntityNotFoundException
	 * @throws InvalidCarException 
	 */
	@Override
	@Transactional
	public void updateSeatCount(long carId, Integer seatCount) throws EntityNotFoundException, InvalidCarException {
		CarDO carDO = findCarChecked(carId);
		if(carDO.getDeleted())
			throw new InvalidCarException("Car is deleted");
		carDO.setSeatCount(seatCount);
	}

	/**
	 * Find all cars by Available state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<CarDO> find(Boolean available) {
		return carRepository.findByAvailable(available);
	}

	/**
	 * Find all cars by Engine Type state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<CarDO> find(EngineTypes engineType) {
		return carRepository.findByEngineType(engineType);
	}

	/**
	 * Find all cars by LicensePlate Type state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public CarDO find(String licensePlate) {
		return carRepository.findByLicensePlate(licensePlate);
	}

	/**
	 * Find all cars by with rating greater than.
	 * 
	 * @param rating
	 * @throws EntityNotFoundException
	 */
	@Override
	public List<CarDO> findByRatingGreaterThanEqual(Float rating) throws EntityNotFoundException {
		return carRepository.findByRatingGreaterThanEqual(rating);
	}
	
	/**
	 * Update the car with Id with the car passed
	 *
	 * @param carId
	 * @param car
	 * @throws EntityNotFoundException
	 * @throws InvalidCarException 
	 */
	@Override
	@Transactional
	public void updateCar(long carId, CarDO car) throws EntityNotFoundException, CloneNotSupportedException, InvalidCarException {
		CarDO carDO = findCarChecked(carId);
		if(carDO.getDeleted())
			throw new InvalidCarException("Car is deleted");
		carDO = car.clone();
	}

	/**
	 * Find the car with the id -> public 
	 * 
	 * @param driverId
	 * @param carId
	 * @throws EntityNotFoundException
	 */
	@Override
	public CarDO findCarForDriver(Long driverId, Long carId) throws EntityNotFoundException {
		LOG.error("Driver with ID: " + driverId + " requested Car: " + carId);
		return carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
	}

	private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
		return carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
	}


}
