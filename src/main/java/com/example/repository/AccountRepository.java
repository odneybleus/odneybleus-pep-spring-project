package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

/**
 * repository interface for the Account entity to handle database operations.
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}