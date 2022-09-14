package com.rohan.usecase.trader.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.rohan.usecase.trader.model.Trader;
import com.rohan.usecase.trader.repository.TraderRepository;

@RestController
@RequestMapping("/trading/traders")
public class TraderController {

	@Autowired
	private TraderRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<Trader>> all() {

		try {
			List<Trader> traderList = new ArrayList<Trader>();

			repository.findAll().forEach(traderList::add);

			if (traderList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(traderList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// @GetMapping
	// public ResponseEntity one(@RequestParam String email) {

	// 	try {
	// 		Trader trader = repository.findByEmail(email);

	// 		if (trader == null) {
	// 			return ResponseEntity
	// 					.status(HttpStatus.NOT_FOUND)
	// 					.body("Trader with given Email Not Found");
	// 		}
	// 		return new ResponseEntity<>(trader, HttpStatus.OK);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}

	// }
	
	@GetMapping
	public ResponseEntity<Trader> findOne(@RequestParam String email) {

		try {
			Trader trader = repository.findByEmail(email);

			if (trader == null) {
				throw new EntityNotFoundException("Trader with email" + email + "not found in the database");
						
			}
			return new ResponseEntity<>(trader, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/register")
	public ResponseEntity createTrader(@RequestBody Trader trader) {
		try {

			if (repository.findByEmail(trader.getEmail()) != null) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Trader with given Email already exists");
			}

			Trader _trader = repository.save(new Trader(trader.getName(), trader.getEmail(), trader.getBalance(),
					new Timestamp(new Date().getTime())));
			return new ResponseEntity<>(_trader, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity updateName(@RequestBody Map<String, String> payload) {

		try {

			Trader trader = repository.findByEmail(payload.get("email"));

			if (trader == null) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("Trader with given Email Not Found");
			}

			trader.setName(payload.get("name"));
			trader.setUpdatedAt(new Timestamp(new Date().getTime()));
			repository.save(trader);

			return new ResponseEntity<>(trader, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/add")
	public ResponseEntity updateBalance(@RequestBody Map<String, String> payload) {

		try {
			Trader trader = repository.findByEmail(payload.get("email"));

			if (trader == null) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("Trader with given Email Not Found");
			}

			Long curr_balance = trader.getBalance();
			trader.setBalance(curr_balance + Long.parseLong(payload.get("amount")));
			trader.setUpdatedAt(new Timestamp(new Date().getTime()));
			repository.save(trader);

			return new ResponseEntity<>(trader, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteTrader(@RequestParam String email) {

		try {
			Trader trader = repository.findByEmail(email);
			repository.delete(trader);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Trader deleted successfully");
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Cold not find a Trader with the given Email ID");
		}
	}

}
