package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.JobTemplateRateCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobTemplateRateCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTemplateRateCardRepository extends JpaRepository<JobTemplateRateCard, Long> {}
