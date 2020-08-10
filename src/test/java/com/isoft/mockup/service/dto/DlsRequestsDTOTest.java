package com.isoft.mockup.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.mockup.web.rest.TestUtil;

public class DlsRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DlsRequestsDTO.class);
        DlsRequestsDTO dlsRequestsDTO1 = new DlsRequestsDTO();
        dlsRequestsDTO1.setId(1L);
        DlsRequestsDTO dlsRequestsDTO2 = new DlsRequestsDTO();
        assertThat(dlsRequestsDTO1).isNotEqualTo(dlsRequestsDTO2);
        dlsRequestsDTO2.setId(dlsRequestsDTO1.getId());
        assertThat(dlsRequestsDTO1).isEqualTo(dlsRequestsDTO2);
        dlsRequestsDTO2.setId(2L);
        assertThat(dlsRequestsDTO1).isNotEqualTo(dlsRequestsDTO2);
        dlsRequestsDTO1.setId(null);
        assertThat(dlsRequestsDTO1).isNotEqualTo(dlsRequestsDTO2);
    }
}
