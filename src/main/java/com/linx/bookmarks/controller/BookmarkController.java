package com.linx.bookmarks.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linx.bookmarks.domain.Bookmark;
import com.linx.bookmarks.exception.UserNotFoundException;
import com.linx.bookmarks.repository.AccountRepository;
import com.linx.bookmarks.repository.BookmarkRepository;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkController {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(Principal principal, @RequestBody Bookmark input) {
		String userId = principal.getName();
		this.validateUser(userId);
		return this.accountRepository.findByUsername(userId).map(account -> {
			Bookmark result = bookmarkRepository.save(new Bookmark(account, input.getUri(), input.getDescription()));

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(result.getId()).toUri());
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
		}).get();
	}

	@RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
	Bookmark readBookmark(Principal principal, @PathVariable Long bookmarkId) {
		String userId = principal.getName();
		this.validateUser(userId);
		return this.bookmarkRepository.findOne(bookmarkId);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Bookmark> readBookmarks(Principal principal) {
		String userId = principal.getName();
		this.validateUser(userId);
		return this.bookmarkRepository.findByAccountUsername(userId);
	}

	private void validateUser(String userId) {
		this.accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
}
