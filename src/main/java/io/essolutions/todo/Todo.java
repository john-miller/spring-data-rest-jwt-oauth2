package io.essolutions.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.essolutions.account.Account;

@Entity
public class Todo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(optional=false)
	private Account owner;
	
	private String name;
	private String description;
	private boolean secret;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSecret() {
		return secret;
	}
	public void setSecret(boolean secret) {
		this.secret = secret;
	}
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}

}
