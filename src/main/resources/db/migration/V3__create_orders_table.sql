CREATE TABLE orders (
    id UUID PRIMARY KEY,
    consumer_id UUID NOT NULL,
    service_offer_id UUID NOT NULL,
    price_amount DECIMAL(19, 2) NOT NULL,
    price_currency VARCHAR(3) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_orders_consumer_id ON orders(consumer_id);
CREATE INDEX idx_orders_service_offer_id ON orders(service_offer_id);
