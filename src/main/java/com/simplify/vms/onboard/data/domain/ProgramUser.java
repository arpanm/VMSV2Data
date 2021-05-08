package com.simplify.vms.onboard.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.vms.onboard.data.domain.enumeration.Role;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProgramUser.
 */
@Entity
@Table(name = "program_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProgramUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "client_designation")
    private String clientDesignation;

    @Enumerated(EnumType.STRING)
    @Column(name = "simplify_role")
    private Role simplifyRole;

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
    @JsonIgnoreProperties(value = { "manager", "location", "hierarchies", "foundationalData" }, allowSetters = true)
    private ProgramUser manager;

    @ManyToOne
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private WorkLocation location;

    @ManyToMany(mappedBy = "managers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "parent", "managers" }, allowSetters = true)
    private Set<Hierarchy> hierarchies = new HashSet<>();

    @ManyToMany(mappedBy = "managers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "foundationalDataType", "managers" }, allowSetters = true)
    private Set<FoundationalData> foundationalData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProgramUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ProgramUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ProgramUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public ProgramUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public ProgramUser sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public ProgramUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getClientDesignation() {
        return this.clientDesignation;
    }

    public ProgramUser clientDesignation(String clientDesignation) {
        this.clientDesignation = clientDesignation;
        return this;
    }

    public void setClientDesignation(String clientDesignation) {
        this.clientDesignation = clientDesignation;
    }

    public Role getSimplifyRole() {
        return this.simplifyRole;
    }

    public ProgramUser simplifyRole(Role simplifyRole) {
        this.simplifyRole = simplifyRole;
        return this;
    }

    public void setSimplifyRole(Role simplifyRole) {
        this.simplifyRole = simplifyRole;
    }

    public String getFileUploadPath() {
        return this.fileUploadPath;
    }

    public ProgramUser fileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
        return this;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ProgramUser isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProgramUser createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public ProgramUser createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ProgramUser updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public ProgramUser updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProgramUser getManager() {
        return this.manager;
    }

    public ProgramUser manager(ProgramUser programUser) {
        this.setManager(programUser);
        return this;
    }

    public void setManager(ProgramUser programUser) {
        this.manager = programUser;
    }

    public WorkLocation getLocation() {
        return this.location;
    }

    public ProgramUser location(WorkLocation workLocation) {
        this.setLocation(workLocation);
        return this;
    }

    public void setLocation(WorkLocation workLocation) {
        this.location = workLocation;
    }

    public Set<Hierarchy> getHierarchies() {
        return this.hierarchies;
    }

    public ProgramUser hierarchies(Set<Hierarchy> hierarchies) {
        this.setHierarchies(hierarchies);
        return this;
    }

    public ProgramUser addHierarchy(Hierarchy hierarchy) {
        this.hierarchies.add(hierarchy);
        hierarchy.getManagers().add(this);
        return this;
    }

    public ProgramUser removeHierarchy(Hierarchy hierarchy) {
        this.hierarchies.remove(hierarchy);
        hierarchy.getManagers().remove(this);
        return this;
    }

    public void setHierarchies(Set<Hierarchy> hierarchies) {
        if (this.hierarchies != null) {
            this.hierarchies.forEach(i -> i.removeManagers(this));
        }
        if (hierarchies != null) {
            hierarchies.forEach(i -> i.addManagers(this));
        }
        this.hierarchies = hierarchies;
    }

    public Set<FoundationalData> getFoundationalData() {
        return this.foundationalData;
    }

    public ProgramUser foundationalData(Set<FoundationalData> foundationalData) {
        this.setFoundationalData(foundationalData);
        return this;
    }

    public ProgramUser addFoundationalData(FoundationalData foundationalData) {
        this.foundationalData.add(foundationalData);
        foundationalData.getManagers().add(this);
        return this;
    }

    public ProgramUser removeFoundationalData(FoundationalData foundationalData) {
        this.foundationalData.remove(foundationalData);
        foundationalData.getManagers().remove(this);
        return this;
    }

    public void setFoundationalData(Set<FoundationalData> foundationalData) {
        if (this.foundationalData != null) {
            this.foundationalData.forEach(i -> i.removeManagers(this));
        }
        if (foundationalData != null) {
            foundationalData.forEach(i -> i.addManagers(this));
        }
        this.foundationalData = foundationalData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramUser)) {
            return false;
        }
        return id != null && id.equals(((ProgramUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", sourceId='" + getSourceId() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", clientDesignation='" + getClientDesignation() + "'" +
            ", simplifyRole='" + getSimplifyRole() + "'" +
            ", fileUploadPath='" + getFileUploadPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
