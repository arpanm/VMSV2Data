package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.Hierarchy;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hierarchy entity.
 */
@Repository
public interface HierarchyRepository extends JpaRepository<Hierarchy, Long> {
    @Query(
        value = "select distinct hierarchy from Hierarchy hierarchy left join fetch hierarchy.managers",
        countQuery = "select count(distinct hierarchy) from Hierarchy hierarchy"
    )
    Page<Hierarchy> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct hierarchy from Hierarchy hierarchy left join fetch hierarchy.managers")
    List<Hierarchy> findAllWithEagerRelationships();

    @Query("select hierarchy from Hierarchy hierarchy left join fetch hierarchy.managers where hierarchy.id =:id")
    Optional<Hierarchy> findOneWithEagerRelationships(@Param("id") Long id);
}
