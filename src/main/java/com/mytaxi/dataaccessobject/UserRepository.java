package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.UserDO;

/**
 * Database Access Object for user table.
 * <p/>
 */
public interface UserRepository extends CrudRepository<UserDO, Long>
{

    UserDO findByUsername(String username);
    
    List<UserDO> findAll();

	List<UserDO> findByRole(String role);

}
