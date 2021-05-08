package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.FoundationalDataType;
import com.simplify.vms.onboard.data.repository.FoundationalDataTypeRepository;
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
 * Integration tests for the {@link FoundationalDataTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoundationalDataTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REQUIRED_IN_HIERARCHY = false;
    private static final Boolean UPDATED_REQUIRED_IN_HIERARCHY = true;

    private static final Boolean DEFAULT_REQUIRED_IN_JOBS = false;
    private static final Boolean UPDATED_REQUIRED_IN_JOBS = true;

    private static final Boolean DEFAULT_REQUIRED_IN_SOW = false;
    private static final Boolean UPDATED_REQUIRED_IN_SOW = true;

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

    private static final String ENTITY_API_URL = "/api/foundational-data-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoundationalDataTypeRepository foundationalDataTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoundationalDataTypeMockMvc;

    private FoundationalDataType foundationalDataType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoundationalDataType createEntity(EntityManager em) {
        FoundationalDataType foundationalDataType = new FoundationalDataType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .requiredInHierarchy(DEFAULT_REQUIRED_IN_HIERARCHY)
            .requiredInJobs(DEFAULT_REQUIRED_IN_JOBS)
            .requiredInSow(DEFAULT_REQUIRED_IN_SOW)
            .fileUploadPath(DEFAULT_FILE_UPLOAD_PATH)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return foundationalDataType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoundationalDataType createUpdatedEntity(EntityManager em) {
        FoundationalDataType foundationalDataType = new FoundationalDataType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requiredInHierarchy(UPDATED_REQUIRED_IN_HIERARCHY)
            .requiredInJobs(UPDATED_REQUIRED_IN_JOBS)
            .requiredInSow(UPDATED_REQUIRED_IN_SOW)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return foundationalDataType;
    }

    @BeforeEach
    public void initTest() {
        foundationalDataType = createEntity(em);
    }

    @Test
    @Transactional
    void createFoundationalDataType() throws Exception {
        int databaseSizeBeforeCreate = foundationalDataTypeRepository.findAll().size();
        // Create the FoundationalDataType
        restFoundationalDataTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isCreated());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FoundationalDataType testFoundationalDataType = foundationalDataTypeList.get(foundationalDataTypeList.size() - 1);
        assertThat(testFoundationalDataType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoundationalDataType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFoundationalDataType.getRequiredInHierarchy()).isEqualTo(DEFAULT_REQUIRED_IN_HIERARCHY);
        assertThat(testFoundationalDataType.getRequiredInJobs()).isEqualTo(DEFAULT_REQUIRED_IN_JOBS);
        assertThat(testFoundationalDataType.getRequiredInSow()).isEqualTo(DEFAULT_REQUIRED_IN_SOW);
        assertThat(testFoundationalDataType.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testFoundationalDataType.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFoundationalDataType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFoundationalDataType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFoundationalDataType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFoundationalDataType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createFoundationalDataTypeWithExistingId() throws Exception {
        // Create the FoundationalDataType with an existing ID
        foundationalDataType.setId(1L);

        int databaseSizeBeforeCreate = foundationalDataTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoundationalDataTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoundationalDataTypes() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        // Get all the foundationalDataTypeList
        restFoundationalDataTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foundationalDataType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].requiredInHierarchy").value(hasItem(DEFAULT_REQUIRED_IN_HIERARCHY.booleanValue())))
            .andExpect(jsonPath("$.[*].requiredInJobs").value(hasItem(DEFAULT_REQUIRED_IN_JOBS.booleanValue())))
            .andExpect(jsonPath("$.[*].requiredInSow").value(hasItem(DEFAULT_REQUIRED_IN_SOW.booleanValue())))
            .andExpect(jsonPath("$.[*].fileUploadPath").value(hasItem(DEFAULT_FILE_UPLOAD_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getFoundationalDataType() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        // Get the foundationalDataType
        restFoundationalDataTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, foundationalDataType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foundationalDataType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.requiredInHierarchy").value(DEFAULT_REQUIRED_IN_HIERARCHY.booleanValue()))
            .andExpect(jsonPath("$.requiredInJobs").value(DEFAULT_REQUIRED_IN_JOBS.booleanValue()))
            .andExpect(jsonPath("$.requiredInSow").value(DEFAULT_REQUIRED_IN_SOW.booleanValue()))
            .andExpect(jsonPath("$.fileUploadPath").value(DEFAULT_FILE_UPLOAD_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFoundationalDataType() throws Exception {
        // Get the foundationalDataType
        restFoundationalDataTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFoundationalDataType() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();

        // Update the foundationalDataType
        FoundationalDataType updatedFoundationalDataType = foundationalDataTypeRepository.findById(foundationalDataType.getId()).get();
        // Disconnect from session so that the updates on updatedFoundationalDataType are not directly saved in db
        em.detach(updatedFoundationalDataType);
        updatedFoundationalDataType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requiredInHierarchy(UPDATED_REQUIRED_IN_HIERARCHY)
            .requiredInJobs(UPDATED_REQUIRED_IN_JOBS)
            .requiredInSow(UPDATED_REQUIRED_IN_SOW)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFoundationalDataTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFoundationalDataType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFoundationalDataType))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
        FoundationalDataType testFoundationalDataType = foundationalDataTypeList.get(foundationalDataTypeList.size() - 1);
        assertThat(testFoundationalDataType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoundationalDataType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoundationalDataType.getRequiredInHierarchy()).isEqualTo(UPDATED_REQUIRED_IN_HIERARCHY);
        assertThat(testFoundationalDataType.getRequiredInJobs()).isEqualTo(UPDATED_REQUIRED_IN_JOBS);
        assertThat(testFoundationalDataType.getRequiredInSow()).isEqualTo(UPDATED_REQUIRED_IN_SOW);
        assertThat(testFoundationalDataType.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testFoundationalDataType.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFoundationalDataType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoundationalDataType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFoundationalDataType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalDataType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foundationalDataType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoundationalDataTypeWithPatch() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();

        // Update the foundationalDataType using partial update
        FoundationalDataType partialUpdatedFoundationalDataType = new FoundationalDataType();
        partialUpdatedFoundationalDataType.setId(foundationalDataType.getId());

        partialUpdatedFoundationalDataType
            .description(UPDATED_DESCRIPTION)
            .requiredInHierarchy(UPDATED_REQUIRED_IN_HIERARCHY)
            .requiredInSow(UPDATED_REQUIRED_IN_SOW)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restFoundationalDataTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoundationalDataType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoundationalDataType))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
        FoundationalDataType testFoundationalDataType = foundationalDataTypeList.get(foundationalDataTypeList.size() - 1);
        assertThat(testFoundationalDataType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoundationalDataType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoundationalDataType.getRequiredInHierarchy()).isEqualTo(UPDATED_REQUIRED_IN_HIERARCHY);
        assertThat(testFoundationalDataType.getRequiredInJobs()).isEqualTo(DEFAULT_REQUIRED_IN_JOBS);
        assertThat(testFoundationalDataType.getRequiredInSow()).isEqualTo(UPDATED_REQUIRED_IN_SOW);
        assertThat(testFoundationalDataType.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testFoundationalDataType.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFoundationalDataType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFoundationalDataType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFoundationalDataType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalDataType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateFoundationalDataTypeWithPatch() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();

        // Update the foundationalDataType using partial update
        FoundationalDataType partialUpdatedFoundationalDataType = new FoundationalDataType();
        partialUpdatedFoundationalDataType.setId(foundationalDataType.getId());

        partialUpdatedFoundationalDataType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requiredInHierarchy(UPDATED_REQUIRED_IN_HIERARCHY)
            .requiredInJobs(UPDATED_REQUIRED_IN_JOBS)
            .requiredInSow(UPDATED_REQUIRED_IN_SOW)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFoundationalDataTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoundationalDataType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoundationalDataType))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
        FoundationalDataType testFoundationalDataType = foundationalDataTypeList.get(foundationalDataTypeList.size() - 1);
        assertThat(testFoundationalDataType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoundationalDataType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoundationalDataType.getRequiredInHierarchy()).isEqualTo(UPDATED_REQUIRED_IN_HIERARCHY);
        assertThat(testFoundationalDataType.getRequiredInJobs()).isEqualTo(UPDATED_REQUIRED_IN_JOBS);
        assertThat(testFoundationalDataType.getRequiredInSow()).isEqualTo(UPDATED_REQUIRED_IN_SOW);
        assertThat(testFoundationalDataType.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testFoundationalDataType.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFoundationalDataType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoundationalDataType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFoundationalDataType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalDataType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foundationalDataType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoundationalDataType() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataTypeRepository.findAll().size();
        foundationalDataType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalDataType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoundationalDataType in the database
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoundationalDataType() throws Exception {
        // Initialize the database
        foundationalDataTypeRepository.saveAndFlush(foundationalDataType);

        int databaseSizeBeforeDelete = foundationalDataTypeRepository.findAll().size();

        // Delete the foundationalDataType
        restFoundationalDataTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, foundationalDataType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoundationalDataType> foundationalDataTypeList = foundationalDataTypeRepository.findAll();
        assertThat(foundationalDataTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
