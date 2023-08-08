package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer branchNo;
	private String name;

	public Branch() {}
	
	public Branch(Long id, Integer branchNo, String name) {
		super();
		this.id = id;
		this.branchNo = branchNo;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(Integer branchNo) {
		this.branchNo = branchNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branchNo, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		return Objects.equals(branchNo, other.branchNo) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
