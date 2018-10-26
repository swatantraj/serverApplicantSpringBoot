package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.UserDTO;
import com.mytaxi.domainobject.UserDO;

public class UserMapper
{
    public static UserDO makeUserDO(UserDTO userDTO)
    {
        return new UserDO(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
    }


    public static UserDTO makeUserDTO(UserDO userDO)
    {
        UserDTO.UserDTOBuilder userDTOBuilder = UserDTO.newBuilder()
            .setId(userDO.getId())
            .setPassword(userDO.getPassword())
            .setUsername(userDO.getUsername())
            .setRole(userDO.getRole());

        return userDTOBuilder.createUserDTO();
    }


    public static List<UserDTO> makeUserDTOList(Collection<UserDO> users)
    {
        return users.stream()
            .map(UserMapper::makeUserDTO)
            .collect(Collectors.toList());
    }
}
