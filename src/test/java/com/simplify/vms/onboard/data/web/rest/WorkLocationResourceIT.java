package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.WorkLocation;
import com.simplify.vms.onboard.data.domain.enumeration.Country;
import com.simplify.vms.onboard.data.domain.enumeration.State;
import com.simplify.vms.onboard.data.repository.WorkLocationRepository;
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
 * Integration tests for the {@link WorkLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkLocationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Country DEFAULT_COUNTRY = Country.US;
    private static final Country UPDATED_COUNTRY = Country.India;

    private static final State DEFAULT_STATE = State.Alabama;
    private static final State UPDATED_STATE = State.Alaska;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/work-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkLocationRepository workLocationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkLocationMockMvc;

    private WorkLocation workLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLocation createEntity(EntityManager em) {
        WorkLocation workLocation = new WorkLocation()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .country(DEFAULT_COUNTRY)
            .state(DEFAULT_STATE)
            .address(DEFAULT_ADDRESS)
            .fileUploadPath(DEFAULT_FILE_UPLOAD_PATH)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return workLocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLocation createUpdatedEntity(EntityManager em) {
        WorkLocation workLocation = new WorkLocation()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .address(UPDATED_ADDRESS)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return workLocation;
    }

    @BeforeEach
    public void initTest() {
        workLocation = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkLocation() throws Exception {
        int databaseSizeBeforeCreate = workLocationRepository.findAll().size();
        // Create the WorkLocation
        restWorkLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocation)))
            .andExpect(status().isCreated());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeCreate + 1);
        WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
        assertThat(testWorkLocation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWorkLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWorkLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWorkLocation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWorkLocation.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testWorkLocation.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWorkLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkLocation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testWorkLocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWorkLocation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createWorkLocationWithExistingId() throws Exception {
        // Create the WorkLocation with an existing ID
        workLocation.setId(1L);

        int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocation)))
            .andExpect(status().isBadRequest());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkLocations() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        // Get all the workLocationList
        restWorkLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].fileUploadPath").value(hasItem(DEFAULT_FILE_UPLOAD_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getWorkLocation() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        // Get the workLocation
        restWorkLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, workLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workLocation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.fileUploadPath").value(DEFAULT_FILE_UPLOAD_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWorkLocation() throws Exception {
        // Get the workLocation
        restWorkLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkLocation() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

        // Update the workLocation
        WorkLocation updatedWorkLocation = workLocationRepository.findById(workLocation.getId()).get();
        // Disconnect from session so that the updates on updatedWorkLocation are not directly saved in db
        em.detach(updatedWorkLocation);
        updatedWorkLocation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .address(UPDATED_ADDRESS)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restWorkLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
        assertThat(testWorkLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkLocation.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testWorkLocation.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWorkLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkLocation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testWorkLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWorkLocation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkLocationWithPatch() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

        // Update the workLocation using partial update
        WorkLocation partialUpdatedWorkLocation = new WorkLocation();
        partialUpdatedWorkLocation.setId(workLocation.getId());

        partialUpdatedWorkLocation.code(UPDATED_CODE).country(UPDATED_COUNTRY).state(UPDATED_STATE).address(UPDATED_ADDRESS);

        restWorkLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
        assertThat(testWorkLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkLocation.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testWorkLocation.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWorkLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkLocation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testWorkLocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWorkLocation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateWorkLocationWithPatch() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

        // Update the workLocation using partial update
        WorkLocation partialUpdatedWorkLocation = new WorkLocation();
        partialUpdatedWorkLocation.setId(workLocation.getId());

        partialUpdatedWorkLocation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .address(UPDATED_ADDRESS)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restWorkLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
        assertThat(testWorkLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkLocation.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testWorkLocation.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWorkLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkLocation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testWorkLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWorkLocation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();
        workLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLocationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workLocation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLocation in the database
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkLocation() throws Exception {
        // Initialize the database
        workLocationRepository.saveAndFlush(workLocation);

        int databaseSizeBeforeDelete = workLocationRepository.findAll().size();

        // Delete the workLocation
        restWorkLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, workLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkLocation> workLocationList = workLocationRepository.findAll();
        assertThat(workLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
