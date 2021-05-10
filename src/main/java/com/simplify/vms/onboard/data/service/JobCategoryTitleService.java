package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.JobCategoryTitle;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link JobCategoryTitle}.
 */
public interface JobCategoryTitleService {
    /**
     * Save a jobCategoryTitle.
     *
     * @param jobCategoryTitle the entity to save.
     * @return the persisted entity.
     */
    JobCategoryTitle save(JobCategoryTitle jobCategoryTitle);

    /**
     * Partially updates a jobCategoryTitle.
     *
     * @param jobCategoryTitle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobCategoryTitle> partialUpdate(JobCategoryTitle jobCategoryTitle);

    /**
     * Get all the jobCategoryTitles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobCategoryTitle> findAll(Pageable pageable);

    /**
     * Get the "id" jobCategoryTitle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobCategoryTitle> findOne(Long id);

    /**
     * Delete the "id" jobCategoryTitle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
