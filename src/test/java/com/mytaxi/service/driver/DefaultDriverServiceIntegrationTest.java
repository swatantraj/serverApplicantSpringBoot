package com.mytaxi.service.driver;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mytaxi.TestConfigurer;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.CarManufacturer;
import com.mytaxi.domainvalue.EngineTypes;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.car.CarAlreadyInUseException;
import com.mytaxi.exception.car.CarException;
import com.mytaxi.exception.car.CarNotAvailableException;
import com.mytaxi.exception.driver.DriverException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.car.DefaultCarService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes={TestConfigurer.class})
public class DefaultDriverServiceIntegrationTest {

//	@Mock
//	private static DriverRepository driverRepository;
//	
//	@Mock
//	private static CarRepository carRepository;
//
	@Autowired
	private DriverService driverService;
//
	@Autowired
	private CarService carService;
//	@TestConfiguration
//	static class DriverServiceImplTestContextConfiguration {
//		
//		@Bean
//		public CarService carService() {
//			return new DefaultCarService(carRepository);
//		}
//		
//		@Bean
//		public DriverService driverService() {
//			return new DefaultDriverService(driverRepository,carService());
//		}
//	}
//
//	@Before
//	public void setUp() {
//		DriverDO driver1 = new DriverDO("Driver1", "Pass1");
//		DriverDO driver2 = new DriverDO("Driver2", "Pass2");
//		DriverDO driver3 = new DriverDO("Driver3", "Pass3");
//		DriverDO driver4 = new DriverDO("Driver4", "Pass4");
//		
//		CarDO car1 = new CarDO("Car1", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2018SV"));
//		CarDO car2 = new CarDO("Car2", 2, EngineTypes.ELECTRIC, new CarManufacturer("ComNAME", "2019SV"));
//		CarDO car3 = new CarDO("Car3", 2, EngineTypes.ELECTRIC, new CarManufacturer("ComNAME", "2017SV"));
////		CarDO car4 = new CarDO("Car4", 1, EngineTypes.GAS_PETROL, new CarManufacturer("ComNAME", "2011SV"));
//		
//		// Test1
//		driver2.setId(2l);
//		Mockito.when(driverRepository.findById(2l)).thenReturn(Optional.of(driver2));
//		
//		//Test2
//		List<DriverDO> drivers = new ArrayList<>();
//		drivers.add(driver1);
//		drivers.add(driver4);
//		Mockito.when(driverRepository.findByOnlineStatus(OnlineStatus.ONLINE)).thenReturn(drivers);
//
//		//Test3
//		Mockito.when(driverRepository.findByUsernameLike("Driver")).thenReturn(drivers);
//		
//		//Test4
//		Mockito.when(carRepository.findByLicensePlate("Car1")).thenReturn(car1);
//		Mockito.when(driverRepository.findByCar(car1)).thenReturn(driver4);
//		
//		//Test5
//		List<CarDO> cars = new ArrayList<>();
//		car1.setRating(4.7f);
//		car2.setRating(5f);
//		driver1.setCar(car2);
//		driver4.setCar(car1);//Test7
//		cars.add(car1);
//		cars.add(car2);
//		Mockito.when(carRepository.findByRatingGreaterThanEqual(4.7f)).thenReturn(cars);
//		Mockito.when(driverRepository.findByCar(car2)).thenReturn(driver1);
//		
//		//Test6
//		driver3.setId(3l);
//		driver3.setOnlineStatus(OnlineStatus.ONLINE);
//		car2.setId(2l);
//		car2.setAvailable(true);
//		Mockito.when(driverRepository.findById(3l)).thenReturn(Optional.of(driver3));
//		Mockito.when(carRepository.findById(2l)).thenReturn(Optional.of(car2));
//		
//		//Test7
//		driver4.setId(4l);
//		driver4.setOnlineStatus(OnlineStatus.ONLINE);
//		car1.setAvailable(false);// Already default false
//		car1.setId(1l);
//		Mockito.when(driverRepository.findById(4l)).thenReturn(Optional.of(driver4));
//		Mockito.when(carRepository.findById(1l)).thenReturn(Optional.of(car1));
//
//		//Test8
//		Mockito.when(carRepository.findById(3l)).thenReturn(Optional.of(car3));
//		
//	}


	@Test //1
	public void whenValidId_thenDriverShouldBeFound() throws EntityNotFoundException {
		Long id = 2l;
		DriverDO found = driverService.find(id);

		Assert.assertEquals(found.getId(), id);
	}

	@Test //2
	public void whenSearchOnlineStatus_thenDriversShouldBeFound() throws EntityNotFoundException {
		List<DriverDO> drivers = new ArrayList<DriverDO>();
		drivers.add(new DriverDO("driver05", "****"));
		drivers.add(new DriverDO("driver04", "****"));
		drivers.add(new DriverDO("driver06", "****"));
		drivers.add(new DriverDO("driver08", "****"));
		
		List<DriverDO> driversFound = driverService.findByOnlineStatus(OnlineStatus.ONLINE);
		Collections.sort(drivers,(o1, o2) -> o1.getUsername().compareTo(o2.getUsername()));
		Collections.sort(driversFound,(o1, o2) -> o1.getUsername().compareTo(o2.getUsername()));

		Assert.assertTrue(driversFound.equals(drivers));
	}

	@Test //3
	public void whenUsername_thenDriversShouldBeFound() throws EntityNotFoundException {

		List<String> names = new ArrayList<>();
		String username = "driver"; 
		
		List<DriverDO> driversFound = driverService.findByUsernameLike(username);
		driversFound.forEach(c -> names.add(c.getUsername()));
		
        assertThat(names, everyItem(containsString(username)));
	}


	@Test //4
	public void whenCar_thenDriverShouldBeFound() throws EntityNotFoundException, CarException, CloneNotSupportedException, DriverException {
		String license = "AB06-AB6789";
		
		driverService.updateDriverCar(5l, carService.find(license).getId());

		DriverDO found = driverService.findByCar(license);
		
		Assert.assertEquals(found.getId(), new Long(5));
	}


	@Test //5
	public void whenRatingGT_thenDriversShouldBeFound() throws EntityNotFoundException {
		List<Float> ratings = new ArrayList<>();
		Float rating  = 4.7f;
		
		List<DriverDO> driversFound = driverService.findByCarRatingGT(rating);
		driversFound.forEach(d -> ratings.add(d.getCar().getRating()));

        assertThat(ratings, everyItem(greaterThanOrEqualTo(rating)));
	}

	@Test //6
	public void whenDriverAskCar_thenUpdatecar() throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException {
		driverService.updateDriverCar(3l, 2l);
		Assert.assertTrue(true);
	}

	@Test(expected=CarAlreadyInUseException.class) //7
	public void whenDriverAskAlreadySelected_thenThrowException() throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException {
		driverService.updateDriverCar(4l, 1l);
	}

	@Test(expected=CarNotAvailableException.class) //8
	public void whenDriverAskNotAvailable_thenThrowException() throws EntityNotFoundException, CloneNotSupportedException, DriverException, CarException {
		driverService.updateDriverCar(4l, 3l);
	}

}