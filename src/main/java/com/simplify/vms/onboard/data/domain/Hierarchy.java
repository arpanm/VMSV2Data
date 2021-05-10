package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.vms.onboard.data.domain.enumeration.Country;
import com.simplify.vms.onboard.data.domain.enumeration.Currency;
import com.simplify.vms.onboard.data.domain.enumeration.Language;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hierarchy.
 */
@Entity
@Table(name = "hierarchy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hierarchy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "work_flow")
    private String workFlow;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_language")
    private Language preferredLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_currency")
    private Currency preferredCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_country")
    private Country primaryCountry;

    @Column(name = "primary_address")
    private String primaryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "secondary_country")
    private Country secondaryCountry;

    @Column(name = "secondary_address")
    private String secondaryAddress;

    @Column(name = "primary_contact_name")
    private String primaryContactName;

    @Column(name = "primary_contact_designation")
    private String primaryContactDesignation;

    @Column(name = "primary_contact_email")
    private String primaryContactEmail;

    @Column(name = "primary_contact_phone")
    private String primaryContactPhone;

    @Column(name = "secondary_contact_name")
    private String secondaryContactName;

    @Column(name = "secondary_contact_designation")
    private String secondaryContactDesignation;

    @Column(name = "secondary_contact_email")
    private String secondaryContactEmail;

    @Column(name = "secondary_contact_phone")
    private String secondaryContactPhone;

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
    private Program client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "client", "parent", "managers" }, allowSetters = true)
    private Hierarchy parent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_hierarchy__managers",
        joinColumns = @JoinColumn(name = "hierarchy_id"),
        inverseJoinColumns = @JoinColumn(name = "managers_id")
    )
    @JsonIgnoreProperties(value = { "manager", "location", "hierarchies", "foundationalData" }, allowSetters = true)
    private Set<ProgramUser> managers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hierarchy id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Hierarchy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkFlow() {
        return this.workFlow;
    }

    public Hierarchy workFlow(String workFlow) {
        this.workFlow = workFlow;
        return this;
    }

    public void setWorkFlow(String workFlow) {
        this.workFlow = workFlow;
    }

    public Language getPreferredLanguage() {
        return this.preferredLanguage;
    }

    public Hierarchy preferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
        return this;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public Currency getPreferredCurrency() {
        return this.preferredCurrency;
    }

    public Hierarchy preferredCurrency(Currency preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
        return this;
    }

    public void setPreferredCurrency(Currency preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    public Country getPrimaryCountry() {
        return this.primaryCountry;
    }

    public Hierarchy primaryCountry(Country primaryCountry) {
        this.primaryCountry = primaryCountry;
        return this;
    }

    public void setPrimaryCountry(Country primaryCountry) {
        this.primaryCountry = primaryCountry;
    }

    public String getPrimaryAddress() {
        return this.primaryAddress;
    }

    public Hierarchy primaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
        return this;
    }

    public void setPrimaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Country getSecondaryCountry() {
        return this.secondaryCountry;
    }

    public Hierarchy secondaryCountry(Country secondaryCountry) {
        this.secondaryCountry = secondaryCountry;
        return this;
    }

    public void setSecondaryCountry(Country secondaryCountry) {
        this.secondaryCountry = secondaryCountry;
    }

    public String getSecondaryAddress() {
        return this.secondaryAddress;
    }

    public Hierarchy secondaryAddress(String secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
        return this;
    }

    public void setSecondaryAddress(String secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

    public String getPrimaryContactName() {
        return this.primaryContactName;
    }

    public Hierarchy primaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
        return this;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactDesignation() {
        return this.primaryContactDesignation;
    }

    public Hierarchy primaryContactDesignation(String primaryContactDesignation) {
        this.primaryContactDesignation = primaryContactDesignation;
        return this;
    }

    public void setPrimaryContactDesignation(String primaryContactDesignation) {
        this.primaryContactDesignation = primaryContactDesignation;
    }

    public String getPrimaryContactEmail() {
        return this.primaryContactEmail;
    }

    public Hierarchy primaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
        return this;
    }

    public void setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
    }

    public String getPrimaryContactPhone() {
        return this.primaryContactPhone;
    }

    public Hierarchy primaryContactPhone(String primaryContactPhone) {
        this.primaryContactPhone = primaryContactPhone;
        return this;
    }

    public void setPrimaryContactPhone(String primaryContactPhone) {
        this.primaryContactPhone = primaryContactPhone;
    }

    public String getSecondaryContactName() {
        return this.secondaryContactName;
    }

    public Hierarchy secondaryContactName(String secondaryContactName) {
        this.secondaryContactName = secondaryContactName;
        return this;
    }

    public void setSecondaryContactName(String secondaryContactName) {
        this.secondaryContactName = secondaryContactName;
    }

    public String getSecondaryContactDesignation() {
        return this.secondaryContactDesignation;
    }

    public Hierarchy secondaryContactDesignation(String secondaryContactDesignation) {
        this.secondaryContactDesignation = secondaryContactDesignation;
        return this;
    }

    public void setSecondaryContactDesignation(String secondaryContactDesignation) {
        this.secondaryContactDesignation = secondaryContactDesignation;
    }

    public String getSecondaryContactEmail() {
        return this.secondaryContactEmail;
    }

    public Hierarchy secondaryContactEmail(String secondaryContactEmail) {
        this.secondaryContactEmail = secondaryContactEmail;
        return this;
    }

    public void setSecondaryContactEmail(String secondaryContactEmail) {
        this.secondaryContactEmail = secondaryContactEmail;
    }

    public String getSecondaryContactPhone() {
        return this.secondaryContactPhone;
    }

    public Hierarchy secondaryContactPhone(String secondaryContactPhone) {
        this.secondaryContactPhone = secondaryContactPhone;
        return this;
    }

    public void setSecondaryContactPhone(String secondaryContactPhone) {
        this.secondaryContactPhone = secondaryContactPhone;
    }

    public String getFileUploadPath() {
        return this.fileUploadPath;
    }

    public Hierarchy fileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
        return this;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Hierarchy isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Hierarchy createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Hierarchy createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Hierarchy updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Hierarchy updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Program getClient() {
        return this.client;
    }

    public Hierarchy client(Program program) {
        this.setClient(program);
        return this;
    }

    public void setClient(Program program) {
        this.client = program;
    }

    public Hierarchy getParent() {
        return this.parent;
    }

    public Hierarchy parent(Hierarchy hierarchy) {
        this.setParent(hierarchy);
        return this;
    }

    public void setParent(Hierarchy hierarchy) {
        this.parent = hierarchy;
    }

    public Set<ProgramUser> getManagers() {
        return this.managers;
    }

    public Hierarchy managers(Set<ProgramUser> programUsers) {
        this.setManagers(programUsers);
        return this;
    }

    public Hierarchy addManagers(ProgramUser programUser) {
        this.managers.add(programUser);
        programUser.getHierarchies().add(this);
        return this;
    }

    public Hierarchy removeManagers(ProgramUser programUser) {
        this.managers.remove(programUser);
        programUser.getHierarchies().remove(this);
        return this;
    }

    public void setManagers(Set<ProgramUser> programUsers) {
        this.managers = programUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hierarchy)) {
            return false;
        }
        return id != null && id.equals(((Hierarchy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hierarchy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", workFlow='" + getWorkFlow() + "'" +
            ", preferredLanguage='" + getPreferredLanguage() + "'" +
            ", preferredCurrency='" + getPreferredCurrency() + "'" +
            ", primaryCountry='" + getPrimaryCountry() + "'" +
            ", primaryAddress='" + getPrimaryAddress() + "'" +
            ", secondaryCountry='" + getSecondaryCountry() + "'" +
            ", secondaryAddress='" + getSecondaryAddress() + "'" +
            ", primaryContactName='" + getPrimaryContactName() + "'" +
            ", primaryContactDesignation='" + getPrimaryContactDesignation() + "'" +
            ", primaryContactEmail='" + getPrimaryContactEmail() + "'" +
            ", primaryContactPhone='" + getPrimaryContactPhone() + "'" +
            ", secondaryContactName='" + getSecondaryContactName() + "'" +
            ", secondaryContactDesignation='" + getSecondaryContactDesignation() + "'" +
            ", secondaryContactEmail='" + getSecondaryContactEmail() + "'" +
            ", secondaryContactPhone='" + getSecondaryContactPhone() + "'" +
            ", fileUploadPath='" + getFileUploadPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
