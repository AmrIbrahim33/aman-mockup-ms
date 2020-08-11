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

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;
    private static final Long SMALLER_REQUEST_ID = 1L - 1L;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_ISSUE_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_ISSUE_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TRAFFIC_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_TRAFFIC_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

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
            .requestID(DEFAULT_REQUEST_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .familyName(DEFAULT_FAMILY_NAME)
            .nationalID(DEFAULT_NATIONAL_ID)
            .passportNo(DEFAULT_PASSPORT_NO)
            .passportIssueCountry(DEFAULT_PASSPORT_ISSUE_COUNTRY)
            .licenseType(DEFAULT_LICENSE_TYPE)
            .trafficUnit(DEFAULT_TRAFFIC_UNIT)
            .birthDate(DEFAULT_BIRTH_DATE);
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
            .requestID(UPDATED_REQUEST_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .nationalID(UPDATED_NATIONAL_ID)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueCountry(UPDATED_PASSPORT_ISSUE_COUNTRY)
            .licenseType(UPDATED_LICENSE_TYPE)
            .trafficUnit(UPDATED_TRAFFIC_UNIT)
            .birthDate(UPDATED_BIRTH_DATE);
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
        assertThat(testDlsRequests.getRequestID()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testDlsRequests.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDlsRequests.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testDlsRequests.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDlsRequests.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testDlsRequests.getNationalID()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testDlsRequests.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testDlsRequests.getPassportIssueCountry()).isEqualTo(DEFAULT_PASSPORT_ISSUE_COUNTRY);
        assertThat(testDlsRequests.getLicenseType()).isEqualTo(DEFAULT_LICENSE_TYPE);
        assertThat(testDlsRequests.getTrafficUnit()).isEqualTo(DEFAULT_TRAFFIC_UNIT);
        assertThat(testDlsRequests.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
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
    public void getAllDlsRequests() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList
        restDlsRequestsMockMvc.perform(get("/api/dls-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dlsRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestID").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].nationalID").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportIssueCountry").value(hasItem(DEFAULT_PASSPORT_ISSUE_COUNTRY)))
            .andExpect(jsonPath("$.[*].licenseType").value(hasItem(DEFAULT_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].trafficUnit").value(hasItem(DEFAULT_TRAFFIC_UNIT)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)));
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
            .andExpect(jsonPath("$.requestID").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME))
            .andExpect(jsonPath("$.nationalID").value(DEFAULT_NATIONAL_ID))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.passportIssueCountry").value(DEFAULT_PASSPORT_ISSUE_COUNTRY))
            .andExpect(jsonPath("$.licenseType").value(DEFAULT_LICENSE_TYPE))
            .andExpect(jsonPath("$.trafficUnit").value(DEFAULT_TRAFFIC_UNIT))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE));
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
    public void getAllDlsRequestsByRequestIDIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID equals to DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.equals=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID equals to UPDATED_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.equals=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID not equals to DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.notEquals=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID not equals to UPDATED_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.notEquals=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID in DEFAULT_REQUEST_ID or UPDATED_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.in=" + DEFAULT_REQUEST_ID + "," + UPDATED_REQUEST_ID);

        // Get all the dlsRequestsList where requestID equals to UPDATED_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.in=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID is not null
        defaultDlsRequestsShouldBeFound("requestID.specified=true");

        // Get all the dlsRequestsList where requestID is null
        defaultDlsRequestsShouldNotBeFound("requestID.specified=false");
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID is greater than or equal to DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.greaterThanOrEqual=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID is greater than or equal to UPDATED_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.greaterThanOrEqual=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID is less than or equal to DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.lessThanOrEqual=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID is less than or equal to SMALLER_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.lessThanOrEqual=" + SMALLER_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsLessThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID is less than DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.lessThan=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID is less than UPDATED_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.lessThan=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByRequestIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where requestID is greater than DEFAULT_REQUEST_ID
        defaultDlsRequestsShouldNotBeFound("requestID.greaterThan=" + DEFAULT_REQUEST_ID);

        // Get all the dlsRequestsList where requestID is greater than SMALLER_REQUEST_ID
        defaultDlsRequestsShouldBeFound("requestID.greaterThan=" + SMALLER_REQUEST_ID);
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
    public void getAllDlsRequestsByNationalIDIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID equals to DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalID.equals=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalID equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalID.equals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID not equals to DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalID.notEquals=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalID not equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalID.notEquals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIDIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID in DEFAULT_NATIONAL_ID or UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalID.in=" + DEFAULT_NATIONAL_ID + "," + UPDATED_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalID equals to UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalID.in=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID is not null
        defaultDlsRequestsShouldBeFound("nationalID.specified=true");

        // Get all the dlsRequestsList where nationalID is null
        defaultDlsRequestsShouldNotBeFound("nationalID.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByNationalIDContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID contains DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalID.contains=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalID contains UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalID.contains=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByNationalIDNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where nationalID does not contain DEFAULT_NATIONAL_ID
        defaultDlsRequestsShouldNotBeFound("nationalID.doesNotContain=" + DEFAULT_NATIONAL_ID);

        // Get all the dlsRequestsList where nationalID does not contain UPDATED_NATIONAL_ID
        defaultDlsRequestsShouldBeFound("nationalID.doesNotContain=" + UPDATED_NATIONAL_ID);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo equals to DEFAULT_PASSPORT_NO
        defaultDlsRequestsShouldBeFound("passportNo.equals=" + DEFAULT_PASSPORT_NO);

        // Get all the dlsRequestsList where passportNo equals to UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldNotBeFound("passportNo.equals=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo not equals to DEFAULT_PASSPORT_NO
        defaultDlsRequestsShouldNotBeFound("passportNo.notEquals=" + DEFAULT_PASSPORT_NO);

        // Get all the dlsRequestsList where passportNo not equals to UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldBeFound("passportNo.notEquals=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo in DEFAULT_PASSPORT_NO or UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldBeFound("passportNo.in=" + DEFAULT_PASSPORT_NO + "," + UPDATED_PASSPORT_NO);

        // Get all the dlsRequestsList where passportNo equals to UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldNotBeFound("passportNo.in=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo is not null
        defaultDlsRequestsShouldBeFound("passportNo.specified=true");

        // Get all the dlsRequestsList where passportNo is null
        defaultDlsRequestsShouldNotBeFound("passportNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo contains DEFAULT_PASSPORT_NO
        defaultDlsRequestsShouldBeFound("passportNo.contains=" + DEFAULT_PASSPORT_NO);

        // Get all the dlsRequestsList where passportNo contains UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldNotBeFound("passportNo.contains=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByPassportNoNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where passportNo does not contain DEFAULT_PASSPORT_NO
        defaultDlsRequestsShouldNotBeFound("passportNo.doesNotContain=" + DEFAULT_PASSPORT_NO);

        // Get all the dlsRequestsList where passportNo does not contain UPDATED_PASSPORT_NO
        defaultDlsRequestsShouldBeFound("passportNo.doesNotContain=" + UPDATED_PASSPORT_NO);
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
    public void getAllDlsRequestsByLicenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType equals to DEFAULT_LICENSE_TYPE
        defaultDlsRequestsShouldBeFound("licenseType.equals=" + DEFAULT_LICENSE_TYPE);

        // Get all the dlsRequestsList where licenseType equals to UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldNotBeFound("licenseType.equals=" + UPDATED_LICENSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType not equals to DEFAULT_LICENSE_TYPE
        defaultDlsRequestsShouldNotBeFound("licenseType.notEquals=" + DEFAULT_LICENSE_TYPE);

        // Get all the dlsRequestsList where licenseType not equals to UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldBeFound("licenseType.notEquals=" + UPDATED_LICENSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType in DEFAULT_LICENSE_TYPE or UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldBeFound("licenseType.in=" + DEFAULT_LICENSE_TYPE + "," + UPDATED_LICENSE_TYPE);

        // Get all the dlsRequestsList where licenseType equals to UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldNotBeFound("licenseType.in=" + UPDATED_LICENSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType is not null
        defaultDlsRequestsShouldBeFound("licenseType.specified=true");

        // Get all the dlsRequestsList where licenseType is null
        defaultDlsRequestsShouldNotBeFound("licenseType.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByLicenseTypeContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType contains DEFAULT_LICENSE_TYPE
        defaultDlsRequestsShouldBeFound("licenseType.contains=" + DEFAULT_LICENSE_TYPE);

        // Get all the dlsRequestsList where licenseType contains UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldNotBeFound("licenseType.contains=" + UPDATED_LICENSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByLicenseTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where licenseType does not contain DEFAULT_LICENSE_TYPE
        defaultDlsRequestsShouldNotBeFound("licenseType.doesNotContain=" + DEFAULT_LICENSE_TYPE);

        // Get all the dlsRequestsList where licenseType does not contain UPDATED_LICENSE_TYPE
        defaultDlsRequestsShouldBeFound("licenseType.doesNotContain=" + UPDATED_LICENSE_TYPE);
    }


    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit equals to DEFAULT_TRAFFIC_UNIT
        defaultDlsRequestsShouldBeFound("trafficUnit.equals=" + DEFAULT_TRAFFIC_UNIT);

        // Get all the dlsRequestsList where trafficUnit equals to UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldNotBeFound("trafficUnit.equals=" + UPDATED_TRAFFIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit not equals to DEFAULT_TRAFFIC_UNIT
        defaultDlsRequestsShouldNotBeFound("trafficUnit.notEquals=" + DEFAULT_TRAFFIC_UNIT);

        // Get all the dlsRequestsList where trafficUnit not equals to UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldBeFound("trafficUnit.notEquals=" + UPDATED_TRAFFIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit in DEFAULT_TRAFFIC_UNIT or UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldBeFound("trafficUnit.in=" + DEFAULT_TRAFFIC_UNIT + "," + UPDATED_TRAFFIC_UNIT);

        // Get all the dlsRequestsList where trafficUnit equals to UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldNotBeFound("trafficUnit.in=" + UPDATED_TRAFFIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit is not null
        defaultDlsRequestsShouldBeFound("trafficUnit.specified=true");

        // Get all the dlsRequestsList where trafficUnit is null
        defaultDlsRequestsShouldNotBeFound("trafficUnit.specified=false");
    }
                @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit contains DEFAULT_TRAFFIC_UNIT
        defaultDlsRequestsShouldBeFound("trafficUnit.contains=" + DEFAULT_TRAFFIC_UNIT);

        // Get all the dlsRequestsList where trafficUnit contains UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldNotBeFound("trafficUnit.contains=" + UPDATED_TRAFFIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByTrafficUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where trafficUnit does not contain DEFAULT_TRAFFIC_UNIT
        defaultDlsRequestsShouldNotBeFound("trafficUnit.doesNotContain=" + DEFAULT_TRAFFIC_UNIT);

        // Get all the dlsRequestsList where trafficUnit does not contain UPDATED_TRAFFIC_UNIT
        defaultDlsRequestsShouldBeFound("trafficUnit.doesNotContain=" + UPDATED_TRAFFIC_UNIT);
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
    public void getAllDlsRequestsByBirthDateContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate contains DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.contains=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate contains UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.contains=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllDlsRequestsByBirthDateNotContainsSomething() throws Exception {
        // Initialize the database
        dlsRequestsRepository.saveAndFlush(dlsRequests);

        // Get all the dlsRequestsList where birthDate does not contain DEFAULT_BIRTH_DATE
        defaultDlsRequestsShouldNotBeFound("birthDate.doesNotContain=" + DEFAULT_BIRTH_DATE);

        // Get all the dlsRequestsList where birthDate does not contain UPDATED_BIRTH_DATE
        defaultDlsRequestsShouldBeFound("birthDate.doesNotContain=" + UPDATED_BIRTH_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDlsRequestsShouldBeFound(String filter) throws Exception {
        restDlsRequestsMockMvc.perform(get("/api/dls-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dlsRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestID").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].nationalID").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportIssueCountry").value(hasItem(DEFAULT_PASSPORT_ISSUE_COUNTRY)))
            .andExpect(jsonPath("$.[*].licenseType").value(hasItem(DEFAULT_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].trafficUnit").value(hasItem(DEFAULT_TRAFFIC_UNIT)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)));

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
            .requestID(UPDATED_REQUEST_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .nationalID(UPDATED_NATIONAL_ID)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueCountry(UPDATED_PASSPORT_ISSUE_COUNTRY)
            .licenseType(UPDATED_LICENSE_TYPE)
            .trafficUnit(UPDATED_TRAFFIC_UNIT)
            .birthDate(UPDATED_BIRTH_DATE);
        DlsRequestsDTO dlsRequestsDTO = dlsRequestsMapper.toDto(updatedDlsRequests);

        restDlsRequestsMockMvc.perform(put("/api/dls-requests")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dlsRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the DlsRequests in the database
        List<DlsRequests> dlsRequestsList = dlsRequestsRepository.findAll();
        assertThat(dlsRequestsList).hasSize(databaseSizeBeforeUpdate);
        DlsRequests testDlsRequests = dlsRequestsList.get(dlsRequestsList.size() - 1);
        assertThat(testDlsRequests.getRequestID()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testDlsRequests.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDlsRequests.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testDlsRequests.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDlsRequests.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testDlsRequests.getNationalID()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testDlsRequests.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testDlsRequests.getPassportIssueCountry()).isEqualTo(UPDATED_PASSPORT_ISSUE_COUNTRY);
        assertThat(testDlsRequests.getLicenseType()).isEqualTo(UPDATED_LICENSE_TYPE);
        assertThat(testDlsRequests.getTrafficUnit()).isEqualTo(UPDATED_TRAFFIC_UNIT);
        assertThat(testDlsRequests.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
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
