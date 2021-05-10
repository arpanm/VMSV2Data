package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.JobTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTemplateRepository extends JpaRepository<JobTemplate, Long> {}
