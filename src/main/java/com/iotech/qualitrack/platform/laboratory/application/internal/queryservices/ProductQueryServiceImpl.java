package com.iotech.qualitrack.platform.laboratory.application.internal.queryservices;

import com.iotech.qualitrack.platform.laboratory.application.queryservices.ProductQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetProductsByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that resolves pharmaceutical product read queries.
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<PharmaceuticalProduct> handle(GetProductsByLabIdQuery query) {
        return productRepository.findAllByLaboratoryId(query.laboratoryId());
    }
}