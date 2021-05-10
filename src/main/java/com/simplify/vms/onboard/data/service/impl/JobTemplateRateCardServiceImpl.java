package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.JobTemplateRateCard;
import com.simplify.vms.onboard.data.repository.JobTemplateRateCardRepository;
import com.simplify.vms.onboard.data.service.JobTemplateRateCardService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobTemplateRateCard}.
 */
@Service
@Transactional
public class JobTemplateRateCardServiceImpl implements JobTemplateRateCardService {

    private final Logger log = LoggerFactory.getLogger(JobTemplateRateCardServiceImpl.class);

    private final JobTemplateRateCardRepository jobTemplateRateCardRepository;

    public JobTemplateRateCardServiceImpl(JobTemplateRateCardRepository jobTemplateRateCardRepository) {
        this.jobTemplateRateCardRepository = jobTemplateRateCardRepository;
    }

    @Override
    public JobTemplateRateCard save(JobTemplateRateCard jobTemplateRateCard) {
        log.debug("Request to save JobTemplateRateCard : {}", jobTemplateRateCard);
        return jobTemplateRateCardRepository.save(jobTemplateRateCard);
    }

    @Override
    public Optional<JobTemplateRateCard> partialUpdate(JobTemplateRateCard jobTemplateRateCard) {
        log.debug("Request to partially update JobTemplateRateCard : {}", jobTemplateRateCard);

        return jobTemplateRateCardRepository
            .findById(jobTemplateRateCard.getId())
            .map(
                existingJobTemplateRateCard -> {
                    if (jobTemplateRateCard.getCurrency() != null) {
                        existingJobTemplateRateCard.setCurrency(jobTemplateRateCard.getCurrency());
                    }
                    if (jobTemplateRateCard.getHourlyMin() != null) {
                        existingJobTemplateRateCard.setHourlyMin(jobTemplateRateCard.getHourlyMin());
                    }
                    if (jobTemplateRateCard.getHourlyMax() != null) {
                        existingJobTemplateRateCard.setHourlyMax(jobTemplateRateCard.getHourlyMax());
                    }
                    if (jobTemplateRateCard.getDailyMin() != null) {
                        existingJobTemplateRateCard.setDailyMin(jobTemplateRateCard.getDailyMin());
                    }
                    if (jobTemplateRateCard.getDailyMax() != null) {
                        existingJobTemplateRateCard.setDailyMax(jobTemplateRateCard.getDailyMax());
                    }
                    if (jobTemplateRateCard.getWeeklyMin() != null) {
                        existingJobTemplateRateCard.setWeeklyMin(jobTemplateRateCard.getWeeklyMin());
                    }
                    if (jobTemplateRateCard.getWeeklyMax() != null) {
                        existingJobTemplateRateCard.setWeeklyMax(jobTemplateRateCard.getWeeklyMax());
                    }
                    if (jobTemplateRateCard.getMonthlyMin() != null) {
                        existingJobTemplateRateCard.setMonthlyMin(jobTemplateRateCard.getMonthlyMin());
                    }
                    if (jobTemplateRateCard.getMonthlyMax() != null) {
                        existingJobTemplateRateCard.setMonthlyMax(jobTemplateRateCard.getMonthlyMax());
                    }
                    if (jobTemplateRateCard.getYearlyMin() != null) {
                        existingJobTemplateRateCard.setYearlyMin(jobTemplateRateCard.getYearlyMin());
                    }
                    if (jobTemplateRateCard.getYearlyMax() != null) {
                        existingJobTemplateRateCard.setYearlyMax(jobTemplateRateCard.getYearlyMax());
                    }
                    if (jobTemplateRateCard.getIsActive() != null) {
                        existingJobTemplateRateCard.setIsActive(jobTemplateRateCard.getIsActive());
                    }
                    if (jobTemplateRateCard.getCreatedBy() != null) {
                        existingJobTemplateRateCard.setCreatedBy(jobTemplateRateCard.getCreatedBy());
                    }
                    if (jobTemplateRateCard.getCreatedAt() != null) {
                        existingJobTemplateRateCard.setCreatedAt(jobTemplateRateCard.getCreatedAt());
                    }
                    if (jobTemplateRateCard.getUpdatedBy() != null) {
                        existingJobTemplateRateCard.setUpdatedBy(jobTemplateRateCard.getUpdatedBy());
                    }
                    if (jobTemplateRateCard.getUpdatedAt() != null) {
                        existingJobTemplateRateCard.setUpdatedAt(jobTemplateRateCard.getUpdatedAt());
                    }

                    return existingJobTemplateRateCard;
                }
            )
            .map(jobTemplateRateCardRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobTemplateRateCard> findAll(Pageable pageable) {
        log.debug("Request to get all JobTemplateRateCards");
        return jobTemplateRateCardRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobTemplateRateCard> findOne(Long id) {
        log.debug("Request to get JobTemplateRateCard : {}", id);
        return jobTemplateRateCardRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobTemplateRateCard : {}", id);
        jobTemplateRateCardRepository.deleteById(id);
    }
}
