package com.mytaxi.dataaccessobject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.TestConfigurer;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.CarManufacturer;
import com.mytaxi.domainvalue.EngineTypes;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes={TestConfigurer.class})
public class CarRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	CarRepository carRepository;
	
	@Test
	public void whenFindByName_thenReturnEmployee() {
	    // given
	    CarDO car1 = new CarDO("Car1", 3, EngineTypes.GAS_DIESEL, new CarManufacturer("Com1", "2018"));
	    entityManager.persist(car1);
//	    car1.setId(11l);
	    entityManager.merge(car1);
	    
	    entityManager.flush();
	 
	    // when
	    CarDO found = carRepository.findByLicensePlate("Car1");
	 
	    // then
	    assertThat(found.getLicensePlate()).isEqualTo(car1.getLicensePlate());
	}

}
