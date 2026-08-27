package com.fragma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fragma.entity.Userdata1;

public interface UserRepository extends JpaRepository<Userdata1, Long>{

	Optional<Userdata1> findByEmail(String email);

}
