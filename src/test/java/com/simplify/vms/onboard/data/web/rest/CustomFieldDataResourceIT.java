package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.CustomFieldData;
import com.simplify.vms.onboard.data.repository.CustomFieldDataRepository;
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
 * Integration tests for the {@link CustomFieldDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomFieldDataResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/custom-field-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomFieldDataRepository customFieldDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomFieldDataMockMvc;

    private CustomFieldData customFieldData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomFieldData createEntity(EntityManager em) {
        CustomFieldData customFieldData = new CustomFieldData()
            .value(DEFAULT_VALUE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return customFieldData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomFieldData createUpdatedEntity(EntityManager em) {
        CustomFieldData customFieldData = new CustomFieldData()
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return customFieldData;
    }

    @BeforeEach
    public void initTest() {
        customFieldData = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomFieldData() throws Exception {
        int databaseSizeBeforeCreate = customFieldDataRepository.findAll().size();
        // Create the CustomFieldData
        restCustomFieldDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isCreated());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeCreate + 1);
        CustomFieldData testCustomFieldData = customFieldDataList.get(customFieldDataList.size() - 1);
        assertThat(testCustomFieldData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustomFieldData.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCustomFieldData.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomFieldData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCustomFieldData.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCustomFieldData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCustomFieldDataWithExistingId() throws Exception {
        // Create the CustomFieldData with an existing ID
        customFieldData.setId(1L);

        int databaseSizeBeforeCreate = customFieldDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomFieldDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomFieldData() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        // Get all the customFieldDataList
        restCustomFieldDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customFieldData.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCustomFieldData() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        // Get the customFieldData
        restCustomFieldDataMockMvc
            .perform(get(ENTITY_API_URL_ID, customFieldData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customFieldData.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomFieldData() throws Exception {
        // Get the customFieldData
        restCustomFieldDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomFieldData() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();

        // Update the customFieldData
        CustomFieldData updatedCustomFieldData = customFieldDataRepository.findById(customFieldData.getId()).get();
        // Disconnect from session so that the updates on updatedCustomFieldData are not directly saved in db
        em.detach(updatedCustomFieldData);
        updatedCustomFieldData
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomFieldDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomFieldData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomFieldData))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldData testCustomFieldData = customFieldDataList.get(customFieldDataList.size() - 1);
        assertThat(testCustomFieldData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustomFieldData.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomFieldData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomFieldData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCustomFieldData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customFieldData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomFieldDataWithPatch() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();

        // Update the customFieldData using partial update
        CustomFieldData partialUpdatedCustomFieldData = new CustomFieldData();
        partialUpdatedCustomFieldData.setId(customFieldData.getId());

        partialUpdatedCustomFieldData.isActive(UPDATED_IS_ACTIVE).createdBy(UPDATED_CREATED_BY).updatedBy(UPDATED_UPDATED_BY);

        restCustomFieldDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomFieldData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomFieldData))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldData testCustomFieldData = customFieldDataList.get(customFieldDataList.size() - 1);
        assertThat(testCustomFieldData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustomFieldData.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomFieldData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCustomFieldData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCustomFieldData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCustomFieldDataWithPatch() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();

        // Update the customFieldData using partial update
        CustomFieldData partialUpdatedCustomFieldData = new CustomFieldData();
        partialUpdatedCustomFieldData.setId(customFieldData.getId());

        partialUpdatedCustomFieldData
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomFieldDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomFieldData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomFieldData))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldData testCustomFieldData = customFieldDataList.get(customFieldDataList.size() - 1);
        assertThat(testCustomFieldData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustomFieldData.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomFieldData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomFieldData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCustomFieldData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customFieldData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomFieldData() throws Exception {
        int databaseSizeBeforeUpdate = customFieldDataRepository.findAll().size();
        customFieldData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomFieldData in the database
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomFieldData() throws Exception {
        // Initialize the database
        customFieldDataRepository.saveAndFlush(customFieldData);

        int databaseSizeBeforeDelete = customFieldDataRepository.findAll().size();

        // Delete the customFieldData
        restCustomFieldDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, customFieldData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomFieldData> customFieldDataList = customFieldDataRepository.findAll();
        assertThat(customFieldDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
