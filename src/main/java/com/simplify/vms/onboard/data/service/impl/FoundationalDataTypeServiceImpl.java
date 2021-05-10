package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.FoundationalDataType;
import com.simplify.vms.onboard.data.repository.FoundationalDataTypeRepository;
import com.simplify.vms.onboard.data.service.FoundationalDataTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FoundationalDataType}.
 */
@Service
@Transactional
public class FoundationalDataTypeServiceImpl implements FoundationalDataTypeService {

    private final Logger log = LoggerFactory.getLogger(FoundationalDataTypeServiceImpl.class);

    private final FoundationalDataTypeRepository foundationalDataTypeRepository;

    public FoundationalDataTypeServiceImpl(FoundationalDataTypeRepository foundationalDataTypeRepository) {
        this.foundationalDataTypeRepository = foundationalDataTypeRepository;
    }

    @Override
    public FoundationalDataType save(FoundationalDataType foundationalDataType) {
        log.debug("Request to save FoundationalDataType : {}", foundationalDataType);
        return foundationalDataTypeRepository.save(foundationalDataType);
    }

    @Override
    public Optional<FoundationalDataType> partialUpdate(FoundationalDataType foundationalDataType) {
        log.debug("Request to partially update FoundationalDataType : {}", foundationalDataType);

        return foundationalDataTypeRepository
            .findById(foundationalDataType.getId())
            .map(
                existingFoundationalDataType -> {
                    if (foundationalDataType.getName() != null) {
                        existingFoundationalDataType.setName(foundationalDataType.getName());
                    }
                    if (foundationalDataType.getDescription() != null) {
                        existingFoundationalDataType.setDescription(foundationalDataType.getDescription());
                    }
                    if (foundationalDataType.getRequiredInHierarchy() != null) {
                        existingFoundationalDataType.setRequiredInHierarchy(foundationalDataType.getRequiredInHierarchy());
                    }
                    if (foundationalDataType.getRequiredInJobs() != null) {
                        existingFoundationalDataType.setRequiredInJobs(foundationalDataType.getRequiredInJobs());
                    }
                    if (foundationalDataType.getRequiredInSow() != null) {
                        existingFoundationalDataType.setRequiredInSow(foundationalDataType.getRequiredInSow());
                    }
                    if (foundationalDataType.getFileUploadPath() != null) {
                        existingFoundationalDataType.setFileUploadPath(foundationalDataType.getFileUploadPath());
                    }
                    if (foundationalDataType.getIsActive() != null) {
                        existingFoundationalDataType.setIsActive(foundationalDataType.getIsActive());
                    }
                    if (foundationalDataType.getCreatedBy() != null) {
                        existingFoundationalDataType.setCreatedBy(foundationalDataType.getCreatedBy());
                    }
                    if (foundationalDataType.getCreatedAt() != null) {
                        existingFoundationalDataType.setCreatedAt(foundationalDataType.getCreatedAt());
                    }
                    if (foundationalDataType.getUpdatedBy() != null) {
                        existingFoundationalDataType.setUpdatedBy(foundationalDataType.getUpdatedBy());
                    }
                    if (foundationalDataType.getUpdatedAt() != null) {
                        existingFoundationalDataType.setUpdatedAt(foundationalDataType.getUpdatedAt());
                    }

                    return existingFoundationalDataType;
                }
            )
            .map(foundationalDataTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoundationalDataType> findAll(Pageable pageable) {
        log.debug("Request to get all FoundationalDataTypes");
        return foundationalDataTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoundationalDataType> findOne(Long id) {
        log.debug("Request to get FoundationalDataType : {}", id);
        return foundationalDataTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoundationalDataType : {}", id);
        foundationalDataTypeRepository.deleteById(id);
    }
}
