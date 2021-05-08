package com.simplify.vms.onboard.data.service.impl;

import com.simplify.vms.onboard.data.domain.Program;
import com.simplify.vms.onboard.data.repository.ProgramRepository;
import com.simplify.vms.onboard.data.service.ProgramService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public Program save(Program program) {
        log.debug("Request to save Program : {}", program);
        return programRepository.save(program);
    }

    @Override
    public Optional<Program> partialUpdate(Program program) {
        log.debug("Request to partially update Program : {}", program);

        return programRepository
            .findById(program.getId())
            .map(
                existingProgram -> {
                    if (program.getName() != null) {
                        existingProgram.setName(program.getName());
                    }
                    if (program.getEmail() != null) {
                        existingProgram.setEmail(program.getEmail());
                    }
                    if (program.getPhoneNumber() != null) {
                        existingProgram.setPhoneNumber(program.getPhoneNumber());
                    }
                    if (program.getDeploymentRegion() != null) {
                        existingProgram.setDeploymentRegion(program.getDeploymentRegion());
                    }
                    if (program.getSubdomain() != null) {
                        existingProgram.setSubdomain(program.getSubdomain());
                    }
                    if (program.getImplementationManagerEmail() != null) {
                        existingProgram.setImplementationManagerEmail(program.getImplementationManagerEmail());
                    }
                    if (program.getIsActive() != null) {
                        existingProgram.setIsActive(program.getIsActive());
                    }
                    if (program.getCreatedBy() != null) {
                        existingProgram.setCreatedBy(program.getCreatedBy());
                    }
                    if (program.getCreatedAt() != null) {
                        existingProgram.setCreatedAt(program.getCreatedAt());
                    }
                    if (program.getUpdatedBy() != null) {
                        existingProgram.setUpdatedBy(program.getUpdatedBy());
                    }
                    if (program.getUpdatedAt() != null) {
                        existingProgram.setUpdatedAt(program.getUpdatedAt());
                    }

                    return existingProgram;
                }
            )
            .map(programRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Program> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Program> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
    }
}
