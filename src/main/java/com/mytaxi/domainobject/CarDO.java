package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.CarManufacturer;
import com.mytaxi.domainvalue.EngineTypes;

@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = { "licenseplate" }))
public class CarDO implements Cloneable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "License Plate can not be null!")
	private String licensePlate;

	@Column(nullable = false)
	@NotNull(message = "Seat Count can not be null!")
	private Integer seatCount;

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column(nullable = false)
	private Boolean convertible = false;

	@Column(nullable = false)
	private Boolean available = false;

	// Added column definition to contain floating overflow 
	@Column(nullable = false, columnDefinition="NUMBER")
	@NotNull(message = "Rating can not be null!")
	private Float rating;

	@Embedded
	private CarManufacturer manufacturer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EngineTypes engineType;

	@Column
	private Long carDriverId;

	private CarDO() {
	}

	public CarDO(String licensePlate, Integer seatCount, EngineTypes engineType, CarManufacturer manufacturer) {
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.deleted = false;
		this.convertible = false;
		this.available = false;
		this.rating = 5.0f;
		this.engineType = engineType;
		this.manufacturer = manufacturer;
		this.carDriverId = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getConvertible() {
		return convertible;
	}

	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public CarManufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(CarManufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public EngineTypes getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineTypes engineType) {
		this.engineType = engineType;
	}

	@Override
	public String toString() {
		return "CarLicensePlate = " + getLicensePlate() +  "||Available = " + getAvailable();
	}
	
	public Long getCarDriverId() {
		return carDriverId;
	}

	public void setCarDriverId(Long carDriverId) {
		this.carDriverId = carDriverId;
	}

	@Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.licensePlate == null) ? 0 : this.licensePlate.hashCode());
        return result;
    }


    @Override
    public boolean equals(final Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final CarDO other = (CarDO) obj;
		
	    return ((this.getLicensePlate().equals(other.getLicensePlate())));
	}

	@Override
	public CarDO clone() throws CloneNotSupportedException{
		CarDO cloneCar = new CarDO();
		// perform Deep Copy while Cloning
		cloneCar.dateCreated = this.dateCreated;
		cloneCar.licensePlate = this.licensePlate;
		cloneCar.seatCount = this.seatCount;
		cloneCar.deleted = this.deleted;
		cloneCar.convertible = this.convertible;
		cloneCar.available = this.available;
		cloneCar.rating = this.rating;
		cloneCar.engineType = this.engineType;
		cloneCar.manufacturer = this.manufacturer;
		cloneCar.carDriverId = this.carDriverId;
		return cloneCar;
	}

}
