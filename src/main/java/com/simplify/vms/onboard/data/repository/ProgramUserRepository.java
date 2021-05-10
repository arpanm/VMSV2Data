package com.simplify.vms.onboard.data.repository;

import com.simplify.vms.onboard.data.domain.ProgramUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProgramUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramUserRepository extends JpaRepository<ProgramUser, Long> {}
