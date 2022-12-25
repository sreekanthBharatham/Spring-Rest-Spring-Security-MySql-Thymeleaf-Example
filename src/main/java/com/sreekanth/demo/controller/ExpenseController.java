package com.sreekanth.demo.controller;


import java.text.ParseException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.sreekanth.demo.dto.ExpenseDTO;
import com.sreekanth.demo.dto.ExpenseFilterDTO;
import com.sreekanth.demo.service.ExpenseService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ExpenseController {
	private final ExpenseService expenseService;
	
	@PostMapping("/saveOrUpdateExpense")
	public String saveOrUpdateExpenseDetails(@ModelAttribute("expense") ExpenseDTO expneseDTO) throws ParseException {
		System.out.println("Printing the Expense DTO: "+expneseDTO);
		expenseService.saveExpenceDetails(expneseDTO);
		return "redirect:/expenses";
		
	}
	
	@GetMapping("/deleteExpense")
	public String deleteExpense(@RequestParam String id) {
		System.out.println("printing the expense id" + id);
		expenseService.deleteExpense(id);
		return "redirect:/expenses";
	}
	
	@GetMapping("/updateExpense")
	public String updateExpense(@RequestParam String id, Model model) {
		System.out.println("update" + id);		
		ExpenseDTO expense = expenseService.getExpenseById(id);
		model.addAttribute("expense", expense);
		return "expense-form";
	}
	
	
	@GetMapping("/expenses")
	public String showExpenseList(Model model) {
		model.addAttribute("expenses", expenseService.getAllExppense());
		model.addAttribute("filter", new ExpenseFilterDTO());
		return "expenses-list";
	}
	
	@GetMapping("/createExpense")
	public String showExpenseForm(Model model) {
		model.addAttribute("expense", new ExpenseDTO());
		return "expense-form";
	}

}
