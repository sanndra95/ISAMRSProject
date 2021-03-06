package rs.team15.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;

import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "e_id")
public class Employee extends User {

	@Column(name = "e_id", insertable = false, updatable = false)
    private Long eid;
	
	@Column(name = "birthday", nullable=false)
	protected Date birthday;
	
	@Column(name = "dressSize", nullable=false)
	protected String dressSize;
	
	@Column(name = "shoeSize", nullable=false)
	protected String shoeSize;
	
	@Column(name = "firstTime", nullable=false)
	protected String firstTime;
	

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rid")
	private Restaurant restaurant;
	
	//@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regid")
    private Region region;
    
    @Column(name = "type")
    private String type;

	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rid")
	private Restaurant restaurant;*/

	
	
	public Employee(){
		super();
	}

	public Employee(String email, String firstName, String lastName, String password, Date birthday, String dressSize, String shoeSize, Restaurant restaurant, Region region){

	//public Employee(Long id, String email, String firstName, String lastName, String password, Date birthday, String dressSize, String shoeSize, Restaurant restaurant, String region){

    	//this.id = id;
    	this.email = email;
		this.fname = firstName;
		this.lname = lastName;
		this.image = "";
		this.password = password;
		this.birthday = birthday;
		this.verified = "no";
		this.login = "no";
		this.firstTime = "yes";
		this.restaurant = restaurant;
		this.region = region;
    }
	
	public Employee(Long id, String email, String firstName, String lastName, String password, Date birthday, String dressSize, String shoeSize, String role, Restaurant restaurant, Region region){

	//public Employee(Long id, String email, String firstName, String lastName, String password, Date birthday, String dressSize, String shoeSize, String role, Restaurant restaurant, String region){

    	this.id = id;
    	this.email = email;
		this.fname = firstName;
		this.lname = lastName;
		this.image = "";
		this.password = password;
		this.birthday = birthday;
		this.verified = "no";
		this.login = "no";
		this.role = role;
		this.firstTime = "yes";
		this.restaurant = restaurant;
		this.region = region;
    }
	
	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDressSize() {
		return dressSize;
	}

	public void setDressSize(String dressSize) {
		this.dressSize = dressSize;
	}

	public String getShoeSize() {
		return shoeSize;
	}

	public void setShoeSize(String shoeSize) {
		this.shoeSize = shoeSize;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	//@JsonIgnore
	public Restaurant getRestaurant() {
		return restaurant;
	}

	@JsonProperty
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	//@JsonIgnore
	public Region getRegion() {
		return region;
	}

	//@JsonProperty
	public void setRegion(Region region) {
		this.region = region;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	
	
}
