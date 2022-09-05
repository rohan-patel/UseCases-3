package com.rohan.usecase.trader.ui.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohan.usecase.trader.ui.model.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {

}
