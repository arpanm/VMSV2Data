package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.JobTemplateRateCard;
import com.simplify.vms.onboard.data.domain.enumeration.Currency;
import com.simplify.vms.onboard.data.repository.JobTemplateRateCardRepository;
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
 * Integration tests for the {@link JobTemplateRateCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobTemplateRateCardResourceIT {

    private static final Currency DEFAULT_CURRENCY = Currency.USD;
    private static final Currency UPDATED_CURRENCY = Currency.INR;

    private static final Integer DEFAULT_HOURLY_MIN = 1;
    private static final Integer UPDATED_HOURLY_MIN = 2;

    private static final Integer DEFAULT_HOURLY_MAX = 1;
    private static final Integer UPDATED_HOURLY_MAX = 2;

    private static final Integer DEFAULT_DAILY_MIN = 1;
    private static final Integer UPDATED_DAILY_MIN = 2;

    private static final Integer DEFAULT_DAILY_MAX = 1;
    private static final Integer UPDATED_DAILY_MAX = 2;

    private static final Integer DEFAULT_WEEKLY_MIN = 1;
    private static final Integer UPDATED_WEEKLY_MIN = 2;

    private static final Integer DEFAULT_WEEKLY_MAX = 1;
    private static final Integer UPDATED_WEEKLY_MAX = 2;

    private static final Integer DEFAULT_MONTHLY_MIN = 1;
    private static final Integer UPDATED_MONTHLY_MIN = 2;

    private static final Integer DEFAULT_MONTHLY_MAX = 1;
    private static final Integer UPDATED_MONTHLY_MAX = 2;

    private static final Integer DEFAULT_YEARLY_MIN = 1;
    private static final Integer UPDATED_YEARLY_MIN = 2;

    private static final Integer DEFAULT_YEARLY_MAX = 1;
    private static final Integer UPDATED_YEARLY_MAX = 2;

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

    private static final String ENTITY_API_URL = "/api/job-template-rate-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobTemplateRateCardRepository jobTemplateRateCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobTemplateRateCardMockMvc;

    private JobTemplateRateCard jobTemplateRateCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTemplateRateCard createEntity(EntityManager em) {
        JobTemplateRateCard jobTemplateRateCard = new JobTemplateRateCard()
            .currency(DEFAULT_CURRENCY)
            .hourlyMin(DEFAULT_HOURLY_MIN)
            .hourlyMax(DEFAULT_HOURLY_MAX)
            .dailyMin(DEFAULT_DAILY_MIN)
            .dailyMax(DEFAULT_DAILY_MAX)
            .weeklyMin(DEFAULT_WEEKLY_MIN)
            .weeklyMax(DEFAULT_WEEKLY_MAX)
            .monthlyMin(DEFAULT_MONTHLY_MIN)
            .monthlyMax(DEFAULT_MONTHLY_MAX)
            .yearlyMin(DEFAULT_YEARLY_MIN)
            .yearlyMax(DEFAULT_YEARLY_MAX)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return jobTemplateRateCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTemplateRateCard createUpdatedEntity(EntityManager em) {
        JobTemplateRateCard jobTemplateRateCard = new JobTemplateRateCard()
            .currency(UPDATED_CURRENCY)
            .hourlyMin(UPDATED_HOURLY_MIN)
            .hourlyMax(UPDATED_HOURLY_MAX)
            .dailyMin(UPDATED_DAILY_MIN)
            .dailyMax(UPDATED_DAILY_MAX)
            .weeklyMin(UPDATED_WEEKLY_MIN)
            .weeklyMax(UPDATED_WEEKLY_MAX)
            .monthlyMin(UPDATED_MONTHLY_MIN)
            .monthlyMax(UPDATED_MONTHLY_MAX)
            .yearlyMin(UPDATED_YEARLY_MIN)
            .yearlyMax(UPDATED_YEARLY_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return jobTemplateRateCard;
    }

    @BeforeEach
    public void initTest() {
        jobTemplateRateCard = createEntity(em);
    }

    @Test
    @Transactional
    void createJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeCreate = jobTemplateRateCardRepository.findAll().size();
        // Create the JobTemplateRateCard
        restJobTemplateRateCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isCreated());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeCreate + 1);
        JobTemplateRateCard testJobTemplateRateCard = jobTemplateRateCardList.get(jobTemplateRateCardList.size() - 1);
        assertThat(testJobTemplateRateCard.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testJobTemplateRateCard.getHourlyMin()).isEqualTo(DEFAULT_HOURLY_MIN);
        assertThat(testJobTemplateRateCard.getHourlyMax()).isEqualTo(DEFAULT_HOURLY_MAX);
        assertThat(testJobTemplateRateCard.getDailyMin()).isEqualTo(DEFAULT_DAILY_MIN);
        assertThat(testJobTemplateRateCard.getDailyMax()).isEqualTo(DEFAULT_DAILY_MAX);
        assertThat(testJobTemplateRateCard.getWeeklyMin()).isEqualTo(DEFAULT_WEEKLY_MIN);
        assertThat(testJobTemplateRateCard.getWeeklyMax()).isEqualTo(DEFAULT_WEEKLY_MAX);
        assertThat(testJobTemplateRateCard.getMonthlyMin()).isEqualTo(DEFAULT_MONTHLY_MIN);
        assertThat(testJobTemplateRateCard.getMonthlyMax()).isEqualTo(DEFAULT_MONTHLY_MAX);
        assertThat(testJobTemplateRateCard.getYearlyMin()).isEqualTo(DEFAULT_YEARLY_MIN);
        assertThat(testJobTemplateRateCard.getYearlyMax()).isEqualTo(DEFAULT_YEARLY_MAX);
        assertThat(testJobTemplateRateCard.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobTemplateRateCard.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobTemplateRateCard.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobTemplateRateCard.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobTemplateRateCard.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createJobTemplateRateCardWithExistingId() throws Exception {
        // Create the JobTemplateRateCard with an existing ID
        jobTemplateRateCard.setId(1L);

        int databaseSizeBeforeCreate = jobTemplateRateCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTemplateRateCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobTemplateRateCards() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        // Get all the jobTemplateRateCardList
        restJobTemplateRateCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTemplateRateCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].hourlyMin").value(hasItem(DEFAULT_HOURLY_MIN)))
            .andExpect(jsonPath("$.[*].hourlyMax").value(hasItem(DEFAULT_HOURLY_MAX)))
            .andExpect(jsonPath("$.[*].dailyMin").value(hasItem(DEFAULT_DAILY_MIN)))
            .andExpect(jsonPath("$.[*].dailyMax").value(hasItem(DEFAULT_DAILY_MAX)))
            .andExpect(jsonPath("$.[*].weeklyMin").value(hasItem(DEFAULT_WEEKLY_MIN)))
            .andExpect(jsonPath("$.[*].weeklyMax").value(hasItem(DEFAULT_WEEKLY_MAX)))
            .andExpect(jsonPath("$.[*].monthlyMin").value(hasItem(DEFAULT_MONTHLY_MIN)))
            .andExpect(jsonPath("$.[*].monthlyMax").value(hasItem(DEFAULT_MONTHLY_MAX)))
            .andExpect(jsonPath("$.[*].yearlyMin").value(hasItem(DEFAULT_YEARLY_MIN)))
            .andExpect(jsonPath("$.[*].yearlyMax").value(hasItem(DEFAULT_YEARLY_MAX)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getJobTemplateRateCard() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        // Get the jobTemplateRateCard
        restJobTemplateRateCardMockMvc
            .perform(get(ENTITY_API_URL_ID, jobTemplateRateCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobTemplateRateCard.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.hourlyMin").value(DEFAULT_HOURLY_MIN))
            .andExpect(jsonPath("$.hourlyMax").value(DEFAULT_HOURLY_MAX))
            .andExpect(jsonPath("$.dailyMin").value(DEFAULT_DAILY_MIN))
            .andExpect(jsonPath("$.dailyMax").value(DEFAULT_DAILY_MAX))
            .andExpect(jsonPath("$.weeklyMin").value(DEFAULT_WEEKLY_MIN))
            .andExpect(jsonPath("$.weeklyMax").value(DEFAULT_WEEKLY_MAX))
            .andExpect(jsonPath("$.monthlyMin").value(DEFAULT_MONTHLY_MIN))
            .andExpect(jsonPath("$.monthlyMax").value(DEFAULT_MONTHLY_MAX))
            .andExpect(jsonPath("$.yearlyMin").value(DEFAULT_YEARLY_MIN))
            .andExpect(jsonPath("$.yearlyMax").value(DEFAULT_YEARLY_MAX))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingJobTemplateRateCard() throws Exception {
        // Get the jobTemplateRateCard
        restJobTemplateRateCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobTemplateRateCard() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();

        // Update the jobTemplateRateCard
        JobTemplateRateCard updatedJobTemplateRateCard = jobTemplateRateCardRepository.findById(jobTemplateRateCard.getId()).get();
        // Disconnect from session so that the updates on updatedJobTemplateRateCard are not directly saved in db
        em.detach(updatedJobTemplateRateCard);
        updatedJobTemplateRateCard
            .currency(UPDATED_CURRENCY)
            .hourlyMin(UPDATED_HOURLY_MIN)
            .hourlyMax(UPDATED_HOURLY_MAX)
            .dailyMin(UPDATED_DAILY_MIN)
            .dailyMax(UPDATED_DAILY_MAX)
            .weeklyMin(UPDATED_WEEKLY_MIN)
            .weeklyMax(UPDATED_WEEKLY_MAX)
            .monthlyMin(UPDATED_MONTHLY_MIN)
            .monthlyMax(UPDATED_MONTHLY_MAX)
            .yearlyMin(UPDATED_YEARLY_MIN)
            .yearlyMax(UPDATED_YEARLY_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobTemplateRateCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobTemplateRateCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobTemplateRateCard))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
        JobTemplateRateCard testJobTemplateRateCard = jobTemplateRateCardList.get(jobTemplateRateCardList.size() - 1);
        assertThat(testJobTemplateRateCard.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testJobTemplateRateCard.getHourlyMin()).isEqualTo(UPDATED_HOURLY_MIN);
        assertThat(testJobTemplateRateCard.getHourlyMax()).isEqualTo(UPDATED_HOURLY_MAX);
        assertThat(testJobTemplateRateCard.getDailyMin()).isEqualTo(UPDATED_DAILY_MIN);
        assertThat(testJobTemplateRateCard.getDailyMax()).isEqualTo(UPDATED_DAILY_MAX);
        assertThat(testJobTemplateRateCard.getWeeklyMin()).isEqualTo(UPDATED_WEEKLY_MIN);
        assertThat(testJobTemplateRateCard.getWeeklyMax()).isEqualTo(UPDATED_WEEKLY_MAX);
        assertThat(testJobTemplateRateCard.getMonthlyMin()).isEqualTo(UPDATED_MONTHLY_MIN);
        assertThat(testJobTemplateRateCard.getMonthlyMax()).isEqualTo(UPDATED_MONTHLY_MAX);
        assertThat(testJobTemplateRateCard.getYearlyMin()).isEqualTo(UPDATED_YEARLY_MIN);
        assertThat(testJobTemplateRateCard.getYearlyMax()).isEqualTo(UPDATED_YEARLY_MAX);
        assertThat(testJobTemplateRateCard.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobTemplateRateCard.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobTemplateRateCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobTemplateRateCard.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobTemplateRateCard.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobTemplateRateCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobTemplateRateCardWithPatch() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();

        // Update the jobTemplateRateCard using partial update
        JobTemplateRateCard partialUpdatedJobTemplateRateCard = new JobTemplateRateCard();
        partialUpdatedJobTemplateRateCard.setId(jobTemplateRateCard.getId());

        partialUpdatedJobTemplateRateCard
            .currency(UPDATED_CURRENCY)
            .hourlyMax(UPDATED_HOURLY_MAX)
            .dailyMax(UPDATED_DAILY_MAX)
            .weeklyMin(UPDATED_WEEKLY_MIN)
            .weeklyMax(UPDATED_WEEKLY_MAX)
            .monthlyMin(UPDATED_MONTHLY_MIN)
            .yearlyMax(UPDATED_YEARLY_MAX)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restJobTemplateRateCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobTemplateRateCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobTemplateRateCard))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
        JobTemplateRateCard testJobTemplateRateCard = jobTemplateRateCardList.get(jobTemplateRateCardList.size() - 1);
        assertThat(testJobTemplateRateCard.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testJobTemplateRateCard.getHourlyMin()).isEqualTo(DEFAULT_HOURLY_MIN);
        assertThat(testJobTemplateRateCard.getHourlyMax()).isEqualTo(UPDATED_HOURLY_MAX);
        assertThat(testJobTemplateRateCard.getDailyMin()).isEqualTo(DEFAULT_DAILY_MIN);
        assertThat(testJobTemplateRateCard.getDailyMax()).isEqualTo(UPDATED_DAILY_MAX);
        assertThat(testJobTemplateRateCard.getWeeklyMin()).isEqualTo(UPDATED_WEEKLY_MIN);
        assertThat(testJobTemplateRateCard.getWeeklyMax()).isEqualTo(UPDATED_WEEKLY_MAX);
        assertThat(testJobTemplateRateCard.getMonthlyMin()).isEqualTo(UPDATED_MONTHLY_MIN);
        assertThat(testJobTemplateRateCard.getMonthlyMax()).isEqualTo(DEFAULT_MONTHLY_MAX);
        assertThat(testJobTemplateRateCard.getYearlyMin()).isEqualTo(DEFAULT_YEARLY_MIN);
        assertThat(testJobTemplateRateCard.getYearlyMax()).isEqualTo(UPDATED_YEARLY_MAX);
        assertThat(testJobTemplateRateCard.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobTemplateRateCard.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobTemplateRateCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobTemplateRateCard.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobTemplateRateCard.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateJobTemplateRateCardWithPatch() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();

        // Update the jobTemplateRateCard using partial update
        JobTemplateRateCard partialUpdatedJobTemplateRateCard = new JobTemplateRateCard();
        partialUpdatedJobTemplateRateCard.setId(jobTemplateRateCard.getId());

        partialUpdatedJobTemplateRateCard
            .currency(UPDATED_CURRENCY)
            .hourlyMin(UPDATED_HOURLY_MIN)
            .hourlyMax(UPDATED_HOURLY_MAX)
            .dailyMin(UPDATED_DAILY_MIN)
            .dailyMax(UPDATED_DAILY_MAX)
            .weeklyMin(UPDATED_WEEKLY_MIN)
            .weeklyMax(UPDATED_WEEKLY_MAX)
            .monthlyMin(UPDATED_MONTHLY_MIN)
            .monthlyMax(UPDATED_MONTHLY_MAX)
            .yearlyMin(UPDATED_YEARLY_MIN)
            .yearlyMax(UPDATED_YEARLY_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobTemplateRateCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobTemplateRateCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobTemplateRateCard))
            )
            .andExpect(status().isOk());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
        JobTemplateRateCard testJobTemplateRateCard = jobTemplateRateCardList.get(jobTemplateRateCardList.size() - 1);
        assertThat(testJobTemplateRateCard.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testJobTemplateRateCard.getHourlyMin()).isEqualTo(UPDATED_HOURLY_MIN);
        assertThat(testJobTemplateRateCard.getHourlyMax()).isEqualTo(UPDATED_HOURLY_MAX);
        assertThat(testJobTemplateRateCard.getDailyMin()).isEqualTo(UPDATED_DAILY_MIN);
        assertThat(testJobTemplateRateCard.getDailyMax()).isEqualTo(UPDATED_DAILY_MAX);
        assertThat(testJobTemplateRateCard.getWeeklyMin()).isEqualTo(UPDATED_WEEKLY_MIN);
        assertThat(testJobTemplateRateCard.getWeeklyMax()).isEqualTo(UPDATED_WEEKLY_MAX);
        assertThat(testJobTemplateRateCard.getMonthlyMin()).isEqualTo(UPDATED_MONTHLY_MIN);
        assertThat(testJobTemplateRateCard.getMonthlyMax()).isEqualTo(UPDATED_MONTHLY_MAX);
        assertThat(testJobTemplateRateCard.getYearlyMin()).isEqualTo(UPDATED_YEARLY_MIN);
        assertThat(testJobTemplateRateCard.getYearlyMax()).isEqualTo(UPDATED_YEARLY_MAX);
        assertThat(testJobTemplateRateCard.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobTemplateRateCard.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobTemplateRateCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobTemplateRateCard.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobTemplateRateCard.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobTemplateRateCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobTemplateRateCard() throws Exception {
        int databaseSizeBeforeUpdate = jobTemplateRateCardRepository.findAll().size();
        jobTemplateRateCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobTemplateRateCardMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobTemplateRateCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobTemplateRateCard in the database
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobTemplateRateCard() throws Exception {
        // Initialize the database
        jobTemplateRateCardRepository.saveAndFlush(jobTemplateRateCard);

        int databaseSizeBeforeDelete = jobTemplateRateCardRepository.findAll().size();

        // Delete the jobTemplateRateCard
        restJobTemplateRateCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobTemplateRateCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobTemplateRateCard> jobTemplateRateCardList = jobTemplateRateCardRepository.findAll();
        assertThat(jobTemplateRateCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
