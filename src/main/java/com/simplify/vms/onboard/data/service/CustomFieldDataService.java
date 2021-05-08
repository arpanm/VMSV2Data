package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.CustomFieldData;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomFieldData}.
 */
public interface CustomFieldDataService {
    /**
     * Save a customFieldData.
     *
     * @param customFieldData the entity to save.
     * @return the persisted entity.
     */
    CustomFieldData save(CustomFieldData customFieldData);

    /**
     * Partially updates a customFieldData.
     *
     * @param customFieldData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomFieldData> partialUpdate(CustomFieldData customFieldData);

    /**
     * Get all the customFieldData.
     *
     * @return the list of entities.
     */
    List<CustomFieldData> findAll();

    /**
     * Get the "id" customFieldData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomFieldData> findOne(Long id);

    /**
     * Delete the "id" customFieldData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
