package com.jaianper.marketplace.order.domain;

import com.jaianper.marketplace.serviceoffer.domain.Price;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Order {
  private final OrderId id;
  private final UUID consumerId;
  private final UUID serviceOfferId;
  private final Price price; // Snapshot of price
  private OrderStatus status;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Constructor for reconstruction
  public Order(
      OrderId id,
      UUID consumerId,
      UUID serviceOfferId,
      Price price,
      OrderStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.consumerId = consumerId;
    this.serviceOfferId = serviceOfferId;
    this.price = price;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for creating new orders
  public static Order createNew(UUID consumerId, UUID serviceOfferId, Price price) {
    LocalDateTime now = LocalDateTime.now();
    return new Order(
        OrderId.random(), consumerId, serviceOfferId, price, OrderStatus.PENDING, now, now);
  }

  public void accept() {
    if (this.status != OrderStatus.PENDING) {
      throw new IllegalStateException("Order must be PENDING to be ACCEPTED");
    }
    this.status = OrderStatus.ACCEPTED;
    this.updatedAt = LocalDateTime.now();
  }

  public void startProgress() {
    if (this.status != OrderStatus.ACCEPTED) {
      throw new IllegalStateException("Order must be ACCEPTED to be IN_PROGRESS");
    }
    this.status = OrderStatus.IN_PROGRESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void complete() {
    if (this.status != OrderStatus.IN_PROGRESS) {
      throw new IllegalStateException("Order must be IN_PROGRESS to be COMPLETED");
    }
    this.status = OrderStatus.COMPLETED;
    this.updatedAt = LocalDateTime.now();
  }

  public void cancel() {
    if (this.status == OrderStatus.COMPLETED) {
      throw new IllegalStateException("Cannot cancel a COMPLETED order");
    }
    this.status = OrderStatus.CANCELLED;
    this.updatedAt = LocalDateTime.now();
  }
}
