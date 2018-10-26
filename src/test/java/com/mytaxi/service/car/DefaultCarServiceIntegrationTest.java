package com.mytaxi.service.car;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
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
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.CarManufacturer;
import com.mytaxi.domainvalue.EngineTypes;
import com.mytaxi.exception.EntityNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes={TestConfigurer.class})
public class DefaultCarServiceIntegrationTest {

	@Autowired
	private CarService carService;

	@Test //1
	public void whenValidId_thenCarShouldBeFound() throws EntityNotFoundException {
		Long id = 1l;
		CarDO found = carService.find(id);
		
		Assert.assertEquals(found.getId(), id);
	}

	@Test //2
	public void whenValidLicense_thenCarShouldBeFound() throws EntityNotFoundException {
		String licensePlate = "AB01-AB1234";
		CarDO found = carService.find(licensePlate);

		Assert.assertArrayEquals(found.getLicensePlate().toCharArray(), licensePlate.toCharArray());
	}


	@Test //3
	public void whenRating_thenCarsShouldBeFound() throws EntityNotFoundException {
		List<Float> ratings = new ArrayList<>();
		Float rating = 4.7f;
		
		List<CarDO> carsFound = carService.findByRatingGreaterThanEqual(4.7f);
		carsFound.forEach(c -> ratings.add(c.getRating()));
		
        assertThat(ratings, everyItem(greaterThanOrEqualTo(rating)));
	}

	@Test //4
	public void whenSearchEngineType_thenCarsShouldBeFound() throws EntityNotFoundException {
		List<CarDO> cars = new ArrayList<CarDO>();
		cars.add(new CarDO("AB02-AB2345", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2018SV")));
		cars.add(new CarDO("AB04-AB4567", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2017SV")));
		
		List<CarDO> carsFound = carService.find(EngineTypes.GAS_DIESEL);
		
		Assert.assertTrue(carsFound.equals(cars));
	}

	@Test //5
	public void whenSearchAvailability_thenAvailableCarsShouldBeFound() throws EntityNotFoundException {
		List<CarDO> cars = new ArrayList<>();
		cars.add(new CarDO("AB04-AB4567", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2017SV")));
		cars.add(new CarDO("AB06-AB6789", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2017SV")));
		cars.add(new CarDO("AB05-AB5678", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("ComNAME", "2018SV")));
		boolean available = true;
		List<CarDO> carsFound = carService.find(available);
		Collections.sort(cars,(o1, o2) -> o1.getLicensePlate().compareTo(o2.getLicensePlate()));
		Collections.sort(carsFound,(o1, o2) -> o1.getLicensePlate().compareTo(o2.getLicensePlate()));

		Assert.assertEquals(carsFound, cars);
	}

}