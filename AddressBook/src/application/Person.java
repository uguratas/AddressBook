package application;

import javafx.beans.property.SimpleStringProperty;

public class Person { 	
	private int ID;
	private String name;
	private String street;
	private String city;
	private String zip;
	
	private String gender;
	public Person() {
		
	}
	 public Person(int _ID, String _name, String _gender, String _street, String _city, String _zip) {
	        ID = _ID;
	        name = _name;
	        gender = _gender;
	        street = _street;
	        city = _city;
	        zip = _zip;
	    }

	public int getID() {
		return ID;
	}
	public void setID(int _ID) {
		ID = _ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String _street) {
		street = _street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String _city) {
		city = _city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String _zip) {
		zip = _zip;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String toString() {
		return getID()+"\n"+getName()+"\n"+getStreet()+"\n"+getCity()+"\n"+getGender()+"\n"+getZip();
	}
	
}
