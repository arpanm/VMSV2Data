package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.FoundationalData;
import com.simplify.vms.onboard.data.repository.FoundationalDataRepository;
import com.simplify.vms.onboard.data.service.FoundationalDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FoundationalData}.
 */
@Service
@Transactional
public class FoundationalDataServiceImpl implements FoundationalDataService {

    private final Logger log = LoggerFactory.getLogger(FoundationalDataServiceImpl.class);

    private final FoundationalDataRepository foundationalDataRepository;

    public FoundationalDataServiceImpl(FoundationalDataRepository foundationalDataRepository) {
        this.foundationalDataRepository = foundationalDataRepository;
    }

    @Override
    public FoundationalData save(FoundationalData foundationalData) {
        log.debug("Request to save FoundationalData : {}", foundationalData);
        return foundationalDataRepository.save(foundationalData);
    }

    @Override
    public Optional<FoundationalData> partialUpdate(FoundationalData foundationalData) {
        log.debug("Request to partially update FoundationalData : {}", foundationalData);

        return foundationalDataRepository
            .findById(foundationalData.getId())
            .map(
                existingFoundationalData -> {
                    if (foundationalData.getName() != null) {
                        existingFoundationalData.setName(foundationalData.getName());
                    }
                    if (foundationalData.getCode() != null) {
                        existingFoundationalData.setCode(foundationalData.getCode());
                    }
                    if (foundationalData.getDescription() != null) {
                        existingFoundationalData.setDescription(foundationalData.getDescription());
                    }
                    if (foundationalData.getIsActive() != null) {
                        existingFoundationalData.setIsActive(foundationalData.getIsActive());
                    }
                    if (foundationalData.getCreatedBy() != null) {
                        existingFoundationalData.setCreatedBy(foundationalData.getCreatedBy());
                    }
                    if (foundationalData.getCreatedAt() != null) {
                        existingFoundationalData.setCreatedAt(foundationalData.getCreatedAt());
                    }
                    if (foundationalData.getUpdatedBy() != null) {
                        existingFoundationalData.setUpdatedBy(foundationalData.getUpdatedBy());
                    }
                    if (foundationalData.getUpdatedAt() != null) {
                        existingFoundationalData.setUpdatedAt(foundationalData.getUpdatedAt());
                    }

                    return existingFoundationalData;
                }
            )
            .map(foundationalDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoundationalData> findAll(Pageable pageable) {
        log.debug("Request to get all FoundationalData");
        return foundationalDataRepository.findAll(pageable);
    }

    public Page<FoundationalData> findAllWithEagerRelationships(Pageable pageable) {
        return foundationalDataRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoundationalData> findOne(Long id) {
        log.debug("Request to get FoundationalData : {}", id);
        return foundationalDataRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoundationalData : {}", id);
        foundationalDataRepository.deleteById(id);
    }
}
