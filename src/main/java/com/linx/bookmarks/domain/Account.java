package com.linx.bookmarks.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private long id;

	@OneToMany(mappedBy = "account")
	private Set<Bookmark> bookmarks = new HashSet<>();

	@JsonIgnore
	private String password;

	private String username;

	public Account() {
	}

	public Account(String name, String password) {
		this.username = name;
		this.password = password;
	}
	
	public Account(Long id, String name, String password) {
		this.id = id;
		this.username = name;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", bookmarks=" + bookmarks + ", password="
				+ password + ", username=" + username + "]";
	}
}
