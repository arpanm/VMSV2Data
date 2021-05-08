package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoundationalDataTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoundationalDataType.class);
        FoundationalDataType foundationalDataType1 = new FoundationalDataType();
        foundationalDataType1.setId(1L);
        FoundationalDataType foundationalDataType2 = new FoundationalDataType();
        foundationalDataType2.setId(foundationalDataType1.getId());
        assertThat(foundationalDataType1).isEqualTo(foundationalDataType2);
        foundationalDataType2.setId(2L);
        assertThat(foundationalDataType1).isNotEqualTo(foundationalDataType2);
        foundationalDataType1.setId(null);
        assertThat(foundationalDataType1).isNotEqualTo(foundationalDataType2);
    }
}
