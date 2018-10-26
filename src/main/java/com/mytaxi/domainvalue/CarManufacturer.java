package com.mytaxi.domainvalue;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class CarManufacturer
{

    @Column(name = "companyName")
    private final String companyName;

    @Column(name = "model")
    private final String model;

    protected CarManufacturer()
    {
    	this.companyName = "";
    	this.model = "";
    }


    /**
     * @param companyName  - manufacturing company
     * @param model - Year and Variant of car manufactured
     */
    public CarManufacturer(String companyName, String model)
    {
        this.companyName = companyName;
        this.model = model;
    }

    @JsonProperty
    public String getCompanyName() {
		return companyName;
	}

    @JsonProperty
	public String getModel() {
		return model;
	}


	@Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.companyName == null || this.model == null) ? 0 : this.model.hashCode());
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
		final CarManufacturer other = (CarManufacturer) obj;
		
	    return ((this.getModel() == other.getModel()) && (this.getCompanyName() == other.getCompanyName()));
	}


    @Override
    public String toString()
    {
        return this.getCompanyName() + " - " + this.getModel();
    }

}
