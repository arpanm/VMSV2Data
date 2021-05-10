package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.JobCategoryTitle;
import com.simplify.vms.onboard.data.repository.JobCategoryTitleRepository;
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
 * Integration tests for the {@link JobCategoryTitleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobCategoryTitleResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/job-category-titles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobCategoryTitleRepository jobCategoryTitleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobCategoryTitleMockMvc;

    private JobCategoryTitle jobCategoryTitle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCategoryTitle createEntity(EntityManager em) {
        JobCategoryTitle jobCategoryTitle = new JobCategoryTitle()
            .category(DEFAULT_CATEGORY)
            .title(DEFAULT_TITLE)
            .code(DEFAULT_CODE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return jobCategoryTitle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCategoryTitle createUpdatedEntity(EntityManager em) {
        JobCategoryTitle jobCategoryTitle = new JobCategoryTitle()
            .category(UPDATED_CATEGORY)
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return jobCategoryTitle;
    }

    @BeforeEach
    public void initTest() {
        jobCategoryTitle = createEntity(em);
    }

    @Test
    @Transactional
    void createJobCategoryTitle() throws Exception {
        int databaseSizeBeforeCreate = jobCategoryTitleRepository.findAll().size();
        // Create the JobCategoryTitle
        restJobCategoryTitleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isCreated());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeCreate + 1);
        JobCategoryTitle testJobCategoryTitle = jobCategoryTitleList.get(jobCategoryTitleList.size() - 1);
        assertThat(testJobCategoryTitle.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testJobCategoryTitle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobCategoryTitle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJobCategoryTitle.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobCategoryTitle.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobCategoryTitle.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobCategoryTitle.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobCategoryTitle.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createJobCategoryTitleWithExistingId() throws Exception {
        // Create the JobCategoryTitle with an existing ID
        jobCategoryTitle.setId(1L);

        int databaseSizeBeforeCreate = jobCategoryTitleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobCategoryTitleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobCategoryTitles() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        // Get all the jobCategoryTitleList
        restJobCategoryTitleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobCategoryTitle.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getJobCategoryTitle() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        // Get the jobCategoryTitle
        restJobCategoryTitleMockMvc
            .perform(get(ENTITY_API_URL_ID, jobCategoryTitle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobCategoryTitle.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingJobCategoryTitle() throws Exception {
        // Get the jobCategoryTitle
        restJobCategoryTitleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobCategoryTitle() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();

        // Update the jobCategoryTitle
        JobCategoryTitle updatedJobCategoryTitle = jobCategoryTitleRepository.findById(jobCategoryTitle.getId()).get();
        // Disconnect from session so that the updates on updatedJobCategoryTitle are not directly saved in db
        em.detach(updatedJobCategoryTitle);
        updatedJobCategoryTitle
            .category(UPDATED_CATEGORY)
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobCategoryTitleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobCategoryTitle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobCategoryTitle))
            )
            .andExpect(status().isOk());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
        JobCategoryTitle testJobCategoryTitle = jobCategoryTitleList.get(jobCategoryTitleList.size() - 1);
        assertThat(testJobCategoryTitle.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testJobCategoryTitle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobCategoryTitle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJobCategoryTitle.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobCategoryTitle.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobCategoryTitle.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobCategoryTitle.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobCategoryTitle.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobCategoryTitle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobCategoryTitleWithPatch() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();

        // Update the jobCategoryTitle using partial update
        JobCategoryTitle partialUpdatedJobCategoryTitle = new JobCategoryTitle();
        partialUpdatedJobCategoryTitle.setId(jobCategoryTitle.getId());

        partialUpdatedJobCategoryTitle
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobCategoryTitleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCategoryTitle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCategoryTitle))
            )
            .andExpect(status().isOk());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
        JobCategoryTitle testJobCategoryTitle = jobCategoryTitleList.get(jobCategoryTitleList.size() - 1);
        assertThat(testJobCategoryTitle.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testJobCategoryTitle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobCategoryTitle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJobCategoryTitle.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobCategoryTitle.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobCategoryTitle.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobCategoryTitle.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobCategoryTitle.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateJobCategoryTitleWithPatch() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();

        // Update the jobCategoryTitle using partial update
        JobCategoryTitle partialUpdatedJobCategoryTitle = new JobCategoryTitle();
        partialUpdatedJobCategoryTitle.setId(jobCategoryTitle.getId());

        partialUpdatedJobCategoryTitle
            .category(UPDATED_CATEGORY)
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobCategoryTitleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCategoryTitle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCategoryTitle))
            )
            .andExpect(status().isOk());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
        JobCategoryTitle testJobCategoryTitle = jobCategoryTitleList.get(jobCategoryTitleList.size() - 1);
        assertThat(testJobCategoryTitle.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testJobCategoryTitle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobCategoryTitle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJobCategoryTitle.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobCategoryTitle.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobCategoryTitle.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobCategoryTitle.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobCategoryTitle.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobCategoryTitle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobCategoryTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryTitleRepository.findAll().size();
        jobCategoryTitle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryTitleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCategoryTitle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCategoryTitle in the database
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobCategoryTitle() throws Exception {
        // Initialize the database
        jobCategoryTitleRepository.saveAndFlush(jobCategoryTitle);

        int databaseSizeBeforeDelete = jobCategoryTitleRepository.findAll().size();

        // Delete the jobCategoryTitle
        restJobCategoryTitleMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobCategoryTitle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobCategoryTitle> jobCategoryTitleList = jobCategoryTitleRepository.findAll();
        assertThat(jobCategoryTitleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
