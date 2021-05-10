package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.JobTemplate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link JobTemplate}.
 */
public interface JobTemplateService {
    /**
     * Save a jobTemplate.
     *
     * @param jobTemplate the entity to save.
     * @return the persisted entity.
     */
    JobTemplate save(JobTemplate jobTemplate);

    /**
     * Partially updates a jobTemplate.
     *
     * @param jobTemplate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobTemplate> partialUpdate(JobTemplate jobTemplate);

    /**
     * Get all the jobTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobTemplate> findAll(Pageable pageable);

    /**
     * Get the "id" jobTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobTemplate> findOne(Long id);

    /**
     * Delete the "id" jobTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
