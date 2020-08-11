package com.isoft.mockup.repository;

import com.isoft.mockup.domain.DlsRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DlsRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DlsRequestsRepository extends JpaRepository<DlsRequests, Long>, JpaSpecificationExecutor<DlsRequests> {

    DlsRequests findByRequestID(Long requestId);

}
