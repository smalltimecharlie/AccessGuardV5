package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A AgreementOrganisation.
 */
@Entity
@Table(name = "agreement_organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgreementOrganisation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "source_system_agreement_id", nullable = false)
    private String sourceSystemAgreementId;

    @NotNull
    @Column(name = "agreement_type", nullable = false)
    private String agreementType;

    @Column(name = "agreement_name")
    private String agreementName;

    @Column(name = "organisation")
    private String organisation;

    @NotNull
    @Column(name = "agreement_status", nullable = false)
    private String agreementStatus;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("agreementOrganisations")
    private Agreement agreement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceSystemAgreementId() {
        return sourceSystemAgreementId;
    }

    public AgreementOrganisation sourceSystemAgreementId(String sourceSystemAgreementId) {
        this.sourceSystemAgreementId = sourceSystemAgreementId;
        return this;
    }

    public void setSourceSystemAgreementId(String sourceSystemAgreementId) {
        this.sourceSystemAgreementId = sourceSystemAgreementId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public AgreementOrganisation agreementType(String agreementType) {
        this.agreementType = agreementType;
        return this;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public AgreementOrganisation agreementName(String agreementName) {
        this.agreementName = agreementName;
        return this;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public AgreementOrganisation organisation(String organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getAgreementStatus() {
        return agreementStatus;
    }

    public AgreementOrganisation agreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
        return this;
    }

    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AgreementOrganisation createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public AgreementOrganisation agreement(Agreement agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AgreementOrganisation agreementOrganisation = (AgreementOrganisation) o;
        if (agreementOrganisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agreementOrganisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgreementOrganisation{" +
            "id=" + getId() +
            ", sourceSystemAgreementId='" + getSourceSystemAgreementId() + "'" +
            ", agreementType='" + getAgreementType() + "'" +
            ", agreementName='" + getAgreementName() + "'" +
            ", organisation='" + getOrganisation() + "'" +
            ", agreementStatus='" + getAgreementStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
