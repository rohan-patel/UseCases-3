package com.rohan.usecase.trader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohan.usecase.trader.model.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {

    Trader findByEmail(String email);
//    Trader deleteByEmail(String email);

}
