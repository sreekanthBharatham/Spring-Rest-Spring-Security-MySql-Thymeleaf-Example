package com.sreekanth.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sreekanth.demo.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	Optional<Expense> findByExpenseId(String id);
	
	List<Expense> findByUserId(Long id);

}
