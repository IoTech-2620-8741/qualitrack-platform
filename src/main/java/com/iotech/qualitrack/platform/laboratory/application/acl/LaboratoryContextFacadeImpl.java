package com.iotech.qualitrack.platform.laboratory.application.acl;

import com.iotech.qualitrack.platform.laboratory.application.queryservices.LaboratoryQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLaboratoryByIdQuery;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Laboratory ACL facade.
 */
@Service
public class LaboratoryContextFacadeImpl implements LaboratoryContextFacade {

    private final LaboratoryQueryService laboratoryQueryService;

    public LaboratoryContextFacadeImpl(LaboratoryQueryService laboratoryQueryService) {
        this.laboratoryQueryService = laboratoryQueryService;
    }

    @Override
    public boolean existsLaboratoryById(Long labId) {
        var query = new GetLaboratoryByIdQuery(labId);

        var result = laboratoryQueryService.handle(query);

        return result.isPresent();
    }
}