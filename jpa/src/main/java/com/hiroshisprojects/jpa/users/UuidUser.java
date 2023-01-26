package com.hiroshisprojects.jpa.users;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *	Saving a UUID Entity with Hibernate is buggy. Not gonna use this.
 * */
@Entity
@Table(name = "uuid_users")
public class UuidUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @Type(type = "org.hibernate.type.UUIDCharType")
	private long id;

	@Column(name = "email", unique = true)
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UuidUser [id=" + id + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UuidUser other = (UuidUser) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
