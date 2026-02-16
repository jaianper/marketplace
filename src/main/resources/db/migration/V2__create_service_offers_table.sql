CREATE TABLE service_offers (
    id UUID PRIMARY KEY,
    provider_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price_amount DECIMAL(19, 2) NOT NULL,
    price_currency VARCHAR(3) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_service_offers_provider_id ON service_offers(provider_id);
