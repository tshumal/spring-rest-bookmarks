/*package com.linx.bookmarks.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
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
import com.linx.bookmarks.hateoas.BookmarkResource;
import com.linx.bookmarks.repository.AccountRepository;
import com.linx.bookmarks.repository.BookmarkRepository;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@PathVariable String userId,
			@RequestBody Bookmark input) {
		this.validateUser(userId);
		return this.accountRepository
				.findByUsername(userId)
				.map(account -> {
					Bookmark result = bookmarkRepository.save(new Bookmark(
							account, input.getUri(), input.getDescription()));

					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setLocation(ServletUriComponentsBuilder
							.fromCurrentRequest().path("/{id}")
							.buildAndExpand(result.getId()).toUri());
					return new ResponseEntity<>(null, httpHeaders,
							HttpStatus.CREATED);
				}).get();
	}

	@RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
	public BookmarkResource readBookmark(@PathVariable String userId,
			@PathVariable Long bookmarkId) {
		this.validateUser(userId);
		return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
	}

	@RequestMapping(method = RequestMethod.GET)
	Resources<BookmarkResource> readBookmarks(@PathVariable String userId) {
		this.validateUser(userId);
		List<BookmarkResource> bookmarkResourceList = bookmarkRepository.findByAccountUsername(userId)
                .stream()
                .map(BookmarkResource::new)
                .collect(Collectors.toList());
		return new Resources <BookmarkResource>(bookmarkResourceList);
	}

	private void validateUser(String userId) {
		this.accountRepository.findByUsername(userId).orElseThrow(
				() -> new UserNotFoundException(userId));
	}
}
*/