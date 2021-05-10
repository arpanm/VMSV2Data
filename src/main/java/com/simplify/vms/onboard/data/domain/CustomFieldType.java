package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.vms.onboard.data.domain.enumeration.CustomFieldDataType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomFieldType.
 */
@Entity
@Table(name = "custom_field_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomFieldType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomFieldDataType type;

    @Column(name = "description")
    private String description;

    @Column(name = "label")
    private String label;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

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
    @JsonIgnoreProperties(value = { "hierarchy" }, allowSetters = true)
    private FoundationalDataType foundationalDataType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomFieldType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustomFieldType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomFieldDataType getType() {
        return this.type;
    }

    public CustomFieldType type(CustomFieldDataType type) {
        this.type = type;
        return this;
    }

    public void setType(CustomFieldDataType type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public CustomFieldType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return this.label;
    }

    public CustomFieldType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public CustomFieldType placeholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Boolean getIsMandatory() {
        return this.isMandatory;
    }

    public CustomFieldType isMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public CustomFieldType isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CustomFieldType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public CustomFieldType createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public CustomFieldType updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public CustomFieldType updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FoundationalDataType getFoundationalDataType() {
        return this.foundationalDataType;
    }

    public CustomFieldType foundationalDataType(FoundationalDataType foundationalDataType) {
        this.setFoundationalDataType(foundationalDataType);
        return this;
    }

    public void setFoundationalDataType(FoundationalDataType foundationalDataType) {
        this.foundationalDataType = foundationalDataType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomFieldType)) {
            return false;
        }
        return id != null && id.equals(((CustomFieldType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomFieldType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", label='" + getLabel() + "'" +
            ", placeholder='" + getPlaceholder() + "'" +
            ", isMandatory='" + getIsMandatory() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
