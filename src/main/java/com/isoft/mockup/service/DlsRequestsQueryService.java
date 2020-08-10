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
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionType(), DlsRequests_.transactionType));
            }
            if (criteria.getLicenseCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenseCategory(), DlsRequests_.licenseCategory));
            }
            if (criteria.getRequestNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestNo(), DlsRequests_.requestNo));
            }
            if (criteria.getExported() != null) {
                specification = specification.and(buildSpecification(criteria.getExported(), DlsRequests_.exported));
            }
            if (criteria.getFamilyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFamilyName(), DlsRequests_.familyName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), DlsRequests_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), DlsRequests_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), DlsRequests_.middleName));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), DlsRequests_.fullName));
            }
            if (criteria.getNationalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalId(), DlsRequests_.nationalId));
            }
            if (criteria.getPassportIssueCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportIssueCountry(), DlsRequests_.passportIssueCountry));
            }
            if (criteria.getPassportKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportKey(), DlsRequests_.passportKey));
            }
            if (criteria.getTrafficUnitCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrafficUnitCode(), DlsRequests_.trafficUnitCode));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), DlsRequests_.birthDate));
            }
            if (criteria.getLicenceExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenceExpiryDate(), DlsRequests_.licenceExpiryDate));
            }
            if (criteria.getLicenceTypeAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenceTypeAr(), DlsRequests_.licenceTypeAr));
            }
            if (criteria.getLicenceTypeEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenceTypeEn(), DlsRequests_.licenceTypeEn));
            }
            if (criteria.getLicenceStatusAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenceStatusAr(), DlsRequests_.licenceStatusAr));
            }
            if (criteria.getLicenceStatusEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLicenceStatusEn(), DlsRequests_.licenceStatusEn));
            }
            if (criteria.getLicenceStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenceStatus(), DlsRequests_.licenceStatus));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicantId(), DlsRequests_.applicantId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), DlsRequests_.userId));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCenterId(), DlsRequests_.centerId));
            }
        }
        return specification;
    }
}
