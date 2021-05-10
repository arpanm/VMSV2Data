package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTemplate.class);
        JobTemplate jobTemplate1 = new JobTemplate();
        jobTemplate1.setId(1L);
        JobTemplate jobTemplate2 = new JobTemplate();
        jobTemplate2.setId(jobTemplate1.getId());
        assertThat(jobTemplate1).isEqualTo(jobTemplate2);
        jobTemplate2.setId(2L);
        assertThat(jobTemplate1).isNotEqualTo(jobTemplate2);
        jobTemplate1.setId(null);
        assertThat(jobTemplate1).isNotEqualTo(jobTemplate2);
    }
}
