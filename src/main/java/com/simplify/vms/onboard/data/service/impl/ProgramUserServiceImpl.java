package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.ProgramUser;
import com.simplify.vms.onboard.data.repository.ProgramUserRepository;
import com.simplify.vms.onboard.data.service.ProgramUserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProgramUser}.
 */
@Service
@Transactional
public class ProgramUserServiceImpl implements ProgramUserService {

    private final Logger log = LoggerFactory.getLogger(ProgramUserServiceImpl.class);

    private final ProgramUserRepository programUserRepository;

    public ProgramUserServiceImpl(ProgramUserRepository programUserRepository) {
        this.programUserRepository = programUserRepository;
    }

    @Override
    public ProgramUser save(ProgramUser programUser) {
        log.debug("Request to save ProgramUser : {}", programUser);
        return programUserRepository.save(programUser);
    }

    @Override
    public Optional<ProgramUser> partialUpdate(ProgramUser programUser) {
        log.debug("Request to partially update ProgramUser : {}", programUser);

        return programUserRepository
            .findById(programUser.getId())
            .map(
                existingProgramUser -> {
                    if (programUser.getFirstName() != null) {
                        existingProgramUser.setFirstName(programUser.getFirstName());
                    }
                    if (programUser.getLastName() != null) {
                        existingProgramUser.setLastName(programUser.getLastName());
                    }
                    if (programUser.getEmail() != null) {
                        existingProgramUser.setEmail(programUser.getEmail());
                    }
                    if (programUser.getSourceId() != null) {
                        existingProgramUser.setSourceId(programUser.getSourceId());
                    }
                    if (programUser.getPhoneNumber() != null) {
                        existingProgramUser.setPhoneNumber(programUser.getPhoneNumber());
                    }
                    if (programUser.getClientDesignation() != null) {
                        existingProgramUser.setClientDesignation(programUser.getClientDesignation());
                    }
                    if (programUser.getSimplifyRole() != null) {
                        existingProgramUser.setSimplifyRole(programUser.getSimplifyRole());
                    }
                    if (programUser.getFileUploadPath() != null) {
                        existingProgramUser.setFileUploadPath(programUser.getFileUploadPath());
                    }
                    if (programUser.getIsActive() != null) {
                        existingProgramUser.setIsActive(programUser.getIsActive());
                    }
                    if (programUser.getCreatedBy() != null) {
                        existingProgramUser.setCreatedBy(programUser.getCreatedBy());
                    }
                    if (programUser.getCreatedAt() != null) {
                        existingProgramUser.setCreatedAt(programUser.getCreatedAt());
                    }
                    if (programUser.getUpdatedBy() != null) {
                        existingProgramUser.setUpdatedBy(programUser.getUpdatedBy());
                    }
                    if (programUser.getUpdatedAt() != null) {
                        existingProgramUser.setUpdatedAt(programUser.getUpdatedAt());
                    }

                    return existingProgramUser;
                }
            )
            .map(programUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramUser> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramUsers");
        return programUserRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramUser> findOne(Long id) {
        log.debug("Request to get ProgramUser : {}", id);
        return programUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProgramUser : {}", id);
        programUserRepository.deleteById(id);
    }
}
