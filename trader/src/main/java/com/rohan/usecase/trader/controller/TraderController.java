package com.rohan.usecase.trader.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.usecase.trader.exception.UserAlreadyExistException;
import com.rohan.usecase.trader.model.Trader;
import com.rohan.usecase.trader.repository.TraderRepository;

@RestController
@RequestMapping("/trading/traders")
public class TraderController {

	@Autowired
	private TraderRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<Trader>> all() {
		List<Trader> traderList = new ArrayList<Trader>();
		repository.findAll().forEach(traderList::add);
		return new ResponseEntity<>(traderList, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Trader> findOne(@RequestParam String email) {

		Trader trader = repository.findByEmail(email);

		if (trader == null) {
			throw new EntityNotFoundException("Trader with email " + email + " not found in the database");
		}
		return new ResponseEntity<>(trader, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Trader> createTrader(@RequestBody Trader trader) {

		if (repository.findByEmail(trader.getEmail()) != null) {
			throw new UserAlreadyExistException(
					"Trader with email " + trader.getEmail() + " already exists in the database");
		}

		Trader _trader = repository.save(new Trader(trader.getName(), trader.getEmail(), trader.getBalance(),
				new Timestamp(new Date().getTime())));
		return new ResponseEntity<>(_trader, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<Trader> updateName(@RequestBody Map<String, String> payload) {

		Trader trader = repository.findByEmail(payload.get("email"));

		if (trader == null) {
			throw new EntityNotFoundException("Trader with email " + payload.get("email") + " not found in the database");
		}

		trader.setName(payload.get("name"));
		trader.setUpdatedAt(new Timestamp(new Date().getTime()));
		repository.save(trader);

		return new ResponseEntity<>(trader, HttpStatus.OK);

	}

	@PutMapping("/add")
	public ResponseEntity<Trader> updateBalance(@RequestBody Map<String, String> payload) {

		Trader trader = repository.findByEmail(payload.get("email"));

		if (trader == null) {
			throw new EntityNotFoundException("Trader with email " + payload.get("email") + " not found in the database");
		}

		Long curr_balance = trader.getBalance();
		trader.setBalance(curr_balance + Long.parseLong(payload.get("amount")));
		trader.setUpdatedAt(new Timestamp(new Date().getTime()));
		repository.save(trader);

		return new ResponseEntity<>(trader, HttpStatus.OK);

	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteTrader(@RequestParam String email) {

		Trader trader = repository.findByEmail(email);

		if (trader == null) {
			throw new EntityNotFoundException("Trader with email " + email + " not found in the database");
		}
		repository.delete(trader);

		return ResponseEntity.status(HttpStatus.OK).body("Trader deleted successfully");

	}

}
