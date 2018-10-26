package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.CarManufacturer;
import com.mytaxi.domainvalue.EngineTypes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

	@JsonIgnore
	private Long id;

	@NotNull(message = "License Plate can not be null!")
	private String licensePlate;

	@NotNull(message = "Seat Count can not be null!")
	private Integer seatCount;

	@NotNull(message = "Engine type can not be null")
	private EngineTypes engineType;

	private CarManufacturer manufacturer;
	
	private CarDTO() {
	}

	private CarDTO(Long id, String licensePlate, Integer seatCount, EngineTypes engineType, CarManufacturer manufacturer) {
		this.id = id;
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.engineType = engineType;
		this.manufacturer = manufacturer;
	}

	public static CarDTOBuilder newBuilder() {
		return new CarDTOBuilder();
	}
	
	@JsonProperty
	public Long getId() {
		return id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public EngineTypes getEngineType() {
		return engineType;
	}

	public CarManufacturer getManufacturer() {
		return manufacturer;
	}


	public static class CarDTOBuilder {
		private Long id;
		private String licensePlate;
		private Integer seatCount;
		private EngineTypes engineType;
		private CarManufacturer manufacturer;

		public CarDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public CarDTOBuilder setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
			return this;
		}

		public CarDTOBuilder setSeatCount(Integer seatCount) {
			this.seatCount = seatCount;
			return this;
		}

		public CarDTOBuilder setEngineType(EngineTypes engineType) {
			this.engineType = engineType;
			return this;
		}

		public CarDTOBuilder setManufacturer(CarManufacturer manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}
		
		public CarDTO createCarDTO() {
			return new CarDTO(id, licensePlate, seatCount, engineType, manufacturer);
		}

	}
}
