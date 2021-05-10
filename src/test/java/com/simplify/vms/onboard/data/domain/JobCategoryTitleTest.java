package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobCategoryTitleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobCategoryTitle.class);
        JobCategoryTitle jobCategoryTitle1 = new JobCategoryTitle();
        jobCategoryTitle1.setId(1L);
        JobCategoryTitle jobCategoryTitle2 = new JobCategoryTitle();
        jobCategoryTitle2.setId(jobCategoryTitle1.getId());
        assertThat(jobCategoryTitle1).isEqualTo(jobCategoryTitle2);
        jobCategoryTitle2.setId(2L);
        assertThat(jobCategoryTitle1).isNotEqualTo(jobCategoryTitle2);
        jobCategoryTitle1.setId(null);
        assertThat(jobCategoryTitle1).isNotEqualTo(jobCategoryTitle2);
    }
}
