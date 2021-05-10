package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.vms.onboard.data.domain.enumeration.Currency;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobTemplateRateCard.
 */
@Entity
@Table(name = "job_template_rate_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobTemplateRateCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "hourly_min")
    private Integer hourlyMin;

    @Column(name = "hourly_max")
    private Integer hourlyMax;

    @Column(name = "daily_min")
    private Integer dailyMin;

    @Column(name = "daily_max")
    private Integer dailyMax;

    @Column(name = "weekly_min")
    private Integer weeklyMin;

    @Column(name = "weekly_max")
    private Integer weeklyMax;

    @Column(name = "monthly_min")
    private Integer monthlyMin;

    @Column(name = "monthly_max")
    private Integer monthlyMax;

    @Column(name = "yearly_min")
    private Integer yearlyMin;

    @Column(name = "yearly_max")
    private Integer yearlyMax;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "category", "hierarchy" }, allowSetters = true)
    private JobTemplate template;

    @ManyToOne
    private JobCategoryTitle category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobTemplateRateCard id(Long id) {
        this.id = id;
        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public JobTemplateRateCard currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getHourlyMin() {
        return this.hourlyMin;
    }

    public JobTemplateRateCard hourlyMin(Integer hourlyMin) {
        this.hourlyMin = hourlyMin;
        return this;
    }

    public void setHourlyMin(Integer hourlyMin) {
        this.hourlyMin = hourlyMin;
    }

    public Integer getHourlyMax() {
        return this.hourlyMax;
    }

    public JobTemplateRateCard hourlyMax(Integer hourlyMax) {
        this.hourlyMax = hourlyMax;
        return this;
    }

    public void setHourlyMax(Integer hourlyMax) {
        this.hourlyMax = hourlyMax;
    }

    public Integer getDailyMin() {
        return this.dailyMin;
    }

    public JobTemplateRateCard dailyMin(Integer dailyMin) {
        this.dailyMin = dailyMin;
        return this;
    }

    public void setDailyMin(Integer dailyMin) {
        this.dailyMin = dailyMin;
    }

    public Integer getDailyMax() {
        return this.dailyMax;
    }

    public JobTemplateRateCard dailyMax(Integer dailyMax) {
        this.dailyMax = dailyMax;
        return this;
    }

    public void setDailyMax(Integer dailyMax) {
        this.dailyMax = dailyMax;
    }

    public Integer getWeeklyMin() {
        return this.weeklyMin;
    }

    public JobTemplateRateCard weeklyMin(Integer weeklyMin) {
        this.weeklyMin = weeklyMin;
        return this;
    }

    public void setWeeklyMin(Integer weeklyMin) {
        this.weeklyMin = weeklyMin;
    }

    public Integer getWeeklyMax() {
        return this.weeklyMax;
    }

    public JobTemplateRateCard weeklyMax(Integer weeklyMax) {
        this.weeklyMax = weeklyMax;
        return this;
    }

    public void setWeeklyMax(Integer weeklyMax) {
        this.weeklyMax = weeklyMax;
    }

    public Integer getMonthlyMin() {
        return this.monthlyMin;
    }

    public JobTemplateRateCard monthlyMin(Integer monthlyMin) {
        this.monthlyMin = monthlyMin;
        return this;
    }

    public void setMonthlyMin(Integer monthlyMin) {
        this.monthlyMin = monthlyMin;
    }

    public Integer getMonthlyMax() {
        return this.monthlyMax;
    }

    public JobTemplateRateCard monthlyMax(Integer monthlyMax) {
        this.monthlyMax = monthlyMax;
        return this;
    }

    public void setMonthlyMax(Integer monthlyMax) {
        this.monthlyMax = monthlyMax;
    }

    public Integer getYearlyMin() {
        return this.yearlyMin;
    }

    public JobTemplateRateCard yearlyMin(Integer yearlyMin) {
        this.yearlyMin = yearlyMin;
        return this;
    }

    public void setYearlyMin(Integer yearlyMin) {
        this.yearlyMin = yearlyMin;
    }

    public Integer getYearlyMax() {
        return this.yearlyMax;
    }

    public JobTemplateRateCard yearlyMax(Integer yearlyMax) {
        this.yearlyMax = yearlyMax;
        return this;
    }

    public void setYearlyMax(Integer yearlyMax) {
        this.yearlyMax = yearlyMax;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public JobTemplateRateCard isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public JobTemplateRateCard createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public JobTemplateRateCard createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public JobTemplateRateCard updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public JobTemplateRateCard updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JobTemplate getTemplate() {
        return this.template;
    }

    public JobTemplateRateCard template(JobTemplate jobTemplate) {
        this.setTemplate(jobTemplate);
        return this;
    }

    public void setTemplate(JobTemplate jobTemplate) {
        this.template = jobTemplate;
    }

    public JobCategoryTitle getCategory() {
        return this.category;
    }

    public JobTemplateRateCard category(JobCategoryTitle jobCategoryTitle) {
        this.setCategory(jobCategoryTitle);
        return this;
    }

    public void setCategory(JobCategoryTitle jobCategoryTitle) {
        this.category = jobCategoryTitle;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobTemplateRateCard)) {
            return false;
        }
        return id != null && id.equals(((JobTemplateRateCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobTemplateRateCard{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", hourlyMin=" + getHourlyMin() +
            ", hourlyMax=" + getHourlyMax() +
            ", dailyMin=" + getDailyMin() +
            ", dailyMax=" + getDailyMax() +
            ", weeklyMin=" + getWeeklyMin() +
            ", weeklyMax=" + getWeeklyMax() +
            ", monthlyMin=" + getMonthlyMin() +
            ", monthlyMax=" + getMonthlyMax() +
            ", yearlyMin=" + getYearlyMin() +
            ", yearlyMax=" + getYearlyMax() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
