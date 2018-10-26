package com.mytaxi.service.user;

import java.util.List;

import com.mytaxi.domainobject.UserDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface UserService
{
    UserDO find(Long userId) throws EntityNotFoundException;

    UserDO create(UserDO userDO) throws ConstraintsViolationException;
    
    UserDO findUserByName(String username) throws EntityNotFoundException;

    List<UserDO> getUsers() throws EntityNotFoundException;

    List<UserDO> findUsersByRole(String role) throws EntityNotFoundException;

}
