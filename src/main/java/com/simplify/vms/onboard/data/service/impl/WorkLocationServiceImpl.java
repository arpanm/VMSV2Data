package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.WorkLocation;
import com.simplify.vms.onboard.data.repository.WorkLocationRepository;
import com.simplify.vms.onboard.data.service.WorkLocationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkLocation}.
 */
@Service
@Transactional
public class WorkLocationServiceImpl implements WorkLocationService {

    private final Logger log = LoggerFactory.getLogger(WorkLocationServiceImpl.class);

    private final WorkLocationRepository workLocationRepository;

    public WorkLocationServiceImpl(WorkLocationRepository workLocationRepository) {
        this.workLocationRepository = workLocationRepository;
    }

    @Override
    public WorkLocation save(WorkLocation workLocation) {
        log.debug("Request to save WorkLocation : {}", workLocation);
        return workLocationRepository.save(workLocation);
    }

    @Override
    public Optional<WorkLocation> partialUpdate(WorkLocation workLocation) {
        log.debug("Request to partially update WorkLocation : {}", workLocation);

        return workLocationRepository
            .findById(workLocation.getId())
            .map(
                existingWorkLocation -> {
                    if (workLocation.getCode() != null) {
                        existingWorkLocation.setCode(workLocation.getCode());
                    }
                    if (workLocation.getName() != null) {
                        existingWorkLocation.setName(workLocation.getName());
                    }
                    if (workLocation.getDescription() != null) {
                        existingWorkLocation.setDescription(workLocation.getDescription());
                    }
                    if (workLocation.getCountry() != null) {
                        existingWorkLocation.setCountry(workLocation.getCountry());
                    }
                    if (workLocation.getState() != null) {
                        existingWorkLocation.setState(workLocation.getState());
                    }
                    if (workLocation.getAddress() != null) {
                        existingWorkLocation.setAddress(workLocation.getAddress());
                    }
                    if (workLocation.getFileUploadPath() != null) {
                        existingWorkLocation.setFileUploadPath(workLocation.getFileUploadPath());
                    }
                    if (workLocation.getIsActive() != null) {
                        existingWorkLocation.setIsActive(workLocation.getIsActive());
                    }
                    if (workLocation.getCreatedBy() != null) {
                        existingWorkLocation.setCreatedBy(workLocation.getCreatedBy());
                    }
                    if (workLocation.getCreatedAt() != null) {
                        existingWorkLocation.setCreatedAt(workLocation.getCreatedAt());
                    }
                    if (workLocation.getUpdatedBy() != null) {
                        existingWorkLocation.setUpdatedBy(workLocation.getUpdatedBy());
                    }
                    if (workLocation.getUpdatedAt() != null) {
                        existingWorkLocation.setUpdatedAt(workLocation.getUpdatedAt());
                    }

                    return existingWorkLocation;
                }
            )
            .map(workLocationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkLocation> findAll(Pageable pageable) {
        log.debug("Request to get all WorkLocations");
        return workLocationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkLocation> findOne(Long id) {
        log.debug("Request to get WorkLocation : {}", id);
        return workLocationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkLocation : {}", id);
        workLocationRepository.deleteById(id);
    }
}
