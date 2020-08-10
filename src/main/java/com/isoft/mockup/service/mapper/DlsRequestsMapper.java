package com.isoft.mockup.service.mapper;

import com.isoft.mockup.domain.*;
import com.isoft.mockup.service.dto.DlsRequestsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DlsRequests} and its DTO {@link DlsRequestsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DlsRequestsMapper extends EntityMapper<DlsRequestsDTO, DlsRequests> {



    default DlsRequests fromId(Long id) {
        if (id == null) {
            return null;
        }
        DlsRequests dlsRequests = new DlsRequests();
        dlsRequests.setId(id);
        return dlsRequests;
    }
}
