package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.JobCategoryTitle;
import com.simplify.vms.onboard.data.repository.JobCategoryTitleRepository;
import com.simplify.vms.onboard.data.service.JobCategoryTitleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobCategoryTitle}.
 */
@Service
@Transactional
public class JobCategoryTitleServiceImpl implements JobCategoryTitleService {

    private final Logger log = LoggerFactory.getLogger(JobCategoryTitleServiceImpl.class);

    private final JobCategoryTitleRepository jobCategoryTitleRepository;

    public JobCategoryTitleServiceImpl(JobCategoryTitleRepository jobCategoryTitleRepository) {
        this.jobCategoryTitleRepository = jobCategoryTitleRepository;
    }

    @Override
    public JobCategoryTitle save(JobCategoryTitle jobCategoryTitle) {
        log.debug("Request to save JobCategoryTitle : {}", jobCategoryTitle);
        return jobCategoryTitleRepository.save(jobCategoryTitle);
    }

    @Override
    public Optional<JobCategoryTitle> partialUpdate(JobCategoryTitle jobCategoryTitle) {
        log.debug("Request to partially update JobCategoryTitle : {}", jobCategoryTitle);

        return jobCategoryTitleRepository
            .findById(jobCategoryTitle.getId())
            .map(
                existingJobCategoryTitle -> {
                    if (jobCategoryTitle.getCategory() != null) {
                        existingJobCategoryTitle.setCategory(jobCategoryTitle.getCategory());
                    }
                    if (jobCategoryTitle.getTitle() != null) {
                        existingJobCategoryTitle.setTitle(jobCategoryTitle.getTitle());
                    }
                    if (jobCategoryTitle.getCode() != null) {
                        existingJobCategoryTitle.setCode(jobCategoryTitle.getCode());
                    }
                    if (jobCategoryTitle.getIsActive() != null) {
                        existingJobCategoryTitle.setIsActive(jobCategoryTitle.getIsActive());
                    }
                    if (jobCategoryTitle.getCreatedBy() != null) {
                        existingJobCategoryTitle.setCreatedBy(jobCategoryTitle.getCreatedBy());
                    }
                    if (jobCategoryTitle.getCreatedAt() != null) {
                        existingJobCategoryTitle.setCreatedAt(jobCategoryTitle.getCreatedAt());
                    }
                    if (jobCategoryTitle.getUpdatedBy() != null) {
                        existingJobCategoryTitle.setUpdatedBy(jobCategoryTitle.getUpdatedBy());
                    }
                    if (jobCategoryTitle.getUpdatedAt() != null) {
                        existingJobCategoryTitle.setUpdatedAt(jobCategoryTitle.getUpdatedAt());
                    }

                    return existingJobCategoryTitle;
                }
            )
            .map(jobCategoryTitleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobCategoryTitle> findAll(Pageable pageable) {
        log.debug("Request to get all JobCategoryTitles");
        return jobCategoryTitleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobCategoryTitle> findOne(Long id) {
        log.debug("Request to get JobCategoryTitle : {}", id);
        return jobCategoryTitleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobCategoryTitle : {}", id);
        jobCategoryTitleRepository.deleteById(id);
    }
}
