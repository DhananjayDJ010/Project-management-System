package com.project.dto;

public class User {
 private String name;
 private String dept;
public User() {
	super();
	// TODO Auto-generated constructor stub
}
public User(String name, String dept) {
	super();
	this.name = name;
	this.dept = dept;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDept() {
	return dept;
} 
public void setDept(String dept) {
	this.dept = dept;
}
@Override
public String toString() {
	return "User [name=" + name + ", dept=" + dept + "]";
}
 
 
}
