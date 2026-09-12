package com.iotech.qualitrack.platform.laboratory.domain.repositories;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.StaffMember;

import java.util.List;
import java.util.Optional;

/**
 * Staff member repository port.
 */
public interface StaffRepository {
    Optional<StaffMember> findById(Long id);

    List<StaffMember> findAll();

    List<StaffMember> findAllByLaboratoryId(Long laboratoryId);

    StaffMember save(StaffMember staffMember);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}