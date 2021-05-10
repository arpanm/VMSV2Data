package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FoundationalDataType.
 */
@Entity
@Table(name = "foundational_data_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FoundationalDataType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "required_in_hierarchy")
    private Boolean requiredInHierarchy;

    @Column(name = "required_in_jobs")
    private Boolean requiredInJobs;

    @Column(name = "required_in_sow")
    private Boolean requiredInSow;

    @Column(name = "file_upload_path")
    private String fileUploadPath;

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
    @JsonIgnoreProperties(value = { "client", "parent", "managers" }, allowSetters = true)
    private Hierarchy hierarchy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoundationalDataType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public FoundationalDataType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public FoundationalDataType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequiredInHierarchy() {
        return this.requiredInHierarchy;
    }

    public FoundationalDataType requiredInHierarchy(Boolean requiredInHierarchy) {
        this.requiredInHierarchy = requiredInHierarchy;
        return this;
    }

    public void setRequiredInHierarchy(Boolean requiredInHierarchy) {
        this.requiredInHierarchy = requiredInHierarchy;
    }

    public Boolean getRequiredInJobs() {
        return this.requiredInJobs;
    }

    public FoundationalDataType requiredInJobs(Boolean requiredInJobs) {
        this.requiredInJobs = requiredInJobs;
        return this;
    }

    public void setRequiredInJobs(Boolean requiredInJobs) {
        this.requiredInJobs = requiredInJobs;
    }

    public Boolean getRequiredInSow() {
        return this.requiredInSow;
    }

    public FoundationalDataType requiredInSow(Boolean requiredInSow) {
        this.requiredInSow = requiredInSow;
        return this;
    }

    public void setRequiredInSow(Boolean requiredInSow) {
        this.requiredInSow = requiredInSow;
    }

    public String getFileUploadPath() {
        return this.fileUploadPath;
    }

    public FoundationalDataType fileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
        return this;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FoundationalDataType isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FoundationalDataType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public FoundationalDataType createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FoundationalDataType updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public FoundationalDataType updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Hierarchy getHierarchy() {
        return this.hierarchy;
    }

    public FoundationalDataType hierarchy(Hierarchy hierarchy) {
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
        if (!(o instanceof FoundationalDataType)) {
            return false;
        }
        return id != null && id.equals(((FoundationalDataType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoundationalDataType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredInHierarchy='" + getRequiredInHierarchy() + "'" +
            ", requiredInJobs='" + getRequiredInJobs() + "'" +
            ", requiredInSow='" + getRequiredInSow() + "'" +
            ", fileUploadPath='" + getFileUploadPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
