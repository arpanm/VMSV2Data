package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HierarchyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hierarchy.class);
        Hierarchy hierarchy1 = new Hierarchy();
        hierarchy1.setId(1L);
        Hierarchy hierarchy2 = new Hierarchy();
        hierarchy2.setId(hierarchy1.getId());
        assertThat(hierarchy1).isEqualTo(hierarchy2);
        hierarchy2.setId(2L);
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
        hierarchy1.setId(null);
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
    }
}
