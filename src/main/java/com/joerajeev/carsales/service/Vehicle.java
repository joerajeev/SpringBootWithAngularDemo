package com.joerajeev.carsales.service;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Domain class representing a Vehicle in the system
 * 
 * @author Rajeev
 *
 */
public class Vehicle {

	@NotEmpty(message="Cannot be empty")
	private String reg;
	
	@Range(min = 1900, max = 9999, message="Should be a valid year")
	private Integer year;
	
	@Range(min = 0, message="Should be a valid milage")
	private Integer milage;
	
	@NotEmpty(message="Cannot be empty")
	private String colour;
	
	@NotEmpty(message="Cannot be empty")
	private String make;
	
	@NotEmpty(message="Cannot be empty")
	private String model;
	
	private int owner;
	/**
	 * @return the reg
	 */
	public String getReg() {
		return reg;
	}
	/**
	 * @param reg the reg to set
	 */
	public void setReg(String reg) {
		this.reg = reg;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}
	/**
	 * @return the milage
	 */
	public Integer getMilage() {
		return milage;
	}
	/**
	 * @param milage the milage to set
	 */
	public void setMilage(Integer milage) {
		this.milage = milage;
	}
	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}
	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}
	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + milage;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + owner;
		result = prime * result + ((reg == null) ? 0 : reg.hashCode());
		result = prime * result + year;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (milage != other.milage)
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (owner != other.owner)
			return false;
		if (reg == null) {
			if (other.reg != null)
				return false;
		} else if (!reg.equals(other.reg))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehicle [reg=" + reg + ", year=" + year + ", owner=" + owner + ", milage=" + milage + ", colour="
				+ colour + ", make=" + make + ", model=" + model + "]";
	}
}
