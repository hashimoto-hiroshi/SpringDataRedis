package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String name;
	private Department department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return department == other.department && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
