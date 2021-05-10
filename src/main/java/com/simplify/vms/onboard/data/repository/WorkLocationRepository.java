package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.WorkLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkLocationRepository extends JpaRepository<WorkLocation, Long> {}
