package com.MagicPrices;

import java.util.Scanner;
import javax.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.MagicPrices.controller.FetcherController;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;

@SpringBootApplication
public class MagicPricesApplication{


	public static void main(String[] args) {

		SpringApplication.run(MagicPricesApplication.class, args);
	}
	
	
}
