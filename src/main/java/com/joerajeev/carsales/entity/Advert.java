package com.joerajeev.carsales.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the advert database table.
 * 
 */
@Entity
@NamedQuery(name="Advert.findAll", query="SELECT a FROM Advert a")
public class Advert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.DATE)
	private Date created;

	private String description;

	//Path to the image
	private String image;

	@Temporal(TemporalType.DATE)
	private Date modified;

	//bi-directional many-to-one association to Vehicle
	@ManyToOne
	@JoinColumn(name="reg")
	private Vehicle vehicle;

	public Advert() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}