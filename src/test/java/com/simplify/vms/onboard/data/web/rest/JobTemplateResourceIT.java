package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.JobTemplate;
import com.simplify.vms.onboard.data.domain.enumeration.JobTemplateDistributionType;
import com.simplify.vms.onboard.data.repository.JobTemplateRepository;
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
 * Integration tests for the {@link JobTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobTemplateResourceIT {

    private static final String DEFAULT_TEMPLATE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_JOB_LEVEL = 1;
    private static final Integer UPDATED_JOB_LEVEL = 2;

    private static final Boolean DEFAULT_IS_DESCRIPTION_EDITABLE = false;
    private static final Boolean UPDATED_IS_DESCRIPTION_EDITABLE = true;

    private static final JobTemplateDistributionType DEFAULT_DISTRIBUTION_TYPE = JobTemplateDistributionType.Automatic_Submit;
    private static final JobTemplateDistributionType UPDATED_DISTRIBUTION_TYPE = JobTemplateDistributionType.Automatic_Approval;

    private static final Integer DEFAULT_DISTRIBUTION_LIMIT = 1;
    private static final Integer UPDATED_DISTRIBUTION_LIMIT = 2;

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

    private static final String ENTITY_API_URL = "/api/job-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobTemplateRepository jobTemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobTemplateMockMvc;

    private JobTemplate jobTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTemplate createEntity(EntityManager em) {
        JobTemplate jobTemplate = new JobTemplate()
            .templateTitle(DEFAULT_TEMPLATE_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .jobLevel(DEFAULT_JOB_LEVEL)
            .isDescriptionEditable(DEFAULT_IS_DESCRIPTION_EDITABLE)
            .distributionType(DEFAULT_DISTRIBUTION_TYPE)
            .distributionLimit(DEFAULT_DISTRIBUTION_LIMIT)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return jobTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTemplate createUpdatedEntity(EntityManager em) {
        JobTemplate jobTemplate = new JobTemplate()
            .templateTitle(UPDATED_TEMPLATE_TITLE)
            .description(UPDATED_DESCRIPTION)
            .jobLevel(UPDATED_JOB_LEVEL)
            .isDescriptionEditable(UPDATED_IS_DESCRIPTION_EDITABLE)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .distributionLimit(UPDATED_DISTRIBUTION_LIMIT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return jobTemplate;
    }

    @BeforeEach
    public void initTest() {
        jobTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createJobTemplate() throws Exception {
        int databaseSizeBeforeCreate = jobTemplateRepository.findAll().size();
        // Create the JobTemplate
        restJobTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplate)))
            .andExpect(status().isCreated());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        JobTemplate testJobTemplate = jobTemplateList.get(jobTemplateList.size() - 1);
        assertThat(testJobTemplate.getTemplateTitle()).isEqualTo(DEFAULT_TEMPLATE_TITLE);
        assertThat(testJobTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobTemplate.getJobLevel()).isEqualTo(DEFAULT_JOB_LEVEL);
        assertThat(testJobTemplate.getIsDescriptionEditable()).isEqualTo(DEFAULT_IS_DESCRIPTION_EDITABLE);
        assertThat(testJobTemplate.getDistributionType()).isEqualTo(DEFAULT_DISTRIBUTION_TYPE);
        assertThat(testJobTemplate.getDistributionLimit()).isEqualTo(DEFAULT_DISTRIBUTION_LIMIT);
        assertThat(testJobTemplate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobTemplate.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobTemplate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobTemplate.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createJobTemplateWithExistingId() throws Exception {
        // Create the JobTemplate with an existing ID
        jobTemplate.setId(1L);

        int databaseSizeBeforeCreate = jobTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobTemplates() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        // Get all the jobTemplateList
        restJobTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateTitle").value(hasItem(DEFAULT_TEMPLATE_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].jobLevel").value(hasItem(DEFAULT_JOB_LEVEL)))
            .andExpect(jsonPath("$.[*].isDescriptionEditable").value(hasItem(DEFAULT_IS_DESCRIPTION_EDITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].distributionType").value(hasItem(DEFAULT_DISTRIBUTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].distributionLimit").value(hasItem(DEFAULT_DISTRIBUTION_LIMIT)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getJobTemplate() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        // Get the jobTemplate
        restJobTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, jobTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobTemplate.getId().intValue()))
            .andExpect(jsonPath("$.templateTitle").value(DEFAULT_TEMPLATE_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.jobLevel").value(DEFAULT_JOB_LEVEL))
            .andExpect(jsonPath("$.isDescriptionEditable").value(DEFAULT_IS_DESCRIPTION_EDITABLE.booleanValue()))
            .andExpect(jsonPath("$.distributionType").value(DEFAULT_DISTRIBUTION_TYPE.toString()))
            .andExpect(jsonPath("$.distributionLimit").value(DEFAULT_DISTRIBUTION_LIMIT))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingJobTemplate() throws Exception {
        // Get the jobTemplate
        restJobTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobTemplate() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();

        // Update the jobTemplate
        JobTemplate updatedJobTemplate = jobTemplateRepository.findById(jobTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedJobTemplate are not directly saved in db
        em.detach(updatedJobTemplate);
        updatedJobTemplate
            .templateTitle(UPDATED_TEMPLATE_TITLE)
            .description(UPDATED_DESCRIPTION)
            .jobLevel(UPDATED_JOB_LEVEL)
            .isDescriptionEditable(UPDATED_IS_DESCRIPTION_EDITABLE)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .distributionLimit(UPDATED_DISTRIBUTION_LIMIT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobTemplate))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
        JobTemplate testJobTemplate = jobTemplateList.get(jobTemplateList.size() - 1);
        assertThat(testJobTemplate.getTemplateTitle()).isEqualTo(UPDATED_TEMPLATE_TITLE);
        assertThat(testJobTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobTemplate.getJobLevel()).isEqualTo(UPDATED_JOB_LEVEL);
        assertThat(testJobTemplate.getIsDescriptionEditable()).isEqualTo(UPDATED_IS_DESCRIPTION_EDITABLE);
        assertThat(testJobTemplate.getDistributionType()).isEqualTo(UPDATED_DISTRIBUTION_TYPE);
        assertThat(testJobTemplate.getDistributionLimit()).isEqualTo(UPDATED_DISTRIBUTION_LIMIT);
        assertThat(testJobTemplate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobTemplate.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobTemplate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobTemplate.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobTemplateWithPatch() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();

        // Update the jobTemplate using partial update
        JobTemplate partialUpdatedJobTemplate = new JobTemplate();
        partialUpdatedJobTemplate.setId(jobTemplate.getId());

        partialUpdatedJobTemplate
            .templateTitle(UPDATED_TEMPLATE_TITLE)
            .description(UPDATED_DESCRIPTION)
            .jobLevel(UPDATED_JOB_LEVEL)
            .isDescriptionEditable(UPDATED_IS_DESCRIPTION_EDITABLE)
            .distributionType(UPDATED_DISTRIBUTION_TYPE);

        restJobTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobTemplate))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
        JobTemplate testJobTemplate = jobTemplateList.get(jobTemplateList.size() - 1);
        assertThat(testJobTemplate.getTemplateTitle()).isEqualTo(UPDATED_TEMPLATE_TITLE);
        assertThat(testJobTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobTemplate.getJobLevel()).isEqualTo(UPDATED_JOB_LEVEL);
        assertThat(testJobTemplate.getIsDescriptionEditable()).isEqualTo(UPDATED_IS_DESCRIPTION_EDITABLE);
        assertThat(testJobTemplate.getDistributionType()).isEqualTo(UPDATED_DISTRIBUTION_TYPE);
        assertThat(testJobTemplate.getDistributionLimit()).isEqualTo(DEFAULT_DISTRIBUTION_LIMIT);
        assertThat(testJobTemplate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobTemplate.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobTemplate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobTemplate.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateJobTemplateWithPatch() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();

        // Update the jobTemplate using partial update
        JobTemplate partialUpdatedJobTemplate = new JobTemplate();
        partialUpdatedJobTemplate.setId(jobTemplate.getId());

        partialUpdatedJobTemplate
            .templateTitle(UPDATED_TEMPLATE_TITLE)
            .description(UPDATED_DESCRIPTION)
            .jobLevel(UPDATED_JOB_LEVEL)
            .isDescriptionEditable(UPDATED_IS_DESCRIPTION_EDITABLE)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .distributionLimit(UPDATED_DISTRIBUTION_LIMIT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobTemplate))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
        JobTemplate testJobTemplate = jobTemplateList.get(jobTemplateList.size() - 1);
        assertThat(testJobTemplate.getTemplateTitle()).isEqualTo(UPDATED_TEMPLATE_TITLE);
        assertThat(testJobTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobTemplate.getJobLevel()).isEqualTo(UPDATED_JOB_LEVEL);
        assertThat(testJobTemplate.getIsDescriptionEditable()).isEqualTo(UPDATED_IS_DESCRIPTION_EDITABLE);
        assertThat(testJobTemplate.getDistributionType()).isEqualTo(UPDATED_DISTRIBUTION_TYPE);
        assertThat(testJobTemplate.getDistributionLimit()).isEqualTo(UPDATED_DISTRIBUTION_LIMIT);
        assertThat(testJobTemplate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobTemplate.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobTemplate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobTemplate.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRepository.findAll().size();
        jobTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobTemplate in the database
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobTemplate() throws Exception {
        // Initialize the database
        jobTemplateRepository.saveAndFlush(jobTemplate);

        int databaseSizeBeforeDelete = jobTemplateRepository.findAll().size();

        // Delete the jobTemplate
        restJobTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobTemplate> jobTemplateList = jobTemplateRepository.findAll();
        assertThat(jobTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
