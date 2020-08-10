package com.isoft.mockup.web.rest;

import com.isoft.mockup.AmanMockupMsApp;
import com.isoft.mockup.config.SecurityBeanOverrideConfiguration;
import com.isoft.mockup.domain.DlsRequests;
import com.isoft.mockup.repository.DlsRequestsRepository;
import com.isoft.mockup.service.DlsRequestsService;
import com.isoft.mockup.service.dto.DlsRequestsDTO;
import com.isoft.mockup.service.mapper.DlsRequestsMapper;
import com.isoft.mockup.web.rest.errors.ExceptionTranslator;
import com.isoft.mockup.service.dto.DlsRequestsCriteria;
import com.isoft.mockup.service.DlsRequestsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.isoft.mockup.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DlsRequestsResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanMockupMsApp.class})
public class DlsRequestsResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_NO = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXPORTED = false;
    private static final Boolean UPDATED_EXPORTED = true;

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_ISSUE_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_ISSUE_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TRAFFIC_UNIT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRAFFIC_UNIT_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_LICENCE_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LICENCE_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LICENCE_EXPIRY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_LICENCE_TYPE_AR = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_TYPE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_TYPE_EN = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_TYPE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_STATUS_AR = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_STATUS_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_STATUS_EN = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_STATUS_EN = "BBBBBBBBBB";

    private static final Integer DEFAULT_LICENCE_STATUS = 1;
    private static final Integer UPDATED_LICENCE_STATUS = 2;
    private static final Integer SMALLER_LICENCE_STATUS = 1 - 1;

    private static final Long DEFAULT_APPLICANT_ID = 1L;
    private static final Long UPDATED_APPLICANT_ID = 2L;
    private static final Long SMALLER_APPLICANT_ID = 1L - 1L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final Long DEFAULT_CENTER_ID = 1L;
    private static final Long UPDATED_CENTER_ID = 2L;
    private static final Long SMALLER_CENTER_ID = 1L - 1L;

    @Autowired
    private DlsRequestsRepository dlsRequestsRepository;

    @Autowired
    private DlsRequestsMapper dlsRequestsMapper;

    @Autowired
    private DlsRequestsService dlsRequestsService;

    @Autowired
    private DlsRequestsQueryService dlsRequestsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDlsRequestsMockMvc;

    private DlsRequests dlsRequests;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DlsRequestsResource dlsRequestsResource = new DlsRequestsResource(dlsRequestsService, dlsRequestsQueryService);
        this.restDlsRequestsMockMvc = MockMvcBuilders.standaloneSetup(dlsRequestsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DlsRequests createEntity(EntityManager em) {
        DlsRequests dlsRequests = new DlsRequests()
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .licenseCategory(DEFAULT_LICENSE_CATEGORY)
            .requestNo(DEFAULT_REQUEST_NO)
            .exported(DEFAULT_EXPORTED)
            .familyName(DEFAULT_FAMILY_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .nationalId(DEFAULT_NATIONAL_ID)
            .passportIssueCountry(DEFAULT_PASSPORT_ISSUE_COUNTRY)
            .passportKey(DEFAULT_PASSPORT_KEY)
            .trafficUnitCode(DEFAULT_TRAFFIC_UNIT_CODE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .licenceExpiryDate(DEFAULT_LICENCE_EXPIRY_DATE)
            .licenceTypeAr(DEFAULT_LICENCE_TYPE_AR)
            .licenceTypeEn(DEFAULT_LICENCE_TYPE_EN)
            .licenceStatusAr(DEFAULT_LICENCE_STATUS_AR)
            .licenceStatusEn(DEFAULT_LICENCE_STATUS_EN)
            .licenceStatus(DEFAULT_LICENCE_STATUS)
            .applicantId(DEFAULT_APPLICANT_ID)
            .userId(DEFAULT_USER_ID)
            .centerId(DEFAULT_CENTER_ID);
        return dlsRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DlsRequests createUpdatedEntity(EntityManager em) {
        DlsRequests dlsRequests = new DlsRequests()
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .licenseCategory(UPDATED_LICENSE_CATEGORY)
            .requestNo(UPDATED_REQUEST_NO)
            .exported(UPDATED_EXPORTED)
            .familyName(UPDATED_FAMILY_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .fullName(UPDATED_FULL_NAME)
            .nationalId(UPDATED_NATIONAL_ID)
            .passportIssueCountry(UPDATED_PASSPORT_ISSUE_COUNTRY)
            .passportKey(UPDATED_PASSPORT_KEY)
            .trafficUnitCode(UPDATED_TRAFFIC_UNIT_CODE)
            .birthDate(UPDATED_BIRTH_DATE)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .licenceTypeAr(UPDATED_LICENCE_TYPE_AR)
            .licenceTypeEn(UPDATED_LICENCE_TYPE_EN)
            .licenceStatusAr(UPDATED_LICENCE_STATUS_AR)
            .licenceStatusEn(UPDATED_LICENCE_STATUS_EN)
            .licenceStatus(UPDATED_LICENCE_STATUS)
            .applicantId(UPDATED_APPLICANT_ID)
            .userId(UPDATED_USER_ID)
            .centerId(UPDATED_CENTER_ID);
        return dlsRequests;
    }

    @BeforeEach
    public void initTest() {
        dlsRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createDlsRequests() throws Exception {
        int databaseSizeBeforeCreate = dlsRequestsRepository.findAll().size();

        // Create the DlsRequests
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);
        restDlsRequestsMockMvc.perform(post("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the DlsRequests in the database
        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        DlsRequests testDlsRequests = dlsRequestsList.get(dlsRequestsList.size() - 1);
        assertThat(testDlsRequests.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testDlsRequests.getLicenseCategory()).isEqualTo(DEFAULT_LICENSE_CATEGORY);
        assertThat(testDlsRequests.getRequestNo()).isEqualTo(DEFAULT_REQUEST_NO);
        assertThat(testDlsRequests.isExported()).isEqualTo(DEFAULT_EXPORTED);
        assertThat(testDlsRequests.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testDlsRequests.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDlsRequests.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDlsRequests.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testDlsRequests.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDlsRequests.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testDlsRequests.getPassportIssueCountry()).isEqualTo(DEFAULT_PASSPORT_ISSUE_COUNTRY);
        assertThat(testDlsRequests.getPassportKey()).isEqualTo(DEFAULT_PASSPORT_KEY);
        assertThat(testDlsRequests.getTrafficUnitCode()).isEqualTo(DEFAULT_TRAFFIC_UNIT_CODE);
        assertThat(testDlsRequests.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testDlsRequests.getLicenceExpiryDate()).isEqualTo(DEFAULT_LICENCE_EXPIRY_DATE);
        assertThat(testDlsRequests.getLicenceTypeAr()).isEqualTo(DEFAULT_LICENCE_TYPE_AR);
        assertThat(testDlsRequests.getLicenceTypeEn()).isEqualTo(DEFAULT_LICENCE_TYPE_EN);
        assertThat(testDlsRequests.getLicenceStatusAr()).isEqualTo(DEFAULT_LICENCE_STATUS_AR);
        assertThat(testDlsRequests.getLicenceStatusEn()).isEqualTo(DEFAULT_LICENCE_STATUS_EN);
        assertThat(testDlsRequests.getLicenceStatus()).isEqualTo(DEFAULT_LICENCE_STATUS);
        assertThat(testDlsRequests.getApplicantId()).isEqualTo(DEFAULT_APPLICANT_ID);
        assertThat(testDlsRequests.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDlsRequests.getCenterId()).isEqualTo(DEFAULT_CENTER_ID);
    }

    @Test
    @Transactional
    public void createDlsRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dlsRequestsRepository.findAll().size();

        // Create the DlsRequests with an existing ID
        dlsRequests.setId(1L);
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDlsRequestsMockMvc.perform(post("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DlsRequests in the database
        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlsRequestsRepository.findAll().size();
        // set the field null
        dlsRequests.setTransactionType(null);

        // Create the DlsRequests, which fails.
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);

        restDlsRequestsMockMvc.perform(post("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlsRequestsRepository.findAll().size();
        // set the field null
        dlsRequests.setLicenseCategory(null);

        // Create the DlsRequests, which fails.
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);

        restDlsRequestsMockMvc.perform(post("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlsRequestsRepository.findAll().size();
        // set the field null
        dlsRequests.setRequestNo(null);

        // Create the DlsRequests, which fails.
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);

        restDlsRequestsMockMvc.perform(post("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlsRequests() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList
        restDlsRequestsMockMvc.perform(get("/api/dls-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dlsRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].licenseCategory").value(hasItem(DEFAULT_LICENSE_CATEGORY)))
            .andExpect(jsonPath("$.[*].requestNo").value(hasItem(DEFAULT_REQUEST_NO)))
            .andExpect(jsonPath("$.[*].exported").value(hasItem(DEFAULT_EXPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].passportIssueCountry").value(hasItem(DEFAULT_PASSPORT_ISSUE_COUNTRY)))
            .andExpect(jsonPath("$.[*].passportKey").value(hasItem(DEFAULT_PASSPORT_KEY)))
            .andExpect(jsonPath("$.[*].trafficUnitCode").value(hasItem(DEFAULT_TRAFFIC_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].licenceExpiryDate").value(hasItem(DEFAULT_LICENCE_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].licenceTypeAr").value(hasItem(DEFAULT_LICENCE_TYPE_AR)))
            .andExpect(jsonPath("$.[*].licenceTypeEn").value(hasItem(DEFAULT_LICENCE_TYPE_EN)))
            .andExpect(jsonPath("$.[*].licenceStatusAr").value(hasItem(DEFAULT_LICENCE_STATUS_AR)))
            .andExpect(jsonPath("$.[*].licenceStatusEn").value(hasItem(DEFAULT_LICENCE_STATUS_EN)))
            .andExpect(jsonPath("$.[*].licenceStatus").value(hasItem(DEFAULT_LICENCE_STATUS)))
            .andExpect(jsonPath("$.[*].applicantId").value(hasItem(DEFAULT_APPLICANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getDlsRequests() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get the dlsRequests
        restDlsRequestsMockMvc.perform(get("/api/dls-requests/{id}", dlsRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dlsRequests.getId().intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.licenseCategory").value(DEFAULT_LICENSE_CATEGORY))
            .andExpect(jsonPath("$.requestNo").value(DEFAULT_REQUEST_NO))
            .andExpect(jsonPath("$.exported").value(DEFAULT_EXPORTED.booleanValue()))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID))
            .andExpect(jsonPath("$.passportIssueCountry").value(DEFAULT_PASSPORT_ISSUE_COUNTRY))
            .andExpect(jsonPath("$.passportKey").value(DEFAULT_PASSPORT_KEY))
            .andExpect(jsonPath("$.trafficUnitCode").value(DEFAULT_TRAFFIC_UNIT_CODE))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.licenceExpiryDate").value(DEFAULT_LICENCE_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.licenceTypeAr").value(DEFAULT_LICENCE_TYPE_AR))
            .andExpect(jsonPath("$.licenceTypeEn").value(DEFAULT_LICENCE_TYPE_EN))
            .andExpect(jsonPath("$.licenceStatusAr").value(DEFAULT_LICENCE_STATUS_AR))
            .andExpect(jsonPath("$.licenceStatusEn").value(DEFAULT_LICENCE_STATUS_EN))
            .andExpect(jsonPath("$.licenceStatus").value(DEFAULT_LICENCE_STATUS))
            .andExpect(jsonPath("$.applicantId").value(DEFAULT_APPLICANT_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.centerId").value(DEFAULT_CENTER_ID.intValue()));
    }


    @Test
    @Transactional
    public void getDlsRequestsByIdFiltering() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        Long id = dlsRequests.getId();

        defaultDlsRequestsShouldBeFound("id.equals=" + id);
        defaultDlsRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultDlsRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDlsRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultDlsRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDlsRequestsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultDlsRequestsShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the dlsRequestsList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultDlsRequestsShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the dlsRequestsList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the dlsRequestsList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType is not null
        defaultDlsRequestsShouldBeFound("transactionType.specified=true");

        // Get all the dlsRequestsList where transactionType is null
        defaultDlsRequestsShouldNotBeFound("transactionType.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType contains DEFAULT_TRANSACTION_TYPE
        defaultDlsRequestsShouldBeFound("transactionType.contains=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the dlsRequestsList where transactionType contains UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldNotBeFound("transactionType.contains=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTransactionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where transactionType does not contain DEFAULT_TRANSACTION_TYPE
        defaultDlsRequestsShouldNotBeFound("transactionType.doesNotContain=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the dlsRequestsList where transactionType does not contain UPDATED_TRANSACTION_TYPE
        defaultDlsRequestsShouldBeFound("transactionType.doesNotContain=" + UPDATED_TRANSACTION_TYPE);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory equals to DEFAULT_LICENSE_CATEGORY
        defaultDlsRequestsShouldBeFound("licenseCategory.equals=" + DEFAULT_LICENSE_CATEGORY);

        // Get all the dlsRequestsList where licenseCategory equals to UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldNotBeFound("licenseCategory.equals=" + UPDATED_LICENSE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory not equals to DEFAULT_LICENSE_CATEGORY
        defaultDlsRequestsShouldNotBeFound("licenseCategory.notEquals=" + DEFAULT_LICENSE_CATEGORY);

        // Get all the dlsRequestsList where licenseCategory not equals to UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldBeFound("licenseCategory.notEquals=" + UPDATED_LICENSE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory in DEFAULT_LICENSE_CATEGORY or UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldBeFound("licenseCategory.in=" + DEFAULT_LICENSE_CATEGORY + "," + UPDATED_LICENSE_CATEGORY);

        // Get all the dlsRequestsList where licenseCategory equals to UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldNotBeFound("licenseCategory.in=" + UPDATED_LICENSE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory is not null
        defaultDlsRequestsShouldBeFound("licenseCategory.specified=true");

        // Get all the dlsRequestsList where licenseCategory is null
        defaultDlsRequestsShouldNotBeFound("licenseCategory.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory contains DEFAULT_LICENSE_CATEGORY
        defaultDlsRequestsShouldBeFound("licenseCategory.contains=" + DEFAULT_LICENSE_CATEGORY);

        // Get all the dlsRequestsList where licenseCategory contains UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldNotBeFound("licenseCategory.contains=" + UPDATED_LICENSE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseCategory does not contain DEFAULT_LICENSE_CATEGORY
        defaultDlsRequestsShouldNotBeFound("licenseCategory.doesNotContain=" + DEFAULT_LICENSE_CATEGORY);

        // Get all the dlsRequestsList where licenseCategory does not contain UPDATED_LICENSE_CATEGORY
        defaultDlsRequestsShouldBeFound("licenseCategory.doesNotContain=" + UPDATED_LICENSE_CATEGORY);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo equals to DEFAULT_REQUEST_NO
        defaultDlsRequestsShouldBeFound("requestNo.equals=" + DEFAULT_REQUEST_NO);

        // Get all the dlsRequestsList where requestNo equals to UPDATED_REQUEST_NO
        defaultDlsRequestsShouldNotBeFound("requestNo.equals=" + UPDATED_REQUEST_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo not equals to DEFAULT_REQUEST_NO
        defaultDlsRequestsShouldNotBeFound("requestNo.notEquals=" + DEFAULT_REQUEST_NO);

        // Get all the dlsRequestsList where requestNo not equals to UPDATED_REQUEST_NO
        defaultDlsRequestsShouldBeFound("requestNo.notEquals=" + UPDATED_REQUEST_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo in DEFAULT_REQUEST_NO or UPDATED_REQUEST_NO
        defaultDlsRequestsShouldBeFound("requestNo.in=" + DEFAULT_REQUEST_NO + "," + UPDATED_REQUEST_NO);

        // Get all the dlsRequestsList where requestNo equals to UPDATED_REQUEST_NO
        defaultDlsRequestsShouldNotBeFound("requestNo.in=" + UPDATED_REQUEST_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo is not null
        defaultDlsRequestsShouldBeFound("requestNo.specified=true");

        // Get all the dlsRequestsList where requestNo is null
        defaultDlsRequestsShouldNotBeFound("requestNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo contains DEFAULT_REQUEST_NO
        defaultDlsRequestsShouldBeFound("requestNo.contains=" + DEFAULT_REQUEST_NO);

        // Get all the dlsRequestsList where requestNo contains UPDATED_REQUEST_NO
        defaultDlsRequestsShouldNotBeFound("requestNo.contains=" + UPDATED_REQUEST_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestNoNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestNo does not contain DEFAULT_REQUEST_NO
        defaultDlsRequestsShouldNotBeFound("requestNo.doesNotContain=" + DEFAULT_REQUEST_NO);

        // Get all the dlsRequestsList where requestNo does not contain UPDATED_REQUEST_NO
        defaultDlsRequestsShouldBeFound("requestNo.doesNotContain=" + UPDATED_REQUEST_NO);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByExportedIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where exported equals to DEFAULT_EXPORTED
        defaultDlsRequestsShouldBeFound("exported.equals=" + DEFAULT_EXPORTED);

        // Get all the dlsRequestsList where exported equals to UPDATED_EXPORTED
        defaultDlsRequestsShouldNotBeFound("exported.equals=" + UPDATED_EXPORTED);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByExportedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where exported not equals to DEFAULT_EXPORTED
        defaultDlsRequestsShouldNotBeFound("exported.notEquals=" + DEFAULT_EXPORTED);

        // Get all the dlsRequestsList where exported not equals to UPDATED_EXPORTED
        defaultDlsRequestsShouldBeFound("exported.notEquals=" + UPDATED_EXPORTED);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByExportedIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where exported in DEFAULT_EXPORTED or UPDATED_EXPORTED
        defaultDlsRequestsShouldBeFound("exported.in=" + DEFAULT_EXPORTED + "," + UPDATED_EXPORTED);

        // Get all the dlsRequestsList where exported equals to UPDATED_EXPORTED
        defaultDlsRequestsShouldNotBeFound("exported.in=" + UPDATED_EXPORTED);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByExportedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where exported is not null
        defaultDlsRequestsShouldBeFound("exported.specified=true");

        // Get all the dlsRequestsList where exported is null
        defaultDlsRequestsShouldNotBeFound("exported.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName equals to DEFAULT_FAMILY_NAME
        defaultDlsRequestsShouldBeFound("familyName.equals=" + DEFAULT_FAMILY_NAME);

        // Get all the dlsRequestsList where familyName equals to UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldNotBeFound("familyName.equals=" + UPDATED_FAMILY_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName not equals to DEFAULT_FAMILY_NAME
        defaultDlsRequestsShouldNotBeFound("familyName.notEquals=" + DEFAULT_FAMILY_NAME);

        // Get all the dlsRequestsList where familyName not equals to UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldBeFound("familyName.notEquals=" + UPDATED_FAMILY_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName in DEFAULT_FAMILY_NAME or UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldBeFound("familyName.in=" + DEFAULT_FAMILY_NAME + "," + UPDATED_FAMILY_NAME);

        // Get all the dlsRequestsList where familyName equals to UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldNotBeFound("familyName.in=" + UPDATED_FAMILY_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName is not null
        defaultDlsRequestsShouldBeFound("familyName.specified=true");

        // Get all the dlsRequestsList where familyName is null
        defaultDlsRequestsShouldNotBeFound("familyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName contains DEFAULT_FAMILY_NAME
        defaultDlsRequestsShouldBeFound("familyName.contains=" + DEFAULT_FAMILY_NAME);

        // Get all the dlsRequestsList where familyName contains UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldNotBeFound("familyName.contains=" + UPDATED_FAMILY_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFamilyNameNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where familyName does not contain DEFAULT_FAMILY_NAME
        defaultDlsRequestsShouldNotBeFound("familyName.doesNotContain=" + DEFAULT_FAMILY_NAME);

        // Get all the dlsRequestsList where familyName does not contain UPDATED_FAMILY_NAME
        defaultDlsRequestsShouldBeFound("familyName.doesNotContain=" + UPDATED_FAMILY_NAME);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName equals to DEFAULT_FIRST_NAME
        defaultDlsRequestsShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the dlsRequestsList where firstName equals to UPDATED_FIRST_NAME
        defaultDlsRequestsShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName not equals to DEFAULT_FIRST_NAME
        defaultDlsRequestsShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the dlsRequestsList where firstName not equals to UPDATED_FIRST_NAME
        defaultDlsRequestsShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultDlsRequestsShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the dlsRequestsList where firstName equals to UPDATED_FIRST_NAME
        defaultDlsRequestsShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName is not null
        defaultDlsRequestsShouldBeFound("firstName.specified=true");

        // Get all the dlsRequestsList where firstName is null
        defaultDlsRequestsShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName contains DEFAULT_FIRST_NAME
        defaultDlsRequestsShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the dlsRequestsList where firstName contains UPDATED_FIRST_NAME
        defaultDlsRequestsShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where firstName does not contain DEFAULT_FIRST_NAME
        defaultDlsRequestsShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the dlsRequestsList where firstName does not contain UPDATED_FIRST_NAME
        defaultDlsRequestsShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName equals to DEFAULT_LAST_NAME
        defaultDlsRequestsShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the dlsRequestsList where lastName equals to UPDATED_LAST_NAME
        defaultDlsRequestsShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName not equals to DEFAULT_LAST_NAME
        defaultDlsRequestsShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the dlsRequestsList where lastName not equals to UPDATED_LAST_NAME
        defaultDlsRequestsShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultDlsRequestsShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the dlsRequestsList where lastName equals to UPDATED_LAST_NAME
        defaultDlsRequestsShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName is not null
        defaultDlsRequestsShouldBeFound("lastName.specified=true");

        // Get all the dlsRequestsList where lastName is null
        defaultDlsRequestsShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName contains DEFAULT_LAST_NAME
        defaultDlsRequestsShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the dlsRequestsList where lastName contains UPDATED_LAST_NAME
        defaultDlsRequestsShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where lastName does not contain DEFAULT_LAST_NAME
        defaultDlsRequestsShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the dlsRequestsList where lastName does not contain UPDATED_LAST_NAME
        defaultDlsRequestsShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultDlsRequestsShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the dlsRequestsList where middleName equals to UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultDlsRequestsShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the dlsRequestsList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the dlsRequestsList where middleName equals to UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName is not null
        defaultDlsRequestsShouldBeFound("middleName.specified=true");

        // Get all the dlsRequestsList where middleName is null
        defaultDlsRequestsShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName contains DEFAULT_MIDDLE_NAME
        defaultDlsRequestsShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the dlsRequestsList where middleName contains UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultDlsRequestsShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the dlsRequestsList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultDlsRequestsShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName equals to DEFAULT_FULL_NAME
        defaultDlsRequestsShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the dlsRequestsList where fullName equals to UPDATED_FULL_NAME
        defaultDlsRequestsShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName not equals to DEFAULT_FULL_NAME
        defaultDlsRequestsShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the dlsRequestsList where fullName not equals to UPDATED_FULL_NAME
        defaultDlsRequestsShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultDlsRequestsShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the dlsRequestsList where fullName equals to UPDATED_FULL_NAME
        defaultDlsRequestsShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName is not null
        defaultDlsRequestsShouldBeFound("fullName.specified=true");

        // Get all the dlsRequestsList where fullName is null
        defaultDlsRequestsShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName contains DEFAULT_FULL_NAME
        defaultDlsRequestsShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the dlsRequestsList where fullName contains UPDATED_FULL_NAME
        defaultDlsRequestsShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where fullName does not contain DEFAULT_FULL_NAME
        defaultDlsRequestsShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the dlsRequestsList where fullName does not contain UPDATED_FULL_NAME
        defaultDlsRequestsShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId equals to DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalId.equals=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalId equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalId.equals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId not equals to DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalId.notEquals=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalId not equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalId.notEquals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId in DEFAULT_NATIONAL_ID or UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalId.in=" + DEFAULT_NATIONAL_ID + "," + UPDATED_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalId equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalId.in=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId is not null
        defaultDlsRequestsShouldBeFound("nationalId.specified=true");

        // Get all the dlsRequestsList where nationalId is null
        defaultDlsRequestsShouldNotBeFound("nationalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId contains DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalId.contains=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalId contains UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalId.contains=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIdNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalId does not contain DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalId.doesNotContain=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalId does not contain UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalId.doesNotContain=" + UPDATED_NATIONAL_ID);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry equals to DEFAULT_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldBeFound("passportIssueCountry.equals=" + DEFAULT_PASSPORT_ISSUE_COUNTRY);

        // Get all the dlsRequestsList where passportIssueCountry equals to UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.equals=" + UPDATED_PASSPORT_ISSUE_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry not equals to DEFAULT_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.notEquals=" + DEFAULT_PASSPORT_ISSUE_COUNTRY);

        // Get all the dlsRequestsList where passportIssueCountry not equals to UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldBeFound("passportIssueCountry.notEquals=" + UPDATED_PASSPORT_ISSUE_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry in DEFAULT_PASSPORT_ISSUE_COUNTRY or UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldBeFound("passportIssueCountry.in=" + DEFAULT_PASSPORT_ISSUE_COUNTRY + "," + UPDATED_PASSPORT_ISSUE_COUNTRY);

        // Get all the dlsRequestsList where passportIssueCountry equals to UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.in=" + UPDATED_PASSPORT_ISSUE_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry is not null
        defaultDlsRequestsShouldBeFound("passportIssueCountry.specified=true");

        // Get all the dlsRequestsList where passportIssueCountry is null
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry contains DEFAULT_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldBeFound("passportIssueCountry.contains=" + DEFAULT_PASSPORT_ISSUE_COUNTRY);

        // Get all the dlsRequestsList where passportIssueCountry contains UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.contains=" + UPDATED_PASSPORT_ISSUE_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportIssueCountryNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportIssueCountry does not contain DEFAULT_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldNotBeFound("passportIssueCountry.doesNotContain=" + DEFAULT_PASSPORT_ISSUE_COUNTRY);

        // Get all the dlsRequestsList where passportIssueCountry does not contain UPDATED_PASSPORT_ISSUE_COUNTRY
        defaultDlsRequestsShouldBeFound("passportIssueCountry.doesNotContain=" + UPDATED_PASSPORT_ISSUE_COUNTRY);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey equals to DEFAULT_PASSPORT_KEY
        defaultDlsRequestsShouldBeFound("passportKey.equals=" + DEFAULT_PASSPORT_KEY);

        // Get all the dlsRequestsList where passportKey equals to UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldNotBeFound("passportKey.equals=" + UPDATED_PASSPORT_KEY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey not equals to DEFAULT_PASSPORT_KEY
        defaultDlsRequestsShouldNotBeFound("passportKey.notEquals=" + DEFAULT_PASSPORT_KEY);

        // Get all the dlsRequestsList where passportKey not equals to UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldBeFound("passportKey.notEquals=" + UPDATED_PASSPORT_KEY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey in DEFAULT_PASSPORT_KEY or UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldBeFound("passportKey.in=" + DEFAULT_PASSPORT_KEY + "," + UPDATED_PASSPORT_KEY);

        // Get all the dlsRequestsList where passportKey equals to UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldNotBeFound("passportKey.in=" + UPDATED_PASSPORT_KEY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey is not null
        defaultDlsRequestsShouldBeFound("passportKey.specified=true");

        // Get all the dlsRequestsList where passportKey is null
        defaultDlsRequestsShouldNotBeFound("passportKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey contains DEFAULT_PASSPORT_KEY
        defaultDlsRequestsShouldBeFound("passportKey.contains=" + DEFAULT_PASSPORT_KEY);

        // Get all the dlsRequestsList where passportKey contains UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldNotBeFound("passportKey.contains=" + UPDATED_PASSPORT_KEY);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportKeyNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportKey does not contain DEFAULT_PASSPORT_KEY
        defaultDlsRequestsShouldNotBeFound("passportKey.doesNotContain=" + DEFAULT_PASSPORT_KEY);

        // Get all the dlsRequestsList where passportKey does not contain UPDATED_PASSPORT_KEY
        defaultDlsRequestsShouldBeFound("passportKey.doesNotContain=" + UPDATED_PASSPORT_KEY);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode equals to DEFAULT_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldBeFound("trafficUnitCode.equals=" + DEFAULT_TRAFFIC_UNIT_CODE);

        // Get all the dlsRequestsList where trafficUnitCode equals to UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.equals=" + UPDATED_TRAFFIC_UNIT_CODE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode not equals to DEFAULT_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.notEquals=" + DEFAULT_TRAFFIC_UNIT_CODE);

        // Get all the dlsRequestsList where trafficUnitCode not equals to UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldBeFound("trafficUnitCode.notEquals=" + UPDATED_TRAFFIC_UNIT_CODE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode in DEFAULT_TRAFFIC_UNIT_CODE or UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldBeFound("trafficUnitCode.in=" + DEFAULT_TRAFFIC_UNIT_CODE + "," + UPDATED_TRAFFIC_UNIT_CODE);

        // Get all the dlsRequestsList where trafficUnitCode equals to UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.in=" + UPDATED_TRAFFIC_UNIT_CODE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode is not null
        defaultDlsRequestsShouldBeFound("trafficUnitCode.specified=true");

        // Get all the dlsRequestsList where trafficUnitCode is null
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode contains DEFAULT_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldBeFound("trafficUnitCode.contains=" + DEFAULT_TRAFFIC_UNIT_CODE);

        // Get all the dlsRequestsList where trafficUnitCode contains UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.contains=" + UPDATED_TRAFFIC_UNIT_CODE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitCodeNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnitCode does not contain DEFAULT_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldNotBeFound("trafficUnitCode.doesNotContain=" + DEFAULT_TRAFFIC_UNIT_CODE);

        // Get all the dlsRequestsList where trafficUnitCode does not contain UPDATED_TRAFFIC_UNIT_CODE
        defaultDlsRequestsShouldBeFound("trafficUnitCode.doesNotContain=" + UPDATED_TRAFFIC_UNIT_CODE);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate equals to UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate equals to UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate is not null
        defaultDlsRequestsShouldBeFound("birthDate.specified=true");

        // Get all the dlsRequestsList where birthDate is null
        defaultDlsRequestsShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate is less than UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate equals to DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.equals=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.equals=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate not equals to DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.notEquals=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate not equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.notEquals=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate in DEFAULT_LICENCE_EXPIRY_DATE or UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.in=" + DEFAULT_LICENCE_EXPIRY_DATE + "," + UPDATED_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.in=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate is not null
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.specified=true");

        // Get all the dlsRequestsList where licenceExpiryDate is null
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate is greater than or equal to DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.greaterThanOrEqual=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate is greater than or equal to UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.greaterThanOrEqual=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate is less than or equal to DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.lessThanOrEqual=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate is less than or equal to SMALLER_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.lessThanOrEqual=" + SMALLER_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate is less than DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.lessThan=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate is less than UPDATED_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.lessThan=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceExpiryDate is greater than DEFAULT_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldNotBeFound("licenceExpiryDate.greaterThan=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the dlsRequestsList where licenceExpiryDate is greater than SMALLER_LICENCE_EXPIRY_DATE
        defaultDlsRequestsShouldBeFound("licenceExpiryDate.greaterThan=" + SMALLER_LICENCE_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr equals to DEFAULT_LICENCE_TYPE_AR
        defaultDlsRequestsShouldBeFound("licenceTypeAr.equals=" + DEFAULT_LICENCE_TYPE_AR);

        // Get all the dlsRequestsList where licenceTypeAr equals to UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.equals=" + UPDATED_LICENCE_TYPE_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr not equals to DEFAULT_LICENCE_TYPE_AR
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.notEquals=" + DEFAULT_LICENCE_TYPE_AR);

        // Get all the dlsRequestsList where licenceTypeAr not equals to UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldBeFound("licenceTypeAr.notEquals=" + UPDATED_LICENCE_TYPE_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr in DEFAULT_LICENCE_TYPE_AR or UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldBeFound("licenceTypeAr.in=" + DEFAULT_LICENCE_TYPE_AR + "," + UPDATED_LICENCE_TYPE_AR);

        // Get all the dlsRequestsList where licenceTypeAr equals to UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.in=" + UPDATED_LICENCE_TYPE_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr is not null
        defaultDlsRequestsShouldBeFound("licenceTypeAr.specified=true");

        // Get all the dlsRequestsList where licenceTypeAr is null
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr contains DEFAULT_LICENCE_TYPE_AR
        defaultDlsRequestsShouldBeFound("licenceTypeAr.contains=" + DEFAULT_LICENCE_TYPE_AR);

        // Get all the dlsRequestsList where licenceTypeAr contains UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.contains=" + UPDATED_LICENCE_TYPE_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeArNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeAr does not contain DEFAULT_LICENCE_TYPE_AR
        defaultDlsRequestsShouldNotBeFound("licenceTypeAr.doesNotContain=" + DEFAULT_LICENCE_TYPE_AR);

        // Get all the dlsRequestsList where licenceTypeAr does not contain UPDATED_LICENCE_TYPE_AR
        defaultDlsRequestsShouldBeFound("licenceTypeAr.doesNotContain=" + UPDATED_LICENCE_TYPE_AR);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn equals to DEFAULT_LICENCE_TYPE_EN
        defaultDlsRequestsShouldBeFound("licenceTypeEn.equals=" + DEFAULT_LICENCE_TYPE_EN);

        // Get all the dlsRequestsList where licenceTypeEn equals to UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.equals=" + UPDATED_LICENCE_TYPE_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn not equals to DEFAULT_LICENCE_TYPE_EN
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.notEquals=" + DEFAULT_LICENCE_TYPE_EN);

        // Get all the dlsRequestsList where licenceTypeEn not equals to UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldBeFound("licenceTypeEn.notEquals=" + UPDATED_LICENCE_TYPE_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn in DEFAULT_LICENCE_TYPE_EN or UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldBeFound("licenceTypeEn.in=" + DEFAULT_LICENCE_TYPE_EN + "," + UPDATED_LICENCE_TYPE_EN);

        // Get all the dlsRequestsList where licenceTypeEn equals to UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.in=" + UPDATED_LICENCE_TYPE_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn is not null
        defaultDlsRequestsShouldBeFound("licenceTypeEn.specified=true");

        // Get all the dlsRequestsList where licenceTypeEn is null
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn contains DEFAULT_LICENCE_TYPE_EN
        defaultDlsRequestsShouldBeFound("licenceTypeEn.contains=" + DEFAULT_LICENCE_TYPE_EN);

        // Get all the dlsRequestsList where licenceTypeEn contains UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.contains=" + UPDATED_LICENCE_TYPE_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceTypeEnNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceTypeEn does not contain DEFAULT_LICENCE_TYPE_EN
        defaultDlsRequestsShouldNotBeFound("licenceTypeEn.doesNotContain=" + DEFAULT_LICENCE_TYPE_EN);

        // Get all the dlsRequestsList where licenceTypeEn does not contain UPDATED_LICENCE_TYPE_EN
        defaultDlsRequestsShouldBeFound("licenceTypeEn.doesNotContain=" + UPDATED_LICENCE_TYPE_EN);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr equals to DEFAULT_LICENCE_STATUS_AR
        defaultDlsRequestsShouldBeFound("licenceStatusAr.equals=" + DEFAULT_LICENCE_STATUS_AR);

        // Get all the dlsRequestsList where licenceStatusAr equals to UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.equals=" + UPDATED_LICENCE_STATUS_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr not equals to DEFAULT_LICENCE_STATUS_AR
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.notEquals=" + DEFAULT_LICENCE_STATUS_AR);

        // Get all the dlsRequestsList where licenceStatusAr not equals to UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldBeFound("licenceStatusAr.notEquals=" + UPDATED_LICENCE_STATUS_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr in DEFAULT_LICENCE_STATUS_AR or UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldBeFound("licenceStatusAr.in=" + DEFAULT_LICENCE_STATUS_AR + "," + UPDATED_LICENCE_STATUS_AR);

        // Get all the dlsRequestsList where licenceStatusAr equals to UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.in=" + UPDATED_LICENCE_STATUS_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr is not null
        defaultDlsRequestsShouldBeFound("licenceStatusAr.specified=true");

        // Get all the dlsRequestsList where licenceStatusAr is null
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr contains DEFAULT_LICENCE_STATUS_AR
        defaultDlsRequestsShouldBeFound("licenceStatusAr.contains=" + DEFAULT_LICENCE_STATUS_AR);

        // Get all the dlsRequestsList where licenceStatusAr contains UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.contains=" + UPDATED_LICENCE_STATUS_AR);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusArNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusAr does not contain DEFAULT_LICENCE_STATUS_AR
        defaultDlsRequestsShouldNotBeFound("licenceStatusAr.doesNotContain=" + DEFAULT_LICENCE_STATUS_AR);

        // Get all the dlsRequestsList where licenceStatusAr does not contain UPDATED_LICENCE_STATUS_AR
        defaultDlsRequestsShouldBeFound("licenceStatusAr.doesNotContain=" + UPDATED_LICENCE_STATUS_AR);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn equals to DEFAULT_LICENCE_STATUS_EN
        defaultDlsRequestsShouldBeFound("licenceStatusEn.equals=" + DEFAULT_LICENCE_STATUS_EN);

        // Get all the dlsRequestsList where licenceStatusEn equals to UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.equals=" + UPDATED_LICENCE_STATUS_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn not equals to DEFAULT_LICENCE_STATUS_EN
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.notEquals=" + DEFAULT_LICENCE_STATUS_EN);

        // Get all the dlsRequestsList where licenceStatusEn not equals to UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldBeFound("licenceStatusEn.notEquals=" + UPDATED_LICENCE_STATUS_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn in DEFAULT_LICENCE_STATUS_EN or UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldBeFound("licenceStatusEn.in=" + DEFAULT_LICENCE_STATUS_EN + "," + UPDATED_LICENCE_STATUS_EN);

        // Get all the dlsRequestsList where licenceStatusEn equals to UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.in=" + UPDATED_LICENCE_STATUS_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn is not null
        defaultDlsRequestsShouldBeFound("licenceStatusEn.specified=true");

        // Get all the dlsRequestsList where licenceStatusEn is null
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn contains DEFAULT_LICENCE_STATUS_EN
        defaultDlsRequestsShouldBeFound("licenceStatusEn.contains=" + DEFAULT_LICENCE_STATUS_EN);

        // Get all the dlsRequestsList where licenceStatusEn contains UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.contains=" + UPDATED_LICENCE_STATUS_EN);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusEnNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatusEn does not contain DEFAULT_LICENCE_STATUS_EN
        defaultDlsRequestsShouldNotBeFound("licenceStatusEn.doesNotContain=" + DEFAULT_LICENCE_STATUS_EN);

        // Get all the dlsRequestsList where licenceStatusEn does not contain UPDATED_LICENCE_STATUS_EN
        defaultDlsRequestsShouldBeFound("licenceStatusEn.doesNotContain=" + UPDATED_LICENCE_STATUS_EN);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus equals to DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.equals=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus equals to UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.equals=" + UPDATED_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus not equals to DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.notEquals=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus not equals to UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.notEquals=" + UPDATED_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus in DEFAULT_LICENCE_STATUS or UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.in=" + DEFAULT_LICENCE_STATUS + "," + UPDATED_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus equals to UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.in=" + UPDATED_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus is not null
        defaultDlsRequestsShouldBeFound("licenceStatus.specified=true");

        // Get all the dlsRequestsList where licenceStatus is null
        defaultDlsRequestsShouldNotBeFound("licenceStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus is greater than or equal to DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.greaterThanOrEqual=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus is greater than or equal to UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.greaterThanOrEqual=" + UPDATED_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus is less than or equal to DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.lessThanOrEqual=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus is less than or equal to SMALLER_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.lessThanOrEqual=" + SMALLER_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus is less than DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.lessThan=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus is less than UPDATED_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.lessThan=" + UPDATED_LICENCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenceStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenceStatus is greater than DEFAULT_LICENCE_STATUS
        defaultDlsRequestsShouldNotBeFound("licenceStatus.greaterThan=" + DEFAULT_LICENCE_STATUS);

        // Get all the dlsRequestsList where licenceStatus is greater than SMALLER_LICENCE_STATUS
        defaultDlsRequestsShouldBeFound("licenceStatus.greaterThan=" + SMALLER_LICENCE_STATUS);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId equals to DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.equals=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId equals to UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.equals=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId not equals to DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.notEquals=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId not equals to UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.notEquals=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId in DEFAULT_APPLICANT_ID or UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.in=" + DEFAULT_APPLICANT_ID + "," + UPDATED_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId equals to UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.in=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId is not null
        defaultDlsRequestsShouldBeFound("applicantId.specified=true");

        // Get all the dlsRequestsList where applicantId is null
        defaultDlsRequestsShouldNotBeFound("applicantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId is greater than or equal to DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.greaterThanOrEqual=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId is greater than or equal to UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.greaterThanOrEqual=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId is less than or equal to DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.lessThanOrEqual=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId is less than or equal to SMALLER_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.lessThanOrEqual=" + SMALLER_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId is less than DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.lessThan=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId is less than UPDATED_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.lessThan=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByApplicantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where applicantId is greater than DEFAULT_APPLICANT_ID
        defaultDlsRequestsShouldNotBeFound("applicantId.greaterThan=" + DEFAULT_APPLICANT_ID);

        // Get all the dlsRequestsList where applicantId is greater than SMALLER_APPLICANT_ID
        defaultDlsRequestsShouldBeFound("applicantId.greaterThan=" + SMALLER_APPLICANT_ID);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId equals to DEFAULT_USER_ID
        defaultDlsRequestsShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId equals to UPDATED_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId not equals to DEFAULT_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId not equals to UPDATED_USER_ID
        defaultDlsRequestsShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultDlsRequestsShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the dlsRequestsList where userId equals to UPDATED_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId is not null
        defaultDlsRequestsShouldBeFound("userId.specified=true");

        // Get all the dlsRequestsList where userId is null
        defaultDlsRequestsShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId is greater than or equal to DEFAULT_USER_ID
        defaultDlsRequestsShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId is greater than or equal to UPDATED_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId is less than or equal to DEFAULT_USER_ID
        defaultDlsRequestsShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId is less than or equal to SMALLER_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId is less than DEFAULT_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId is less than UPDATED_USER_ID
        defaultDlsRequestsShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where userId is greater than DEFAULT_USER_ID
        defaultDlsRequestsShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the dlsRequestsList where userId is greater than SMALLER_USER_ID
        defaultDlsRequestsShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId equals to DEFAULT_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.equals=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId equals to UPDATED_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.equals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId not equals to DEFAULT_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.notEquals=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId not equals to UPDATED_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.notEquals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId in DEFAULT_CENTER_ID or UPDATED_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.in=" + DEFAULT_CENTER_ID + "," + UPDATED_CENTER_ID);

        // Get all the dlsRequestsList where centerId equals to UPDATED_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.in=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId is not null
        defaultDlsRequestsShouldBeFound("centerId.specified=true");

        // Get all the dlsRequestsList where centerId is null
        defaultDlsRequestsShouldNotBeFound("centerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId is greater than or equal to DEFAULT_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.greaterThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId is greater than or equal to UPDATED_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.greaterThanOrEqual=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId is less than or equal to DEFAULT_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.lessThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId is less than or equal to SMALLER_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.lessThanOrEqual=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId is less than DEFAULT_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.lessThan=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId is less than UPDATED_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.lessThan=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByCenterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where centerId is greater than DEFAULT_CENTER_ID
        defaultDlsRequestsShouldNotBeFound("centerId.greaterThan=" + DEFAULT_CENTER_ID);

        // Get all the dlsRequestsList where centerId is greater than SMALLER_CENTER_ID
        defaultDlsRequestsShouldBeFound("centerId.greaterThan=" + SMALLER_CENTER_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDlsRequestsShouldBeFound(String filter) throws Exception {
        restDlsRequestsMockMvc.perform(get("/api/dls-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dlsRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].licenseCategory").value(hasItem(DEFAULT_LICENSE_CATEGORY)))
            .andExpect(jsonPath("$.[*].requestNo").value(hasItem(DEFAULT_REQUEST_NO)))
            .andExpect(jsonPath("$.[*].exported").value(hasItem(DEFAULT_EXPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].passportIssueCountry").value(hasItem(DEFAULT_PASSPORT_ISSUE_COUNTRY)))
            .andExpect(jsonPath("$.[*].passportKey").value(hasItem(DEFAULT_PASSPORT_KEY)))
            .andExpect(jsonPath("$.[*].trafficUnitCode").value(hasItem(DEFAULT_TRAFFIC_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].licenceExpiryDate").value(hasItem(DEFAULT_LICENCE_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].licenceTypeAr").value(hasItem(DEFAULT_LICENCE_TYPE_AR)))
            .andExpect(jsonPath("$.[*].licenceTypeEn").value(hasItem(DEFAULT_LICENCE_TYPE_EN)))
            .andExpect(jsonPath("$.[*].licenceStatusAr").value(hasItem(DEFAULT_LICENCE_STATUS_AR)))
            .andExpect(jsonPath("$.[*].licenceStatusEn").value(hasItem(DEFAULT_LICENCE_STATUS_EN)))
            .andExpect(jsonPath("$.[*].licenceStatus").value(hasItem(DEFAULT_LICENCE_STATUS)))
            .andExpect(jsonPath("$.[*].applicantId").value(hasItem(DEFAULT_APPLICANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())));

        // Check, that the count call also returns 1
        restDlsRequestsMockMvc.perform(get("/api/dls-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDlsRequestsShouldNotBeFound(String filter) throws Exception {
        restDlsRequestsMockMvc.perform(get("/api/dls-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDlsRequestsMockMvc.perform(get("/api/dls-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDlsRequests() throws Exception {
        // Get the dlsRequests
        restDlsRequestsMockMvc.perform(get("/api/dls-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlsRequests() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        int databaseSizeBeforeUpdate = dlsRequestsRepository.findAll().size();

        // Update the dlsRequests
        DlsRequests updatedDlsRequests = dlsRequestsRepository.findById(dlsRequests.getId()).get();
        // Disconnect from session so that the updates on updatedDlsRequests are not directly saved in db
        em.detach(updatedDlsRequests);
        updatedDlsRequests
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .licenseCategory(UPDATED_LICENSE_CATEGORY)
            .requestNo(UPDATED_REQUEST_NO)
            .exported(UPDATED_EXPORTED)
            .familyName(UPDATED_FAMILY_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .fullName(UPDATED_FULL_NAME)
            .nationalId(UPDATED_NATIONAL_ID)
            .passportIssueCountry(UPDATED_PASSPORT_ISSUE_COUNTRY)
            .passportKey(UPDATED_PASSPORT_KEY)
            .trafficUnitCode(UPDATED_TRAFFIC_UNIT_CODE)
            .birthDate(UPDATED_BIRTH_DATE)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .licenceTypeAr(UPDATED_LICENCE_TYPE_AR)
            .licenceTypeEn(UPDATED_LICENCE_TYPE_EN)
            .licenceStatusAr(UPDATED_LICENCE_STATUS_AR)
            .licenceStatusEn(UPDATED_LICENCE_STATUS_EN)
            .licenceStatus(UPDATED_LICENCE_STATUS)
            .applicantId(UPDATED_APPLICANT_ID)
            .userId(UPDATED_USER_ID)
            .centerId(UPDATED_CENTER_ID);
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(updatedDlsRequests);

        restDlsRequestsMockMvc.perform(put("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the DlsRequests in the database
        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeUpdate);
        DlsRequests testDlsRequests = dlsRequestsList.get(dlsRequestsList.size() - 1);
        assertThat(testDlsRequests.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testDlsRequests.getLicenseCategory()).isEqualTo(UPDATED_LICENSE_CATEGORY);
        assertThat(testDlsRequests.getRequestNo()).isEqualTo(UPDATED_REQUEST_NO);
        assertThat(testDlsRequests.isExported()).isEqualTo(UPDATED_EXPORTED);
        assertThat(testDlsRequests.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testDlsRequests.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDlsRequests.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDlsRequests.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testDlsRequests.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDlsRequests.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testDlsRequests.getPassportIssueCountry()).isEqualTo(UPDATED_PASSPORT_ISSUE_COUNTRY);
        assertThat(testDlsRequests.getPassportKey()).isEqualTo(UPDATED_PASSPORT_KEY);
        assertThat(testDlsRequests.getTrafficUnitCode()).isEqualTo(UPDATED_TRAFFIC_UNIT_CODE);
        assertThat(testDlsRequests.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testDlsRequests.getLicenceExpiryDate()).isEqualTo(UPDATED_LICENCE_EXPIRY_DATE);
        assertThat(testDlsRequests.getLicenceTypeAr()).isEqualTo(UPDATED_LICENCE_TYPE_AR);
        assertThat(testDlsRequests.getLicenceTypeEn()).isEqualTo(UPDATED_LICENCE_TYPE_EN);
        assertThat(testDlsRequests.getLicenceStatusAr()).isEqualTo(UPDATED_LICENCE_STATUS_AR);
        assertThat(testDlsRequests.getLicenceStatusEn()).isEqualTo(UPDATED_LICENCE_STATUS_EN);
        assertThat(testDlsRequests.getLicenceStatus()).isEqualTo(UPDATED_LICENCE_STATUS);
        assertThat(testDlsRequests.getApplicantId()).isEqualTo(UPDATED_APPLICANT_ID);
        assertThat(testDlsRequests.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDlsRequests.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDlsRequests() throws Exception {
        int databaseSizeBeforeUpdate = dlsRequestsRepository.findAll().size();

        // Create the DlsRequests
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(dlsRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDlsRequestsMockMvc.perform(put("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DlsRequests in the database
        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

//    @Test
//    @Transactional
//    public void deleteDlsRequests() throws Exception {
//        // Initialize the database
//        dlsRequestsRepository.saveAndFlush(dlsRequests);
//
//        int databaseSizeBeforeDelete = dlsRequestsRepository.findAll().size();
//
//        // Delete the dlsRequests
//        restDlsRequestsMockMvc.perform(delete("/api/dls-requests/{id}", dlsRequests.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
//        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
