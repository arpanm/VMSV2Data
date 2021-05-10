package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomFieldTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomFieldType.class);
        CustomFieldType customFieldType1 = new CustomFieldType();
        customFieldType1.setId(1L);
        CustomFieldType customFieldType2 = new CustomFieldType();
        customFieldType2.setId(customFieldType1.getId());
        assertThat(customFieldType1).isEqualTo(customFieldType2);
        customFieldType2.setId(2L);
        assertThat(customFieldType1).isNotEqualTo(customFieldType2);
        customFieldType1.setId(null);
        assertThat(customFieldType1).isNotEqualTo(customFieldType2);
    }
}
