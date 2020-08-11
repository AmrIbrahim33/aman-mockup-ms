package com.isoft.mockup.web.rest;

import com.isoft.mockup.service.DlsRequestsService;
import com.isoft.mockup.service.dto.InquireExamEligibilityResponse;
import com.isoft.mockup.web.rest.errors.BadRequestAlertException;
import com.isoft.mockup.service.dto.DlsRequestsDTO;
import com.isoft.mockup.service.dto.DlsRequestsCriteria;
import com.isoft.mockup.service.DlsRequestsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.mockup.domain.DlsRequests}.
 */
@RestController
@RequestMapping("/api")
public class DlsRequestsResource {

    private final Logger log = LoggerFactory.getLogger(DlsRequestsResource.class);

    private static final String ENTITY_NAME = "amanMockupMsDlsRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DlsRequestsService dlsRequestsService;

    private final DlsRequestsQueryService dlsRequestsQueryService;

    public DlsRequestsResource(DlsRequestsService dlsRequestsService, DlsRequestsQueryService dlsRequestsQueryService) {
        this.dlsRequestsService = dlsRequestsService;
        this.dlsRequestsQueryService = dlsRequestsQueryService;
    }

    /**
     * {@code POST  /dls-requests} : Create a new dlsRequests.
     *
     * @param dlsRequestsDTO the dlsRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dlsRequestsDTO, or with status {@code 400 (Bad Request)} if the dlsRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dls-requests")
    public ResponseEntity<DlsRequestsDTO> createDlsRequests(@RequestBody DlsRequestsDTO dlsRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save DlsRequests : {}", dlsRequestsDTO);
        if (dlsRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dlsRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DlsRequestsDTO result = dlsRequestsService.save(dlsRequestsDTO);
        return ResponseEntity.created(new URI("/api/dls-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dls-requests} : Updates an existing dlsRequests.
     *
     * @param dlsRequestsDTO the dlsRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dlsRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the dlsRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dlsRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dls-requests")
    public ResponseEntity<DlsRequestsDTO> updateDlsRequests(@RequestBody DlsRequestsDTO dlsRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update DlsRequests : {}", dlsRequestsDTO);
        if (dlsRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DlsRequestsDTO result = dlsRequestsService.save(dlsRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dlsRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dls-requests} : get all the dlsRequests.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dlsRequests in body.
     */
    @GetMapping("/dls-requests")
    public ResponseEntity<List<DlsRequestsDTO>> getAllDlsRequests(DlsRequestsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DlsRequests by criteria: {}", criteria);
        Page<DlsRequestsDTO> page = dlsRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /dls-requests/count} : count all the dlsRequests.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/dls-requests/count")
    public ResponseEntity<Long> countDlsRequests(DlsRequestsCriteria criteria) {
        log.debug("REST request to count DlsRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(dlsRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dls-requests/:id} : get the "id" dlsRequests.
     *
     * @param id the id of the dlsRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dlsRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dls-requests/{id}")
    public ResponseEntity<DlsRequestsDTO> getDlsRequests(@PathVariable Long id) {
        log.debug("REST request to get DlsRequests : {}", id);
        Optional<DlsRequestsDTO> dlsRequestsDTO = dlsRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dlsRequestsDTO);
    }

    /**
     * {@code DELETE  /dls-requests/:id} : delete the "id" dlsRequests.
     *
     * @param id the id of the dlsRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dls-requests/{id}")
    public ResponseEntity<Void> deleteDlsRequests(@PathVariable Long id) {
        log.debug("REST request to delete DlsRequests : {}", id);
        dlsRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/inquiry/{requestId}")
    public ResponseEntity<InquireExamEligibilityResponse> Inquiry(@PathVariable Long requestId) {
        log.debug("REST request to get InquireExamEligibilityResponse : {}", requestId);
        InquireExamEligibilityResponse response = dlsRequestsService.inquiry(requestId);
        return ResponseEntity.ok(response);
    }
}
