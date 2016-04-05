package com.linx.bookmarks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linx.bookmarks.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
