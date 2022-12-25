package com.sreekanth.demo.service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.sreekanth.demo.dto.ExpenseDTO;
import com.sreekanth.demo.entity.Expense;
import com.sreekanth.demo.entity.User;
import com.sreekanth.demo.repository.ExpenseRepository;
import com.sreekanth.demo.repository.UserRepository;
import com.sreekanth.demo.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ExpenseService {
	
	private final ExpenseRepository expenseRepository; 
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	
	
	
	public List<ExpenseDTO> getAllExppense() {
	
		User user = userService.getLoggedInUser();
		List<Expense> list = expenseRepository.findByUserId(user.getId());
		List<ExpenseDTO> expenseList = list.stream().map(this::mapToDTO).collect(Collectors.toList());
		return expenseList;
	}

	
	
	public ExpenseDTO saveExpenceDetails(ExpenseDTO expenseDTO) throws ParseException{
		Expense expense = mapToEntity(expenseDTO);
		expense.setUser(userService.getLoggedInUser());
		expense = expenseRepository.save(expense);
		return mapToDTO(expense);
		
	}



	private ExpenseDTO mapToDTO(Expense expense) {
	// TODO Auto-generated method stub
		ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
		expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
		return expenseDTO;
}



	private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException{
		// TODO Auto-generated method stub
		Expense expense = modelMapper.map(expenseDTO, Expense.class);
		
		if (expense.getId() == null) {
			expense.setExpenseId(UUID.randomUUID().toString());
		}
		expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
		return expense;
	}
	
	public void deleteExpense(String id) {
		Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(() -> new RuntimeException("not found id" + id));
		expenseRepository.delete(existingExpense);
	}
	
	
	public ExpenseDTO getExpenseById(String id) {
		Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(() -> new RuntimeException("not found id" + id));
		return mapToDTO(existingExpense);
	}

}
