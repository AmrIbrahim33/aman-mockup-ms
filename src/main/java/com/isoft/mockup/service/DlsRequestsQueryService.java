package com.isoft.mockup.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isoft.mockup.domain.DlsRequests;
import com.isoft.mockup.domain.*; // for static metamodels
import com.isoft.mockup.repository.DlsRequestsRepository;
import com.isoft.mockup.service.dto.DlsRequestsCriteria;
import com.isoft.mockup.service.dto.DlsRequestsDTO;
import com.isoft.mockup.service.mapper.DlsRequestsMapper;

/**
 * Service for executing complex queries for {@link DlsRequests} entities in the database.
 * The main input is a {@link DlsRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DlsRequestsDTO} or a {@link Page} of {@link DlsRequestsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DlsRequestsQueryService extends QueryService<DlsRequests> {

    private final Logger log = LoggerFactory.getLogger(DlsRequestsQueryService.class);

    private final DlsRequestsRepository dlsRequestsRepository;

    private final DlsRequestsMapper dlsRequestsMapper;

    public DlsRequestsQueryService(DlsRequestsRepository dlsRequestsRepository, DlsRequestsMapper dlsRequestsMapper) {
        this.dlsRequestsRepository = dlsRequestsRepository;
        this.dlsRequestsMapper = dlsRequestsMapper;
    }

    /**
     * Return a {@link List} of {@link DlsRequestsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DlsRequestsDTO> findByCriteria(DlsRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DlsRequests> specification = createSpecification(criteria);
        return dlsRequestsMapper.toDto(dlsRequestsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DlsRequestsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DlsRequestsDTO> findByCriteria(DlsRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DlsRequests> specification = createSpecification(criteria);
        return dlsRequestsRepository.findAll(specification, page)
            .map(dlsRequestsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DlsRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DlsRequests> specification = createSpecification(criteria);
        return dlsRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link DlsRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DlsRequests> createSpecification(DlsRequestsCriteria criteria) {
        Specification<DlsRequests> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DlsRequests_.id));
            }
            if (criteria.getRequestID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestID(), DlsRequests_.requestID));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), DlsRequests_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), DlsRequests_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), DlsRequests_.lastName));
            }
            if (criteria.getFamilyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFamilyName(), DlsRequests_.familyName));
            }
            if (criteria.getNationalID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalID(), DlsRequests_.nationalID));
            }
            if (criteria.getPassportNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportNo(), DlsRequests_.passportNo));
            }
            if (criteria.getPassportIssueCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportIssueCountry(), DlsRequests_.passportIssueCountry));
            }
            if (criteria.getLicenseType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenseType(), DlsRequests_.licenseType));
            }
            if (criteria.getTrafficUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrafficUnit(), DlsRequests_.trafficUnit));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthDate(), DlsRequests_.birthDate));
            }
        }
        return specification;
    }
}
