package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.CustomFieldType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomFieldType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomFieldTypeRepository extends JpaRepository<CustomFieldType, Long> {}
