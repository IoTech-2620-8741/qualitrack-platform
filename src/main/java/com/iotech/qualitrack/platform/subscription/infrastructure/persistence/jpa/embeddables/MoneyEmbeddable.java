package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA embeddable representation of the Money value object.
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MoneyEmbeddable {

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    public MoneyEmbeddable(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}