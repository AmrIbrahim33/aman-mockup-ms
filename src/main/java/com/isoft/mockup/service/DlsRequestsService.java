package com.isoft.mockup.service;

import com.isoft.mockup.service.dto.DlsRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.mockup.domain.DlsRequests}.
 */
public interface DlsRequestsService {

    /**
     * Save a dlsRequests.
     *
     * @param dlsRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    DlsRequestsDTO save(DlsRequestsDTO dlsRequestsDTO);

    /**
     * Get all the dlsRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DlsRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dlsRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DlsRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" dlsRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
