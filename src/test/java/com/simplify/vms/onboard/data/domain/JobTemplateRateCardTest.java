package com.simplify.vms.onboard.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.vms.onboard.data.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobTemplateRateCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTemplateRateCard.class);
        JobTemplateRateCard jobTemplateRateCard1 = new JobTemplateRateCard();
        jobTemplateRateCard1.setId(1L);
        JobTemplateRateCard jobTemplateRateCard2 = new JobTemplateRateCard();
        jobTemplateRateCard2.setId(jobTemplateRateCard1.getId());
        assertThat(jobTemplateRateCard1).isEqualTo(jobTemplateRateCard2);
        jobTemplateRateCard2.setId(2L);
        assertThat(jobTemplateRateCard1).isNotEqualTo(jobTemplateRateCard2);
        jobTemplateRateCard1.setId(null);
        assertThat(jobTemplateRateCard1).isNotEqualTo(jobTemplateRateCard2);
    }
}
