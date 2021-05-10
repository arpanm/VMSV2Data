package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.JobTemplateRateCard;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link JobTemplateRateCard}.
 */
public interface JobTemplateRateCardService {
    /**
     * Save a jobTemplateRateCard.
     *
     * @param jobTemplateRateCard the entity to save.
     * @return the persisted entity.
     */
    JobTemplateRateCard save(JobTemplateRateCard jobTemplateRateCard);

    /**
     * Partially updates a jobTemplateRateCard.
     *
     * @param jobTemplateRateCard the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobTemplateRateCard> partialUpdate(JobTemplateRateCard jobTemplateRateCard);

    /**
     * Get all the jobTemplateRateCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobTemplateRateCard> findAll(Pageable pageable);

    /**
     * Get the "id" jobTemplateRateCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobTemplateRateCard> findOne(Long id);

    /**
     * Delete the "id" jobTemplateRateCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
