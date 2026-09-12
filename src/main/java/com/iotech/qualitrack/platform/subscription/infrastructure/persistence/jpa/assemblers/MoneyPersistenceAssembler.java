package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.Money;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.embeddables.MoneyEmbeddable;

/**
 * Assembler for Money value object and persistence embeddable.
 */
public class MoneyPersistenceAssembler {

    private MoneyPersistenceAssembler() {
    }

    public static Money toDomainFromPersistence(MoneyEmbeddable embeddable) {
        if (embeddable == null) return null;

        return new Money(
                embeddable.getAmount(),
                embeddable.getCurrency()
        );
    }

    public static MoneyEmbeddable toPersistenceFromDomain(Money money) {
        if (money == null) return null;

        return new MoneyEmbeddable(
                money.amount(),
                money.currency()
        );
    }
}