package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkLocation.class);
        WorkLocation workLocation1 = new WorkLocation();
        workLocation1.setId(1L);
        WorkLocation workLocation2 = new WorkLocation();
        workLocation2.setId(workLocation1.getId());
        assertThat(workLocation1).isEqualTo(workLocation2);
        workLocation2.setId(2L);
        assertThat(workLocation1).isNotEqualTo(workLocation2);
        workLocation1.setId(null);
        assertThat(workLocation1).isNotEqualTo(workLocation2);
    }
}
