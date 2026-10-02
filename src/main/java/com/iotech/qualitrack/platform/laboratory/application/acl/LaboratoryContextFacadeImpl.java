package com.iotech.qualitrack.platform.laboratory.application.acl;

import com.iotech.qualitrack.platform.laboratory.application.queryservices.EnvironmentQueryService;
import com.iotech.qualitrack.platform.laboratory.application.queryservices.LaboratoryQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetEnvironmentByIdQuery;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLaboratoryByIdQuery;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Laboratory ACL facade.
 */
@Service
public class LaboratoryContextFacadeImpl implements LaboratoryContextFacade {

    private final LaboratoryQueryService laboratoryQueryService;
    private final EnvironmentQueryService environmentQueryService;

    public LaboratoryContextFacadeImpl(LaboratoryQueryService laboratoryQueryService,
                                       EnvironmentQueryService environmentQueryService) {
        this.laboratoryQueryService = laboratoryQueryService;
        this.environmentQueryService = environmentQueryService;
    }

    @Override
    public boolean existsLaboratoryById(Long labId) {
        var query = new GetLaboratoryByIdQuery(labId);

        var result = laboratoryQueryService.handle(query);

        return result.isPresent();
    }

    @Override
    public boolean existsEnvironment(Long laboratoryId, Long environmentId) {
        if (laboratoryId == null || laboratoryId <= 0 || environmentId == null || environmentId <= 0) return false;
        return environmentQueryService.handle(new GetEnvironmentByIdQuery(laboratoryId, environmentId)).isPresent();
    }
}
