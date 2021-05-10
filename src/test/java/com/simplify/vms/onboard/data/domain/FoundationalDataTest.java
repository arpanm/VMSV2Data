package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoundationalDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoundationalData.class);
        FoundationalData foundationalData1 = new FoundationalData();
        foundationalData1.setId(1L);
        FoundationalData foundationalData2 = new FoundationalData();
        foundationalData2.setId(foundationalData1.getId());
        assertThat(foundationalData1).isEqualTo(foundationalData2);
        foundationalData2.setId(2L);
        assertThat(foundationalData1).isNotEqualTo(foundationalData2);
        foundationalData1.setId(null);
        assertThat(foundationalData1).isNotEqualTo(foundationalData2);
    }
}
