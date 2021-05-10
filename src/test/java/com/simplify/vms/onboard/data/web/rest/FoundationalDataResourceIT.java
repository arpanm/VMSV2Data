package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.FoundationalData;
import com.simplify.vms.onboard.data.repository.FoundationalDataRepository;
import com.simplify.vms.onboard.data.service.FoundationalDataService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FoundationalDataResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FoundationalDataResourceIT {

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

    private static final String ENTITY_API_URL = "/api/foundational-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoundationalDataRepository foundationalDataRepository;

    @Mock
    private FoundationalDataRepository foundationalDataRepositoryMock;

    @Mock
    private FoundationalDataService foundationalDataServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoundationalDataMockMvc;

    private FoundationalData foundationalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoundationalData createEntity(EntityManager em) {
        FoundationalData foundationalData = new FoundationalData()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return foundationalData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoundationalData createUpdatedEntity(EntityManager em) {
        FoundationalData foundationalData = new FoundationalData()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return foundationalData;
    }

    @BeforeEach
    public void initTest() {
        foundationalData = createEntity(em);
    }

    @Test
    @Transactional
    void createFoundationalData() throws Exception {
        int databaseSizeBeforeCreate = foundationalDataRepository.findAll().size();
        // Create the FoundationalData
        restFoundationalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isCreated());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeCreate + 1);
        FoundationalData testFoundationalData = foundationalDataList.get(foundationalDataList.size() - 1);
        assertThat(testFoundationalData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoundationalData.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFoundationalData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFoundationalData.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFoundationalData.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFoundationalData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFoundationalData.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFoundationalData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createFoundationalDataWithExistingId() throws Exception {
        // Create the FoundationalData with an existing ID
        foundationalData.setId(1L);

        int databaseSizeBeforeCreate = foundationalDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoundationalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoundationalData() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        // Get all the foundationalDataList
        restFoundationalDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foundationalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFoundationalDataWithEagerRelationshipsIsEnabled() throws Exception {
        when(foundationalDataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFoundationalDataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(foundationalDataServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFoundationalDataWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(foundationalDataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFoundationalDataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(foundationalDataServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getFoundationalData() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        // Get the foundationalData
        restFoundationalDataMockMvc
            .perform(get(ENTITY_API_URL_ID, foundationalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foundationalData.getId().intValue()))
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
    void getNonExistingFoundationalData() throws Exception {
        // Get the foundationalData
        restFoundationalDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFoundationalData() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();

        // Update the foundationalData
        FoundationalData updatedFoundationalData = foundationalDataRepository.findById(foundationalData.getId()).get();
        // Disconnect from session so that the updates on updatedFoundationalData are not directly saved in db
        em.detach(updatedFoundationalData);
        updatedFoundationalData
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFoundationalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFoundationalData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFoundationalData))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
        FoundationalData testFoundationalData = foundationalDataList.get(foundationalDataList.size() - 1);
        assertThat(testFoundationalData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoundationalData.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFoundationalData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoundationalData.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFoundationalData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoundationalData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFoundationalData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foundationalData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoundationalDataWithPatch() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();

        // Update the foundationalData using partial update
        FoundationalData partialUpdatedFoundationalData = new FoundationalData();
        partialUpdatedFoundationalData.setId(foundationalData.getId());

        partialUpdatedFoundationalData
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFoundationalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoundationalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoundationalData))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
        FoundationalData testFoundationalData = foundationalDataList.get(foundationalDataList.size() - 1);
        assertThat(testFoundationalData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoundationalData.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFoundationalData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFoundationalData.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFoundationalData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoundationalData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFoundationalData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateFoundationalDataWithPatch() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();

        // Update the foundationalData using partial update
        FoundationalData partialUpdatedFoundationalData = new FoundationalData();
        partialUpdatedFoundationalData.setId(foundationalData.getId());

        partialUpdatedFoundationalData
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFoundationalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoundationalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoundationalData))
            )
            .andExpect(status().isOk());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
        FoundationalData testFoundationalData = foundationalDataList.get(foundationalDataList.size() - 1);
        assertThat(testFoundationalData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoundationalData.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFoundationalData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoundationalData.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFoundationalData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoundationalData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFoundationalData.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFoundationalData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foundationalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoundationalData() throws Exception {
        int databaseSizeBeforeUpdate = foundationalDataRepository.findAll().size();
        foundationalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoundationalDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foundationalData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoundationalData in the database
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoundationalData() throws Exception {
        // Initialize the database
        foundationalDataRepository.saveAndFlush(foundationalData);

        int databaseSizeBeforeDelete = foundationalDataRepository.findAll().size();

        // Delete the foundationalData
        restFoundationalDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, foundationalData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoundationalData> foundationalDataList = foundationalDataRepository.findAll();
        assertThat(foundationalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
