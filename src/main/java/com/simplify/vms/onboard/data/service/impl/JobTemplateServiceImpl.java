package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.JobTemplate;
import com.simplify.vms.onboard.data.repository.JobTemplateRepository;
import com.simplify.vms.onboard.data.service.JobTemplateService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobTemplate}.
 */
@Service
@Transactional
public class JobTemplateServiceImpl implements JobTemplateService {

    private final Logger log = LoggerFactory.getLogger(JobTemplateServiceImpl.class);

    private final JobTemplateRepository jobTemplateRepository;

    public JobTemplateServiceImpl(JobTemplateRepository jobTemplateRepository) {
        this.jobTemplateRepository = jobTemplateRepository;
    }

    @Override
    public JobTemplate save(JobTemplate jobTemplate) {
        log.debug("Request to save JobTemplate : {}", jobTemplate);
        return jobTemplateRepository.save(jobTemplate);
    }

    @Override
    public Optional<JobTemplate> partialUpdate(JobTemplate jobTemplate) {
        log.debug("Request to partially update JobTemplate : {}", jobTemplate);

        return jobTemplateRepository
            .findById(jobTemplate.getId())
            .map(
                existingJobTemplate -> {
                    if (jobTemplate.getTemplateTitle() != null) {
                        existingJobTemplate.setTemplateTitle(jobTemplate.getTemplateTitle());
                    }
                    if (jobTemplate.getDescription() != null) {
                        existingJobTemplate.setDescription(jobTemplate.getDescription());
                    }
                    if (jobTemplate.getJobLevel() != null) {
                        existingJobTemplate.setJobLevel(jobTemplate.getJobLevel());
                    }
                    if (jobTemplate.getIsDescriptionEditable() != null) {
                        existingJobTemplate.setIsDescriptionEditable(jobTemplate.getIsDescriptionEditable());
                    }
                    if (jobTemplate.getDistributionType() != null) {
                        existingJobTemplate.setDistributionType(jobTemplate.getDistributionType());
                    }
                    if (jobTemplate.getDistributionLimit() != null) {
                        existingJobTemplate.setDistributionLimit(jobTemplate.getDistributionLimit());
                    }
                    if (jobTemplate.getIsActive() != null) {
                        existingJobTemplate.setIsActive(jobTemplate.getIsActive());
                    }
                    if (jobTemplate.getCreatedBy() != null) {
                        existingJobTemplate.setCreatedBy(jobTemplate.getCreatedBy());
                    }
                    if (jobTemplate.getCreatedAt() != null) {
                        existingJobTemplate.setCreatedAt(jobTemplate.getCreatedAt());
                    }
                    if (jobTemplate.getUpdatedBy() != null) {
                        existingJobTemplate.setUpdatedBy(jobTemplate.getUpdatedBy());
                    }
                    if (jobTemplate.getUpdatedAt() != null) {
                        existingJobTemplate.setUpdatedAt(jobTemplate.getUpdatedAt());
                    }

                    return existingJobTemplate;
                }
            )
            .map(jobTemplateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all JobTemplates");
        return jobTemplateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobTemplate> findOne(Long id) {
        log.debug("Request to get JobTemplate : {}", id);
        return jobTemplateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobTemplate : {}", id);
        jobTemplateRepository.deleteById(id);
    }
}
