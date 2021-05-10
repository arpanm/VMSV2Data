package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.vms.onboard.data.domain.enumeration.JobTemplateDistributionType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobTemplate.
 */
@Entity
@Table(name = "job_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_title")
    private String templateTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "job_level")
    private Integer jobLevel;

    @Column(name = "is_description_editable")
    private Boolean isDescriptionEditable;

    @Enumerated(EnumType.STRING)
    @Column(name = "distribution_type")
    private JobTemplateDistributionType distributionType;

    @Column(name = "distribution_limit")
    private Integer distributionLimit;

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
    private JobCategoryTitle category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "client", "parent", "managers" }, allowSetters = true)
    private Hierarchy hierarchy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public String getTemplateTitle() {
        return this.templateTitle;
    }

    public JobTemplate templateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
        return this;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public JobTemplate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getJobLevel() {
        return this.jobLevel;
    }

    public JobTemplate jobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
        return this;
    }

    public void setJobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
    }

    public Boolean getIsDescriptionEditable() {
        return this.isDescriptionEditable;
    }

    public JobTemplate isDescriptionEditable(Boolean isDescriptionEditable) {
        this.isDescriptionEditable = isDescriptionEditable;
        return this;
    }

    public void setIsDescriptionEditable(Boolean isDescriptionEditable) {
        this.isDescriptionEditable = isDescriptionEditable;
    }

    public JobTemplateDistributionType getDistributionType() {
        return this.distributionType;
    }

    public JobTemplate distributionType(JobTemplateDistributionType distributionType) {
        this.distributionType = distributionType;
        return this;
    }

    public void setDistributionType(JobTemplateDistributionType distributionType) {
        this.distributionType = distributionType;
    }

    public Integer getDistributionLimit() {
        return this.distributionLimit;
    }

    public JobTemplate distributionLimit(Integer distributionLimit) {
        this.distributionLimit = distributionLimit;
        return this;
    }

    public void setDistributionLimit(Integer distributionLimit) {
        this.distributionLimit = distributionLimit;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public JobTemplate isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public JobTemplate createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public JobTemplate createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public JobTemplate updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public JobTemplate updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JobCategoryTitle getCategory() {
        return this.category;
    }

    public JobTemplate category(JobCategoryTitle jobCategoryTitle) {
        this.setCategory(jobCategoryTitle);
        return this;
    }

    public void setCategory(JobCategoryTitle jobCategoryTitle) {
        this.category = jobCategoryTitle;
    }

    public Hierarchy getHierarchy() {
        return this.hierarchy;
    }

    public JobTemplate hierarchy(Hierarchy hierarchy) {
        this.setHierarchy(hierarchy);
        return this;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobTemplate)) {
            return false;
        }
        return id != null && id.equals(((JobTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobTemplate{" +
            "id=" + getId() +
            ", templateTitle='" + getTemplateTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", jobLevel=" + getJobLevel() +
            ", isDescriptionEditable='" + getIsDescriptionEditable() + "'" +
            ", distributionType='" + getDistributionType() + "'" +
            ", distributionLimit=" + getDistributionLimit() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
