package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.CustomFieldType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CustomFieldType}.
 */
public interface CustomFieldTypeService {
    /**
     * Save a customFieldType.
     *
     * @param customFieldType the entity to save.
     * @return the persisted entity.
     */
    CustomFieldType save(CustomFieldType customFieldType);

    /**
     * Partially updates a customFieldType.
     *
     * @param customFieldType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomFieldType> partialUpdate(CustomFieldType customFieldType);

    /**
     * Get all the customFieldTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomFieldType> findAll(Pageable pageable);

    /**
     * Get the "id" customFieldType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomFieldType> findOne(Long id);

    /**
     * Delete the "id" customFieldType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
