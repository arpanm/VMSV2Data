package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.Hierarchy;
import com.simplify.vms.onboard.data.repository.HierarchyRepository;
import com.simplify.vms.onboard.data.service.HierarchyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hierarchy}.
 */
@Service
@Transactional
public class HierarchyServiceImpl implements HierarchyService {

    private final Logger log = LoggerFactory.getLogger(HierarchyServiceImpl.class);

    private final HierarchyRepository hierarchyRepository;

    public HierarchyServiceImpl(HierarchyRepository hierarchyRepository) {
        this.hierarchyRepository = hierarchyRepository;
    }

    @Override
    public Hierarchy save(Hierarchy hierarchy) {
        log.debug("Request to save Hierarchy : {}", hierarchy);
        return hierarchyRepository.save(hierarchy);
    }

    @Override
    public Optional<Hierarchy> partialUpdate(Hierarchy hierarchy) {
        log.debug("Request to partially update Hierarchy : {}", hierarchy);

        return hierarchyRepository
            .findById(hierarchy.getId())
            .map(
                existingHierarchy -> {
                    if (hierarchy.getName() != null) {
                        existingHierarchy.setName(hierarchy.getName());
                    }
                    if (hierarchy.getWorkFlow() != null) {
                        existingHierarchy.setWorkFlow(hierarchy.getWorkFlow());
                    }
                    if (hierarchy.getPreferredLanguage() != null) {
                        existingHierarchy.setPreferredLanguage(hierarchy.getPreferredLanguage());
                    }
                    if (hierarchy.getPreferredCurrency() != null) {
                        existingHierarchy.setPreferredCurrency(hierarchy.getPreferredCurrency());
                    }
                    if (hierarchy.getPrimaryCountry() != null) {
                        existingHierarchy.setPrimaryCountry(hierarchy.getPrimaryCountry());
                    }
                    if (hierarchy.getPrimaryAddress() != null) {
                        existingHierarchy.setPrimaryAddress(hierarchy.getPrimaryAddress());
                    }
                    if (hierarchy.getSecondaryCountry() != null) {
                        existingHierarchy.setSecondaryCountry(hierarchy.getSecondaryCountry());
                    }
                    if (hierarchy.getSecondaryAddress() != null) {
                        existingHierarchy.setSecondaryAddress(hierarchy.getSecondaryAddress());
                    }
                    if (hierarchy.getPrimaryContactName() != null) {
                        existingHierarchy.setPrimaryContactName(hierarchy.getPrimaryContactName());
                    }
                    if (hierarchy.getPrimaryContactDesignation() != null) {
                        existingHierarchy.setPrimaryContactDesignation(hierarchy.getPrimaryContactDesignation());
                    }
                    if (hierarchy.getPrimaryContactEmail() != null) {
                        existingHierarchy.setPrimaryContactEmail(hierarchy.getPrimaryContactEmail());
                    }
                    if (hierarchy.getPrimaryContactPhone() != null) {
                        existingHierarchy.setPrimaryContactPhone(hierarchy.getPrimaryContactPhone());
                    }
                    if (hierarchy.getSecondaryContactName() != null) {
                        existingHierarchy.setSecondaryContactName(hierarchy.getSecondaryContactName());
                    }
                    if (hierarchy.getSecondaryContactDesignation() != null) {
                        existingHierarchy.setSecondaryContactDesignation(hierarchy.getSecondaryContactDesignation());
                    }
                    if (hierarchy.getSecondaryContactEmail() != null) {
                        existingHierarchy.setSecondaryContactEmail(hierarchy.getSecondaryContactEmail());
                    }
                    if (hierarchy.getSecondaryContactPhone() != null) {
                        existingHierarchy.setSecondaryContactPhone(hierarchy.getSecondaryContactPhone());
                    }
                    if (hierarchy.getFileUploadPath() != null) {
                        existingHierarchy.setFileUploadPath(hierarchy.getFileUploadPath());
                    }
                    if (hierarchy.getIsActive() != null) {
                        existingHierarchy.setIsActive(hierarchy.getIsActive());
                    }
                    if (hierarchy.getCreatedBy() != null) {
                        existingHierarchy.setCreatedBy(hierarchy.getCreatedBy());
                    }
                    if (hierarchy.getCreatedAt() != null) {
                        existingHierarchy.setCreatedAt(hierarchy.getCreatedAt());
                    }
                    if (hierarchy.getUpdatedBy() != null) {
                        existingHierarchy.setUpdatedBy(hierarchy.getUpdatedBy());
                    }
                    if (hierarchy.getUpdatedAt() != null) {
                        existingHierarchy.setUpdatedAt(hierarchy.getUpdatedAt());
                    }

                    return existingHierarchy;
                }
            )
            .map(hierarchyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hierarchy> findAll(Pageable pageable) {
        log.debug("Request to get all Hierarchies");
        return hierarchyRepository.findAll(pageable);
    }

    public Page<Hierarchy> findAllWithEagerRelationships(Pageable pageable) {
        return hierarchyRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hierarchy> findOne(Long id) {
        log.debug("Request to get Hierarchy : {}", id);
        return hierarchyRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hierarchy : {}", id);
        hierarchyRepository.deleteById(id);
    }
}
