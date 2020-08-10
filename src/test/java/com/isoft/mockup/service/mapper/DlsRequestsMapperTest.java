package com.isoft.mockup.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DlsRequestsMapperTest {

    private DlsRequestsMapper dlsRequestsMapper;

    @BeforeEach
    public void setUp() {
        dlsRequestsMapper = new DlsRequestsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(dlsRequestsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dlsRequestsMapper.fromId(null)).isNull();
    }
}
