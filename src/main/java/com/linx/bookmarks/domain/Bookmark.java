package com.linx.bookmarks.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Bookmark {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String uri;
	private String description;
	
	@JsonIgnore
	@ManyToOne
	private Account account;
	
	public Bookmark(){
		
	}
	
	public Bookmark(Account account, String uri, String description){		
		this.account = account;
		this.uri = uri;
		this.description = description;
	}
	
	public Bookmark(Long id, Account account, String uri, String description){
		this.id = id;
		this.account = account;
		this.uri = uri;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getUri() {
		return uri;
	}

	public String getDescription() {
		return description;
	}

	public Account getAccount() {
		return account;
	}	

	public void setId(Long id) {
		this.id = id;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Bookmark [id=" + id + ", uri=" + uri + ", description="
				+ description + ", account=" + account + "]";
	}	
}
