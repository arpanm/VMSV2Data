package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.FoundationalDataType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FoundationalDataType}.
 */
public interface FoundationalDataTypeService {
    /**
     * Save a foundationalDataType.
     *
     * @param foundationalDataType the entity to save.
     * @return the persisted entity.
     */
    FoundationalDataType save(FoundationalDataType foundationalDataType);

    /**
     * Partially updates a foundationalDataType.
     *
     * @param foundationalDataType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FoundationalDataType> partialUpdate(FoundationalDataType foundationalDataType);

    /**
     * Get all the foundationalDataTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoundationalDataType> findAll(Pageable pageable);

    /**
     * Get the "id" foundationalDataType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoundationalDataType> findOne(Long id);

    /**
     * Delete the "id" foundationalDataType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
