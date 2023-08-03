package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("branch")
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String name;
	private Scale scale;

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

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", scale=" + scale + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, scale);
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
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && scale == other.scale;
	}

}
