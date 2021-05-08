package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.CustomFieldType;
import com.simplify.vms.onboard.data.repository.CustomFieldTypeRepository;
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
 * Integration tests for the {@link CustomFieldTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomFieldTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/custom-field-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomFieldTypeRepository customFieldTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomFieldTypeMockMvc;

    private CustomFieldType customFieldType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomFieldType createEntity(EntityManager em) {
        CustomFieldType customFieldType = new CustomFieldType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return customFieldType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomFieldType createUpdatedEntity(EntityManager em) {
        CustomFieldType customFieldType = new CustomFieldType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return customFieldType;
    }

    @BeforeEach
    public void initTest() {
        customFieldType = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomFieldType() throws Exception {
        int databaseSizeBeforeCreate = customFieldTypeRepository.findAll().size();
        // Create the CustomFieldType
        restCustomFieldTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isCreated());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomFieldType testCustomFieldType = customFieldTypeList.get(customFieldTypeList.size() - 1);
        assertThat(testCustomFieldType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomFieldType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomFieldType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomFieldType.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCustomFieldType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomFieldType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCustomFieldType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCustomFieldType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCustomFieldTypeWithExistingId() throws Exception {
        // Create the CustomFieldType with an existing ID
        customFieldType.setId(1L);

        int databaseSizeBeforeCreate = customFieldTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomFieldTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomFieldTypes() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        // Get all the customFieldTypeList
        restCustomFieldTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customFieldType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCustomFieldType() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        // Get the customFieldType
        restCustomFieldTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, customFieldType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customFieldType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomFieldType() throws Exception {
        // Get the customFieldType
        restCustomFieldTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomFieldType() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();

        // Update the customFieldType
        CustomFieldType updatedCustomFieldType = customFieldTypeRepository.findById(customFieldType.getId()).get();
        // Disconnect from session so that the updates on updatedCustomFieldType are not directly saved in db
        em.detach(updatedCustomFieldType);
        updatedCustomFieldType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomFieldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomFieldType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomFieldType))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldType testCustomFieldType = customFieldTypeList.get(customFieldTypeList.size() - 1);
        assertThat(testCustomFieldType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomFieldType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomFieldType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomFieldType.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomFieldType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomFieldType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCustomFieldType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customFieldType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomFieldTypeWithPatch() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();

        // Update the customFieldType using partial update
        CustomFieldType partialUpdatedCustomFieldType = new CustomFieldType();
        partialUpdatedCustomFieldType.setId(customFieldType.getId());

        partialUpdatedCustomFieldType
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomFieldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomFieldType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomFieldType))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldType testCustomFieldType = customFieldTypeList.get(customFieldTypeList.size() - 1);
        assertThat(testCustomFieldType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomFieldType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomFieldType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomFieldType.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCustomFieldType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomFieldType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCustomFieldType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCustomFieldTypeWithPatch() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();

        // Update the customFieldType using partial update
        CustomFieldType partialUpdatedCustomFieldType = new CustomFieldType();
        partialUpdatedCustomFieldType.setId(customFieldType.getId());

        partialUpdatedCustomFieldType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomFieldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomFieldType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomFieldType))
            )
            .andExpect(status().isOk());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomFieldType testCustomFieldType = customFieldTypeList.get(customFieldTypeList.size() - 1);
        assertThat(testCustomFieldType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomFieldType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomFieldType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomFieldType.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomFieldType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomFieldType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomFieldType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCustomFieldType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customFieldType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomFieldType() throws Exception {
        int databaseSizeBeforeUpdate = customFieldTypeRepository.findAll().size();
        customFieldType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomFieldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customFieldType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomFieldType in the database
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomFieldType() throws Exception {
        // Initialize the database
        customFieldTypeRepository.saveAndFlush(customFieldType);

        int databaseSizeBeforeDelete = customFieldTypeRepository.findAll().size();

        // Delete the customFieldType
        restCustomFieldTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, customFieldType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomFieldType> customFieldTypeList = customFieldTypeRepository.findAll();
        assertThat(customFieldTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
