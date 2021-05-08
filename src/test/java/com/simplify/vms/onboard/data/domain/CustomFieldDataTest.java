package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomFieldDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomFieldData.class);
        CustomFieldData customFieldData1 = new CustomFieldData();
        customFieldData1.setId(1L);
        CustomFieldData customFieldData2 = new CustomFieldData();
        customFieldData2.setId(customFieldData1.getId());
        assertThat(customFieldData1).isEqualTo(customFieldData2);
        customFieldData2.setId(2L);
        assertThat(customFieldData1).isNotEqualTo(customFieldData2);
        customFieldData1.setId(null);
        assertThat(customFieldData1).isNotEqualTo(customFieldData2);
    }
}
