package com.linx.bookmarks.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linx.bookmarks.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
