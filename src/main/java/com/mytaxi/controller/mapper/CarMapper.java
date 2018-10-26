package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.CarManufacturer;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO carDTO)
    {
        return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.getManufacturer());
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(carDO.getId())
            .setLicensePlate(carDO.getLicensePlate())
            .setSeatCount(carDO.getSeatCount())
            .setEngineType(carDO.getEngineType());

        CarManufacturer manufacturer = carDO.getManufacturer();
        if (manufacturer != null)
        {
            carDTOBuilder.setManufacturer(manufacturer);
        }
        
        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
