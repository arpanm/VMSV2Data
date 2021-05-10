package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.FoundationalData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FoundationalData entity.
 */
@Repository
public interface FoundationalDataRepository extends JpaRepository<FoundationalData, Long> {
    @Query(
        value = "select distinct foundationalData from FoundationalData foundationalData left join fetch foundationalData.managers",
        countQuery = "select count(distinct foundationalData) from FoundationalData foundationalData"
    )
    Page<FoundationalData> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct foundationalData from FoundationalData foundationalData left join fetch foundationalData.managers")
    List<FoundationalData> findAllWithEagerRelationships();

    @Query(
        "select foundationalData from FoundationalData foundationalData left join fetch foundationalData.managers where foundationalData.id =:id"
    )
    Optional<FoundationalData> findOneWithEagerRelationships(@Param("id") Long id);
}
