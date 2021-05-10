package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.ProgramUser;
import com.simplify.vms.onboard.data.domain.enumeration.Role;
import com.simplify.vms.onboard.data.repository.ProgramUserRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProgramUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_DESIGNATION = "BBBBBBBBBB";

    private static final Role DEFAULT_SIMPLIFY_ROLE = Role.Administrator;
    private static final Role UPDATED_SIMPLIFY_ROLE = Role.Hiring_Manager;

    private static final String DEFAULT_FILE_UPLOAD_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_UPLOAD_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/program-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProgramUserRepository programUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramUserMockMvc;

    private ProgramUser programUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramUser createEntity(EntityManager em) {
        ProgramUser programUser = new ProgramUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .sourceId(DEFAULT_SOURCE_ID)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .clientDesignation(DEFAULT_CLIENT_DESIGNATION)
            .simplifyRole(DEFAULT_SIMPLIFY_ROLE)
            .fileUploadPath(DEFAULT_FILE_UPLOAD_PATH)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return programUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramUser createUpdatedEntity(EntityManager em) {
        ProgramUser programUser = new ProgramUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .sourceId(UPDATED_SOURCE_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .clientDesignation(UPDATED_CLIENT_DESIGNATION)
            .simplifyRole(UPDATED_SIMPLIFY_ROLE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return programUser;
    }

    @BeforeEach
    public void initTest() {
        programUser = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramUser() throws Exception {
        int databaseSizeBeforeCreate = programUserRepository.findAll().size();
        // Create the ProgramUser
        restProgramUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programUser)))
            .andExpect(status().isCreated());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramUser testProgramUser = programUserList.get(programUserList.size() - 1);
        assertThat(testProgramUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProgramUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProgramUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProgramUser.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testProgramUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProgramUser.getClientDesignation()).isEqualTo(DEFAULT_CLIENT_DESIGNATION);
        assertThat(testProgramUser.getSimplifyRole()).isEqualTo(DEFAULT_SIMPLIFY_ROLE);
        assertThat(testProgramUser.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testProgramUser.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testProgramUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProgramUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProgramUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testProgramUser.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createProgramUserWithExistingId() throws Exception {
        // Create the ProgramUser with an existing ID
        programUser.setId(1L);

        int databaseSizeBeforeCreate = programUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programUser)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgramUsers() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        // Get all the programUserList
        restProgramUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].clientDesignation").value(hasItem(DEFAULT_CLIENT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].simplifyRole").value(hasItem(DEFAULT_SIMPLIFY_ROLE.toString())))
            .andExpect(jsonPath("$.[*].fileUploadPath").value(hasItem(DEFAULT_FILE_UPLOAD_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getProgramUser() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        // Get the programUser
        restProgramUserMockMvc
            .perform(get(ENTITY_API_URL_ID, programUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.clientDesignation").value(DEFAULT_CLIENT_DESIGNATION))
            .andExpect(jsonPath("$.simplifyRole").value(DEFAULT_SIMPLIFY_ROLE.toString()))
            .andExpect(jsonPath("$.fileUploadPath").value(DEFAULT_FILE_UPLOAD_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramUser() throws Exception {
        // Get the programUser
        restProgramUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProgramUser() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();

        // Update the programUser
        ProgramUser updatedProgramUser = programUserRepository.findById(programUser.getId()).get();
        // Disconnect from session so that the updates on updatedProgramUser are not directly saved in db
        em.detach(updatedProgramUser);
        updatedProgramUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .sourceId(UPDATED_SOURCE_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .clientDesignation(UPDATED_CLIENT_DESIGNATION)
            .simplifyRole(UPDATED_SIMPLIFY_ROLE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restProgramUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramUser))
            )
            .andExpect(status().isOk());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
        ProgramUser testProgramUser = programUserList.get(programUserList.size() - 1);
        assertThat(testProgramUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProgramUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProgramUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProgramUser.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testProgramUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProgramUser.getClientDesignation()).isEqualTo(UPDATED_CLIENT_DESIGNATION);
        assertThat(testProgramUser.getSimplifyRole()).isEqualTo(UPDATED_SIMPLIFY_ROLE);
        assertThat(testProgramUser.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testProgramUser.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProgramUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProgramUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProgramUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProgramUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramUserWithPatch() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();

        // Update the programUser using partial update
        ProgramUser partialUpdatedProgramUser = new ProgramUser();
        partialUpdatedProgramUser.setId(programUser.getId());

        partialUpdatedProgramUser
            .firstName(UPDATED_FIRST_NAME)
            .sourceId(UPDATED_SOURCE_ID)
            .clientDesignation(UPDATED_CLIENT_DESIGNATION)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedAt(UPDATED_UPDATED_AT);

        restProgramUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramUser))
            )
            .andExpect(status().isOk());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
        ProgramUser testProgramUser = programUserList.get(programUserList.size() - 1);
        assertThat(testProgramUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProgramUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProgramUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProgramUser.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testProgramUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProgramUser.getClientDesignation()).isEqualTo(UPDATED_CLIENT_DESIGNATION);
        assertThat(testProgramUser.getSimplifyRole()).isEqualTo(DEFAULT_SIMPLIFY_ROLE);
        assertThat(testProgramUser.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testProgramUser.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProgramUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProgramUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProgramUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testProgramUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateProgramUserWithPatch() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();

        // Update the programUser using partial update
        ProgramUser partialUpdatedProgramUser = new ProgramUser();
        partialUpdatedProgramUser.setId(programUser.getId());

        partialUpdatedProgramUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .sourceId(UPDATED_SOURCE_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .clientDesignation(UPDATED_CLIENT_DESIGNATION)
            .simplifyRole(UPDATED_SIMPLIFY_ROLE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restProgramUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramUser))
            )
            .andExpect(status().isOk());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
        ProgramUser testProgramUser = programUserList.get(programUserList.size() - 1);
        assertThat(testProgramUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProgramUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProgramUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProgramUser.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testProgramUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProgramUser.getClientDesignation()).isEqualTo(UPDATED_CLIENT_DESIGNATION);
        assertThat(testProgramUser.getSimplifyRole()).isEqualTo(UPDATED_SIMPLIFY_ROLE);
        assertThat(testProgramUser.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testProgramUser.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProgramUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProgramUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProgramUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProgramUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramUser() throws Exception {
        int databaseSizeBeforeUpdate = programUserRepository.findAll().size();
        programUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(programUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramUser in the database
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramUser() throws Exception {
        // Initialize the database
        programUserRepository.saveAndFlush(programUser);

        int databaseSizeBeforeDelete = programUserRepository.findAll().size();

        // Delete the programUser
        restProgramUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, programUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramUser> programUserList = programUserRepository.findAll();
        assertThat(programUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
