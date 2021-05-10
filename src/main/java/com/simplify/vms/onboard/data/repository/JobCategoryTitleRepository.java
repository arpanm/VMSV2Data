package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.JobCategoryTitle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobCategoryTitle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobCategoryTitleRepository extends JpaRepository<JobCategoryTitle, Long> {}
