package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.CustomFieldData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomFieldData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomFieldDataRepository extends JpaRepository<CustomFieldData, Long> {}
