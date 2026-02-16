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
    try (var lines = java.nio.file.Files.lines(java.nio.file.Paths.get(".env"))) {
      lines
          .filter(line -> line.contains("=") && !line.startsWith("#"))
          .forEach(
              line -> {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                  System.setProperty(parts[0].trim(), parts[1].trim());
                }
              });
    } catch (Throwable e) {
      // Ignore if .env missing
    }
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
