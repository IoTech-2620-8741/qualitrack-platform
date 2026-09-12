package com.iotech.qualitrack.platform.laboratory.domain.model.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;

/**
 * Domain event published when a new {@link Laboratory} is successfully registered and persisted.
 *
 * <p>Other bounded contexts (e.g. {@code tracking} or {@code equipment}) can listen to this event
 * to react to laboratory creation without directly coupling to the {@code laboratory}
 * application services.</p>
 *
 * @param laboratoryId The numeric identity assigned to the newly created laboratory.
 * @param name         The official name of the laboratory.
 */
public record LaboratoryRegisteredEvent(
        Long laboratoryId,
        String name) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Laboratory}.
     *
     * @param laboratory the saved laboratory
     * @return a fully populated {@link LaboratoryRegisteredEvent}
     */
    public static LaboratoryRegisteredEvent from(Laboratory laboratory) {
        return new LaboratoryRegisteredEvent(
                laboratory.getId(),
                laboratory.getName().name()
        );
    }
}