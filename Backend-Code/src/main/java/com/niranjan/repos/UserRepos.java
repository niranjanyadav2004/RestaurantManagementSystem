package com.niranjan.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niranjan.entities.User;

@Repository
public interface UserRepos extends JpaRepository<User, Long> {

}
