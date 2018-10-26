package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineTypes;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{
    List<CarDO> findByAvailable(Boolean available);
    
    List<CarDO> findByEngineType(EngineTypes engineType);
    
    CarDO findByLicensePlate(String licensePlate);
    
    List<CarDO> findByRatingGreaterThanEqual(Float rating);

}
