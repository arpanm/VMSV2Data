package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramUser.class);
        ProgramUser programUser1 = new ProgramUser();
        programUser1.setId(1L);
        ProgramUser programUser2 = new ProgramUser();
        programUser2.setId(programUser1.getId());
        assertThat(programUser1).isEqualTo(programUser2);
        programUser2.setId(2L);
        assertThat(programUser1).isNotEqualTo(programUser2);
        programUser1.setId(null);
        assertThat(programUser1).isNotEqualTo(programUser2);
    }
}
