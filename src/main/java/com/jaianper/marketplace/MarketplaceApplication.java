package com.jaianper.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableReactiveMongoRepositories
public class MarketplaceApplication {

  public static void main(String[] args) {
    try {
      io.github.cdimascio.dotenv.Dotenv dotenv =
          io.github.cdimascio.dotenv.Dotenv.configure().ignoreIfMissing().load();
      dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    } catch (Exception e) {
      // Ignore
    }
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
