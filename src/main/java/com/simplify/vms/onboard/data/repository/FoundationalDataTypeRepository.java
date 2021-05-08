package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.FoundationalDataType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FoundationalDataType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoundationalDataTypeRepository extends JpaRepository<FoundationalDataType, Long> {}
