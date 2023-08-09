package com.shinhan.sunInCloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByLoginId(String loginId);
}
