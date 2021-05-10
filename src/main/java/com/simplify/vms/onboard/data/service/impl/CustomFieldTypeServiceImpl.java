package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.CustomFieldType;
import com.simplify.vms.onboard.data.repository.CustomFieldTypeRepository;
import com.simplify.vms.onboard.data.service.CustomFieldTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomFieldType}.
 */
@Service
@Transactional
public class CustomFieldTypeServiceImpl implements CustomFieldTypeService {

    private final Logger log = LoggerFactory.getLogger(CustomFieldTypeServiceImpl.class);

    private final CustomFieldTypeRepository customFieldTypeRepository;

    public CustomFieldTypeServiceImpl(CustomFieldTypeRepository customFieldTypeRepository) {
        this.customFieldTypeRepository = customFieldTypeRepository;
    }

    @Override
    public CustomFieldType save(CustomFieldType customFieldType) {
        log.debug("Request to save CustomFieldType : {}", customFieldType);
        return customFieldTypeRepository.save(customFieldType);
    }

    @Override
    public Optional<CustomFieldType> partialUpdate(CustomFieldType customFieldType) {
        log.debug("Request to partially update CustomFieldType : {}", customFieldType);

        return customFieldTypeRepository
            .findById(customFieldType.getId())
            .map(
                existingCustomFieldType -> {
                    if (customFieldType.getName() != null) {
                        existingCustomFieldType.setName(customFieldType.getName());
                    }
                    if (customFieldType.getType() != null) {
                        existingCustomFieldType.setType(customFieldType.getType());
                    }
                    if (customFieldType.getDescription() != null) {
                        existingCustomFieldType.setDescription(customFieldType.getDescription());
                    }
                    if (customFieldType.getLabel() != null) {
                        existingCustomFieldType.setLabel(customFieldType.getLabel());
                    }
                    if (customFieldType.getPlaceholder() != null) {
                        existingCustomFieldType.setPlaceholder(customFieldType.getPlaceholder());
                    }
                    if (customFieldType.getIsMandatory() != null) {
                        existingCustomFieldType.setIsMandatory(customFieldType.getIsMandatory());
                    }
                    if (customFieldType.getIsActive() != null) {
                        existingCustomFieldType.setIsActive(customFieldType.getIsActive());
                    }
                    if (customFieldType.getCreatedBy() != null) {
                        existingCustomFieldType.setCreatedBy(customFieldType.getCreatedBy());
                    }
                    if (customFieldType.getCreatedAt() != null) {
                        existingCustomFieldType.setCreatedAt(customFieldType.getCreatedAt());
                    }
                    if (customFieldType.getUpdatedBy() != null) {
                        existingCustomFieldType.setUpdatedBy(customFieldType.getUpdatedBy());
                    }
                    if (customFieldType.getUpdatedAt() != null) {
                        existingCustomFieldType.setUpdatedAt(customFieldType.getUpdatedAt());
                    }

                    return existingCustomFieldType;
                }
            )
            .map(customFieldTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomFieldType> findAll(Pageable pageable) {
        log.debug("Request to get all CustomFieldTypes");
        return customFieldTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomFieldType> findOne(Long id) {
        log.debug("Request to get CustomFieldType : {}", id);
        return customFieldTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomFieldType : {}", id);
        customFieldTypeRepository.deleteById(id);
    }
}
