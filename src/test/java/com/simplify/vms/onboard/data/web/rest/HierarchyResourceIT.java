package com.simplify.vms.onboard.data.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.vms.onboard.data.IntegrationTest;
import com.simplify.vms.onboard.data.domain.Hierarchy;
import com.simplify.vms.onboard.data.domain.enumeration.Country;
import com.simplify.vms.onboard.data.domain.enumeration.Country;
import com.simplify.vms.onboard.data.domain.enumeration.Currency;
import com.simplify.vms.onboard.data.domain.enumeration.Language;
import com.simplify.vms.onboard.data.repository.HierarchyRepository;
import com.simplify.vms.onboard.data.service.HierarchyService;
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
 * Integration tests for the {@link HierarchyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HierarchyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_FLOW = "AAAAAAAAAA";
    private static final String UPDATED_WORK_FLOW = "BBBBBBBBBB";

    private static final Language DEFAULT_PREFERRED_LANGUAGE = Language.English;
    private static final Language UPDATED_PREFERRED_LANGUAGE = Language.Spanish;

    private static final Currency DEFAULT_PREFERRED_CURRENCY = Currency.USD;
    private static final Currency UPDATED_PREFERRED_CURRENCY = Currency.INR;

    private static final Country DEFAULT_PRIMARY_COUNTRY = Country.US;
    private static final Country UPDATED_PRIMARY_COUNTRY = Country.India;

    private static final String DEFAULT_PRIMARY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_ADDRESS = "BBBBBBBBBB";

    private static final Country DEFAULT_SECONDARY_COUNTRY = Country.US;
    private static final Country UPDATED_SECONDARY_COUNTRY = Country.India;

    private static final String DEFAULT_SECONDARY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_CONTACT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_CONTACT_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CONTACT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT_PHONE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/hierarchies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Mock
    private HierarchyRepository hierarchyRepositoryMock;

    @Mock
    private HierarchyService hierarchyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHierarchyMockMvc;

    private Hierarchy hierarchy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hierarchy createEntity(EntityManager em) {
        Hierarchy hierarchy = new Hierarchy()
            .name(DEFAULT_NAME)
            .workFlow(DEFAULT_WORK_FLOW)
            .preferredLanguage(DEFAULT_PREFERRED_LANGUAGE)
            .preferredCurrency(DEFAULT_PREFERRED_CURRENCY)
            .primaryCountry(DEFAULT_PRIMARY_COUNTRY)
            .primaryAddress(DEFAULT_PRIMARY_ADDRESS)
            .secondaryCountry(DEFAULT_SECONDARY_COUNTRY)
            .secondaryAddress(DEFAULT_SECONDARY_ADDRESS)
            .primaryContactName(DEFAULT_PRIMARY_CONTACT_NAME)
            .primaryContactDesignation(DEFAULT_PRIMARY_CONTACT_DESIGNATION)
            .primaryContactEmail(DEFAULT_PRIMARY_CONTACT_EMAIL)
            .primaryContactPhone(DEFAULT_PRIMARY_CONTACT_PHONE)
            .secondaryContactName(DEFAULT_SECONDARY_CONTACT_NAME)
            .secondaryContactDesignation(DEFAULT_SECONDARY_CONTACT_DESIGNATION)
            .secondaryContactEmail(DEFAULT_SECONDARY_CONTACT_EMAIL)
            .secondaryContactPhone(DEFAULT_SECONDARY_CONTACT_PHONE)
            .fileUploadPath(DEFAULT_FILE_UPLOAD_PATH)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return hierarchy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hierarchy createUpdatedEntity(EntityManager em) {
        Hierarchy hierarchy = new Hierarchy()
            .name(UPDATED_NAME)
            .workFlow(UPDATED_WORK_FLOW)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .preferredCurrency(UPDATED_PREFERRED_CURRENCY)
            .primaryCountry(UPDATED_PRIMARY_COUNTRY)
            .primaryAddress(UPDATED_PRIMARY_ADDRESS)
            .secondaryCountry(UPDATED_SECONDARY_COUNTRY)
            .secondaryAddress(UPDATED_SECONDARY_ADDRESS)
            .primaryContactName(UPDATED_PRIMARY_CONTACT_NAME)
            .primaryContactDesignation(UPDATED_PRIMARY_CONTACT_DESIGNATION)
            .primaryContactEmail(UPDATED_PRIMARY_CONTACT_EMAIL)
            .primaryContactPhone(UPDATED_PRIMARY_CONTACT_PHONE)
            .secondaryContactName(UPDATED_SECONDARY_CONTACT_NAME)
            .secondaryContactDesignation(UPDATED_SECONDARY_CONTACT_DESIGNATION)
            .secondaryContactEmail(UPDATED_SECONDARY_CONTACT_EMAIL)
            .secondaryContactPhone(UPDATED_SECONDARY_CONTACT_PHONE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return hierarchy;
    }

    @BeforeEach
    public void initTest() {
        hierarchy = createEntity(em);
    }

    @Test
    @Transactional
    void createHierarchy() throws Exception {
        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();
        // Create the Hierarchy
        restHierarchyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isCreated());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate + 1);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHierarchy.getWorkFlow()).isEqualTo(DEFAULT_WORK_FLOW);
        assertThat(testHierarchy.getPreferredLanguage()).isEqualTo(DEFAULT_PREFERRED_LANGUAGE);
        assertThat(testHierarchy.getPreferredCurrency()).isEqualTo(DEFAULT_PREFERRED_CURRENCY);
        assertThat(testHierarchy.getPrimaryCountry()).isEqualTo(DEFAULT_PRIMARY_COUNTRY);
        assertThat(testHierarchy.getPrimaryAddress()).isEqualTo(DEFAULT_PRIMARY_ADDRESS);
        assertThat(testHierarchy.getSecondaryCountry()).isEqualTo(DEFAULT_SECONDARY_COUNTRY);
        assertThat(testHierarchy.getSecondaryAddress()).isEqualTo(DEFAULT_SECONDARY_ADDRESS);
        assertThat(testHierarchy.getPrimaryContactName()).isEqualTo(DEFAULT_PRIMARY_CONTACT_NAME);
        assertThat(testHierarchy.getPrimaryContactDesignation()).isEqualTo(DEFAULT_PRIMARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getPrimaryContactEmail()).isEqualTo(DEFAULT_PRIMARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getPrimaryContactPhone()).isEqualTo(DEFAULT_PRIMARY_CONTACT_PHONE);
        assertThat(testHierarchy.getSecondaryContactName()).isEqualTo(DEFAULT_SECONDARY_CONTACT_NAME);
        assertThat(testHierarchy.getSecondaryContactDesignation()).isEqualTo(DEFAULT_SECONDARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getSecondaryContactEmail()).isEqualTo(DEFAULT_SECONDARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getSecondaryContactPhone()).isEqualTo(DEFAULT_SECONDARY_CONTACT_PHONE);
        assertThat(testHierarchy.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testHierarchy.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testHierarchy.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHierarchy.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testHierarchy.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testHierarchy.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createHierarchyWithExistingId() throws Exception {
        // Create the Hierarchy with an existing ID
        hierarchy.setId(1L);

        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierarchyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHierarchies() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        // Get all the hierarchyList
        restHierarchyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].workFlow").value(hasItem(DEFAULT_WORK_FLOW)))
            .andExpect(jsonPath("$.[*].preferredLanguage").value(hasItem(DEFAULT_PREFERRED_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].preferredCurrency").value(hasItem(DEFAULT_PREFERRED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].primaryCountry").value(hasItem(DEFAULT_PRIMARY_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].primaryAddress").value(hasItem(DEFAULT_PRIMARY_ADDRESS)))
            .andExpect(jsonPath("$.[*].secondaryCountry").value(hasItem(DEFAULT_SECONDARY_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].secondaryAddress").value(hasItem(DEFAULT_SECONDARY_ADDRESS)))
            .andExpect(jsonPath("$.[*].primaryContactName").value(hasItem(DEFAULT_PRIMARY_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].primaryContactDesignation").value(hasItem(DEFAULT_PRIMARY_CONTACT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].primaryContactEmail").value(hasItem(DEFAULT_PRIMARY_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].primaryContactPhone").value(hasItem(DEFAULT_PRIMARY_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].secondaryContactName").value(hasItem(DEFAULT_SECONDARY_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].secondaryContactDesignation").value(hasItem(DEFAULT_SECONDARY_CONTACT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].secondaryContactEmail").value(hasItem(DEFAULT_SECONDARY_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].secondaryContactPhone").value(hasItem(DEFAULT_SECONDARY_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].fileUploadPath").value(hasItem(DEFAULT_FILE_UPLOAD_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHierarchiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(hierarchyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHierarchyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hierarchyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHierarchiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(hierarchyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHierarchyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hierarchyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        // Get the hierarchy
        restHierarchyMockMvc
            .perform(get(ENTITY_API_URL_ID, hierarchy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hierarchy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.workFlow").value(DEFAULT_WORK_FLOW))
            .andExpect(jsonPath("$.preferredLanguage").value(DEFAULT_PREFERRED_LANGUAGE.toString()))
            .andExpect(jsonPath("$.preferredCurrency").value(DEFAULT_PREFERRED_CURRENCY.toString()))
            .andExpect(jsonPath("$.primaryCountry").value(DEFAULT_PRIMARY_COUNTRY.toString()))
            .andExpect(jsonPath("$.primaryAddress").value(DEFAULT_PRIMARY_ADDRESS))
            .andExpect(jsonPath("$.secondaryCountry").value(DEFAULT_SECONDARY_COUNTRY.toString()))
            .andExpect(jsonPath("$.secondaryAddress").value(DEFAULT_SECONDARY_ADDRESS))
            .andExpect(jsonPath("$.primaryContactName").value(DEFAULT_PRIMARY_CONTACT_NAME))
            .andExpect(jsonPath("$.primaryContactDesignation").value(DEFAULT_PRIMARY_CONTACT_DESIGNATION))
            .andExpect(jsonPath("$.primaryContactEmail").value(DEFAULT_PRIMARY_CONTACT_EMAIL))
            .andExpect(jsonPath("$.primaryContactPhone").value(DEFAULT_PRIMARY_CONTACT_PHONE))
            .andExpect(jsonPath("$.secondaryContactName").value(DEFAULT_SECONDARY_CONTACT_NAME))
            .andExpect(jsonPath("$.secondaryContactDesignation").value(DEFAULT_SECONDARY_CONTACT_DESIGNATION))
            .andExpect(jsonPath("$.secondaryContactEmail").value(DEFAULT_SECONDARY_CONTACT_EMAIL))
            .andExpect(jsonPath("$.secondaryContactPhone").value(DEFAULT_SECONDARY_CONTACT_PHONE))
            .andExpect(jsonPath("$.fileUploadPath").value(DEFAULT_FILE_UPLOAD_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHierarchy() throws Exception {
        // Get the hierarchy
        restHierarchyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Update the hierarchy
        Hierarchy updatedHierarchy = hierarchyRepository.findById(hierarchy.getId()).get();
        // Disconnect from session so that the updates on updatedHierarchy are not directly saved in db
        em.detach(updatedHierarchy);
        updatedHierarchy
            .name(UPDATED_NAME)
            .workFlow(UPDATED_WORK_FLOW)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .preferredCurrency(UPDATED_PREFERRED_CURRENCY)
            .primaryCountry(UPDATED_PRIMARY_COUNTRY)
            .primaryAddress(UPDATED_PRIMARY_ADDRESS)
            .secondaryCountry(UPDATED_SECONDARY_COUNTRY)
            .secondaryAddress(UPDATED_SECONDARY_ADDRESS)
            .primaryContactName(UPDATED_PRIMARY_CONTACT_NAME)
            .primaryContactDesignation(UPDATED_PRIMARY_CONTACT_DESIGNATION)
            .primaryContactEmail(UPDATED_PRIMARY_CONTACT_EMAIL)
            .primaryContactPhone(UPDATED_PRIMARY_CONTACT_PHONE)
            .secondaryContactName(UPDATED_SECONDARY_CONTACT_NAME)
            .secondaryContactDesignation(UPDATED_SECONDARY_CONTACT_DESIGNATION)
            .secondaryContactEmail(UPDATED_SECONDARY_CONTACT_EMAIL)
            .secondaryContactPhone(UPDATED_SECONDARY_CONTACT_PHONE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restHierarchyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHierarchy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHierarchy))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHierarchy.getWorkFlow()).isEqualTo(UPDATED_WORK_FLOW);
        assertThat(testHierarchy.getPreferredLanguage()).isEqualTo(UPDATED_PREFERRED_LANGUAGE);
        assertThat(testHierarchy.getPreferredCurrency()).isEqualTo(UPDATED_PREFERRED_CURRENCY);
        assertThat(testHierarchy.getPrimaryCountry()).isEqualTo(UPDATED_PRIMARY_COUNTRY);
        assertThat(testHierarchy.getPrimaryAddress()).isEqualTo(UPDATED_PRIMARY_ADDRESS);
        assertThat(testHierarchy.getSecondaryCountry()).isEqualTo(UPDATED_SECONDARY_COUNTRY);
        assertThat(testHierarchy.getSecondaryAddress()).isEqualTo(UPDATED_SECONDARY_ADDRESS);
        assertThat(testHierarchy.getPrimaryContactName()).isEqualTo(UPDATED_PRIMARY_CONTACT_NAME);
        assertThat(testHierarchy.getPrimaryContactDesignation()).isEqualTo(UPDATED_PRIMARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getPrimaryContactEmail()).isEqualTo(UPDATED_PRIMARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getPrimaryContactPhone()).isEqualTo(UPDATED_PRIMARY_CONTACT_PHONE);
        assertThat(testHierarchy.getSecondaryContactName()).isEqualTo(UPDATED_SECONDARY_CONTACT_NAME);
        assertThat(testHierarchy.getSecondaryContactDesignation()).isEqualTo(UPDATED_SECONDARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getSecondaryContactEmail()).isEqualTo(UPDATED_SECONDARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getSecondaryContactPhone()).isEqualTo(UPDATED_SECONDARY_CONTACT_PHONE);
        assertThat(testHierarchy.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testHierarchy.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHierarchy.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHierarchy.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHierarchy.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHierarchy.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hierarchy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hierarchy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hierarchy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHierarchyWithPatch() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Update the hierarchy using partial update
        Hierarchy partialUpdatedHierarchy = new Hierarchy();
        partialUpdatedHierarchy.setId(hierarchy.getId());

        partialUpdatedHierarchy
            .name(UPDATED_NAME)
            .workFlow(UPDATED_WORK_FLOW)
            .preferredCurrency(UPDATED_PREFERRED_CURRENCY)
            .primaryAddress(UPDATED_PRIMARY_ADDRESS)
            .secondaryContactName(UPDATED_SECONDARY_CONTACT_NAME)
            .secondaryContactDesignation(UPDATED_SECONDARY_CONTACT_DESIGNATION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restHierarchyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHierarchy))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHierarchy.getWorkFlow()).isEqualTo(UPDATED_WORK_FLOW);
        assertThat(testHierarchy.getPreferredLanguage()).isEqualTo(DEFAULT_PREFERRED_LANGUAGE);
        assertThat(testHierarchy.getPreferredCurrency()).isEqualTo(UPDATED_PREFERRED_CURRENCY);
        assertThat(testHierarchy.getPrimaryCountry()).isEqualTo(DEFAULT_PRIMARY_COUNTRY);
        assertThat(testHierarchy.getPrimaryAddress()).isEqualTo(UPDATED_PRIMARY_ADDRESS);
        assertThat(testHierarchy.getSecondaryCountry()).isEqualTo(DEFAULT_SECONDARY_COUNTRY);
        assertThat(testHierarchy.getSecondaryAddress()).isEqualTo(DEFAULT_SECONDARY_ADDRESS);
        assertThat(testHierarchy.getPrimaryContactName()).isEqualTo(DEFAULT_PRIMARY_CONTACT_NAME);
        assertThat(testHierarchy.getPrimaryContactDesignation()).isEqualTo(DEFAULT_PRIMARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getPrimaryContactEmail()).isEqualTo(DEFAULT_PRIMARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getPrimaryContactPhone()).isEqualTo(DEFAULT_PRIMARY_CONTACT_PHONE);
        assertThat(testHierarchy.getSecondaryContactName()).isEqualTo(UPDATED_SECONDARY_CONTACT_NAME);
        assertThat(testHierarchy.getSecondaryContactDesignation()).isEqualTo(UPDATED_SECONDARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getSecondaryContactEmail()).isEqualTo(DEFAULT_SECONDARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getSecondaryContactPhone()).isEqualTo(DEFAULT_SECONDARY_CONTACT_PHONE);
        assertThat(testHierarchy.getFileUploadPath()).isEqualTo(DEFAULT_FILE_UPLOAD_PATH);
        assertThat(testHierarchy.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHierarchy.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHierarchy.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHierarchy.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHierarchy.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateHierarchyWithPatch() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Update the hierarchy using partial update
        Hierarchy partialUpdatedHierarchy = new Hierarchy();
        partialUpdatedHierarchy.setId(hierarchy.getId());

        partialUpdatedHierarchy
            .name(UPDATED_NAME)
            .workFlow(UPDATED_WORK_FLOW)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .preferredCurrency(UPDATED_PREFERRED_CURRENCY)
            .primaryCountry(UPDATED_PRIMARY_COUNTRY)
            .primaryAddress(UPDATED_PRIMARY_ADDRESS)
            .secondaryCountry(UPDATED_SECONDARY_COUNTRY)
            .secondaryAddress(UPDATED_SECONDARY_ADDRESS)
            .primaryContactName(UPDATED_PRIMARY_CONTACT_NAME)
            .primaryContactDesignation(UPDATED_PRIMARY_CONTACT_DESIGNATION)
            .primaryContactEmail(UPDATED_PRIMARY_CONTACT_EMAIL)
            .primaryContactPhone(UPDATED_PRIMARY_CONTACT_PHONE)
            .secondaryContactName(UPDATED_SECONDARY_CONTACT_NAME)
            .secondaryContactDesignation(UPDATED_SECONDARY_CONTACT_DESIGNATION)
            .secondaryContactEmail(UPDATED_SECONDARY_CONTACT_EMAIL)
            .secondaryContactPhone(UPDATED_SECONDARY_CONTACT_PHONE)
            .fileUploadPath(UPDATED_FILE_UPLOAD_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restHierarchyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHierarchy))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHierarchy.getWorkFlow()).isEqualTo(UPDATED_WORK_FLOW);
        assertThat(testHierarchy.getPreferredLanguage()).isEqualTo(UPDATED_PREFERRED_LANGUAGE);
        assertThat(testHierarchy.getPreferredCurrency()).isEqualTo(UPDATED_PREFERRED_CURRENCY);
        assertThat(testHierarchy.getPrimaryCountry()).isEqualTo(UPDATED_PRIMARY_COUNTRY);
        assertThat(testHierarchy.getPrimaryAddress()).isEqualTo(UPDATED_PRIMARY_ADDRESS);
        assertThat(testHierarchy.getSecondaryCountry()).isEqualTo(UPDATED_SECONDARY_COUNTRY);
        assertThat(testHierarchy.getSecondaryAddress()).isEqualTo(UPDATED_SECONDARY_ADDRESS);
        assertThat(testHierarchy.getPrimaryContactName()).isEqualTo(UPDATED_PRIMARY_CONTACT_NAME);
        assertThat(testHierarchy.getPrimaryContactDesignation()).isEqualTo(UPDATED_PRIMARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getPrimaryContactEmail()).isEqualTo(UPDATED_PRIMARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getPrimaryContactPhone()).isEqualTo(UPDATED_PRIMARY_CONTACT_PHONE);
        assertThat(testHierarchy.getSecondaryContactName()).isEqualTo(UPDATED_SECONDARY_CONTACT_NAME);
        assertThat(testHierarchy.getSecondaryContactDesignation()).isEqualTo(UPDATED_SECONDARY_CONTACT_DESIGNATION);
        assertThat(testHierarchy.getSecondaryContactEmail()).isEqualTo(UPDATED_SECONDARY_CONTACT_EMAIL);
        assertThat(testHierarchy.getSecondaryContactPhone()).isEqualTo(UPDATED_SECONDARY_CONTACT_PHONE);
        assertThat(testHierarchy.getFileUploadPath()).isEqualTo(UPDATED_FILE_UPLOAD_PATH);
        assertThat(testHierarchy.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testHierarchy.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHierarchy.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHierarchy.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHierarchy.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hierarchy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hierarchy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hierarchy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();
        hierarchy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hierarchy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        int databaseSizeBeforeDelete = hierarchyRepository.findAll().size();

        // Delete the hierarchy
        restHierarchyMockMvc
            .perform(delete(ENTITY_API_URL_ID, hierarchy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
