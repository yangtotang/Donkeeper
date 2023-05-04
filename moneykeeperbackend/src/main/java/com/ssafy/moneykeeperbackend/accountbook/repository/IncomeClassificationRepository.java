package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;

@Repository
public interface IncomeClassificationRepository extends JpaRepository<IncomeClassification, Long> {

	Optional<IncomeClassification> findById(Long id);

}