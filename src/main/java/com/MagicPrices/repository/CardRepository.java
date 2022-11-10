package com.MagicPrices.repository;

import org.springframework.data.repository.CrudRepository;
import com.MagicPrices.model.Card;

public interface CardRepository extends CrudRepository<Card, String>{

	Card findCardByCardId(String cardId);
	
}
