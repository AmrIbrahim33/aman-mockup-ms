package com.isoft.mockup.service.impl;

import com.isoft.mockup.service.DlsRequestsService;
import com.isoft.mockup.domain.DlsRequests;
import com.isoft.mockup.repository.DlsRequestsRepository;
import com.isoft.mockup.service.dto.*;
import com.isoft.mockup.service.mapper.DlsRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link DlsRequests}.
 */
@Service
@Transactional
public class DlsRequestsServiceImpl implements DlsRequestsService {

    private final Logger log = LoggerFactory.getLogger(DlsRequestsServiceImpl.class);

    private final DlsRequestsRepository dlsRequestsRepository;

    private final DlsRequestsMapper dlsRequestsMapper;

    public DlsRequestsServiceImpl(DlsRequestsRepository dlsRequestsRepository, DlsRequestsMapper dlsRequestsMapper) {
        this.dlsRequestsRepository = dlsRequestsRepository;
        this.dlsRequestsMapper = dlsRequestsMapper;
    }

    /**
     * Save a dlsRequests.
     *
     * @param dlsRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DlsRequestsDTO save(DlsRequestsDTO dlsRequestsDTO) {
        log.debug("Request to save DlsRequests : {}", dlsRequestsDTO);
        DlsRequests dlsRequests = dlsRequestsMapper.toEntity(dlsRequestsDTO);
        dlsRequests = dlsRequestsRepository.save(dlsRequests);
        return dlsRequestsMapper.toDto(dlsRequests);
    }

    /**
     * Get all the dlsRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DlsRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DlsRequests");
        return dlsRequestsRepository.findAll(pageable)
            .map(dlsRequestsMapper::toDto);
    }


    /**
     * Get one dlsRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DlsRequestsDTO> findOne(Long id) {
        log.debug("Request to get DlsRequests : {}", id);
        return dlsRequestsRepository.findById(id)
            .map(dlsRequestsMapper::toDto);
    }

    /**
     * Delete the dlsRequests by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DlsRequests : {}", id);
        dlsRequestsRepository.deleteById(id);
    }

    @Override
    public InquireExamEligibilityResponse inquiry(Long requestId) {
        InquireExamEligibilityResponse response = new InquireExamEligibilityResponse();

        ThreeSHeader header = new ThreeSHeader();
        header.setVersion("1.0");
        header.setCategory("response");
        header.setService("TIT_TheoExam_Inquiry");
        header.setTimestamp(new Date().toString());
        header.setTid(UUID.randomUUID().toString());

        response.setHeader(header);

        InquireExamEligibilityResponseBody body = new InquireExamEligibilityResponseBody();
        body.setErrorMessage(200);

        List<InquireExamEligibilityResponseDetails> requests = new ArrayList<>();

        DlsRequests dlsRequests = dlsRequestsRepository.findByRequestID(requestId);
        InquireExamEligibilityResponseDetails reqDetails = convertToReqDetails(dlsRequests);
        requests.add(reqDetails);
        body.setRequests(requests);
        response.setBody(body);
        return response;
    }

    private InquireExamEligibilityResponseDetails convertToReqDetails(DlsRequests dlsRequests) {
        InquireExamEligibilityResponseDetails reqDetails = new InquireExamEligibilityResponseDetails();
        reqDetails.setRequestID(dlsRequests.getRequestID());
        reqDetails.setFirstName(dlsRequests.getFirstName());
        reqDetails.setFamilyName(dlsRequests.getFamilyName());
        reqDetails.setMiddleName(dlsRequests.getMiddleName());
        reqDetails.setLastName(dlsRequests.getLastName());
        reqDetails.setBirthDate(dlsRequests.getBirthDate());
        reqDetails.setTrafficUnit(dlsRequests.getTrafficUnit());
        reqDetails.setLicenseType(dlsRequests.getLicenseType());
        reqDetails.setNationalID(dlsRequests.getNationalID());
        reqDetails.setPassportIssueCountry(dlsRequests.getPassportIssueCountry());
        reqDetails.setPassportNo(dlsRequests.getPassportNo());
        return reqDetails;
    }
}
