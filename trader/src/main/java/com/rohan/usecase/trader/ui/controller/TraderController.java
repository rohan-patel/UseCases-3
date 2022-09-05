package com.rohan.usecase.trader.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.usecase.trader.ui.model.Trader;
import com.rohan.usecase.trader.ui.repository.TraderRepository;

@RestController
public class TraderController {

	@Autowired
	private TraderRepository repository;
	
	@GetMapping("/trading/traders/all")
	List<Trader> all() {
		return repository.findAll();
	}
	
	@GetMapping("/trading/traders/all")
	List<Trader> one() {
		return repository.findAll();
	}
	
	
	
	
}
