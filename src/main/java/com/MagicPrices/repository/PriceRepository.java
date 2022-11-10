package com.MagicPrices.repository;

import org.springframework.data.repository.CrudRepository;
import com.MagicPrices.model.Price;

public interface PriceRepository extends CrudRepository<Price, Integer>{

  Price findPriceByPriceId(Integer priceId);
	
}
