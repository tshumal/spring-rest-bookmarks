package com.linx.bookmarks.controller.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.linx.bookmarks.controller.BookmarkRestHateoasController;
import com.linx.bookmarks.domain.Bookmark;

public class BookmarkResource extends ResourceSupport implements Comparable<Bookmark>{
	
	private final Bookmark bookmark;

	public Bookmark getBookmark() {
		return bookmark;
	}
	
	public BookmarkResource(Bookmark bookmark){
		String username = bookmark.getAccount().getUsername();
		this.bookmark = bookmark;
		this.add(new Link(bookmark.getUri(), "bookmarks-uri"));
		this.add(linkTo(BookmarkRestHateoasController.class, username).withRel("bookmarks"));		
		this.add(linkTo(methodOn(BookmarkRestHateoasController.class, username).readBookmark(username, bookmark.getId())).withSelfRel());
	}

	@Override
	public int compareTo(Bookmark o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
