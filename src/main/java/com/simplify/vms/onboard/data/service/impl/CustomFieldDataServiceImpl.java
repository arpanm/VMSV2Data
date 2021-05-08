package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.CustomFieldData;
import com.simplify.vms.onboard.data.repository.CustomFieldDataRepository;
import com.simplify.vms.onboard.data.service.CustomFieldDataService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomFieldData}.
 */
@Service
@Transactional
public class CustomFieldDataServiceImpl implements CustomFieldDataService {

    private final Logger log = LoggerFactory.getLogger(CustomFieldDataServiceImpl.class);

    private final CustomFieldDataRepository customFieldDataRepository;

    public CustomFieldDataServiceImpl(CustomFieldDataRepository customFieldDataRepository) {
        this.customFieldDataRepository = customFieldDataRepository;
    }

    @Override
    public CustomFieldData save(CustomFieldData customFieldData) {
        log.debug("Request to save CustomFieldData : {}", customFieldData);
        return customFieldDataRepository.save(customFieldData);
    }

    @Override
    public Optional<CustomFieldData> partialUpdate(CustomFieldData customFieldData) {
        log.debug("Request to partially update CustomFieldData : {}", customFieldData);

        return customFieldDataRepository
            .findById(customFieldData.getId())
            .map(
                existingCustomFieldData -> {
                    if (customFieldData.getValue() != null) {
                        existingCustomFieldData.setValue(customFieldData.getValue());
                    }
                    if (customFieldData.getIsActive() != null) {
                        existingCustomFieldData.setIsActive(customFieldData.getIsActive());
                    }
                    if (customFieldData.getCreatedBy() != null) {
                        existingCustomFieldData.setCreatedBy(customFieldData.getCreatedBy());
                    }
                    if (customFieldData.getCreatedAt() != null) {
                        existingCustomFieldData.setCreatedAt(customFieldData.getCreatedAt());
                    }
                    if (customFieldData.getUpdatedBy() != null) {
                        existingCustomFieldData.setUpdatedBy(customFieldData.getUpdatedBy());
                    }
                    if (customFieldData.getUpdatedAt() != null) {
                        existingCustomFieldData.setUpdatedAt(customFieldData.getUpdatedAt());
                    }

                    return existingCustomFieldData;
                }
            )
            .map(customFieldDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomFieldData> findAll() {
        log.debug("Request to get all CustomFieldData");
        return customFieldDataRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomFieldData> findOne(Long id) {
        log.debug("Request to get CustomFieldData : {}", id);
        return customFieldDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomFieldData : {}", id);
        customFieldDataRepository.deleteById(id);
    }
}
