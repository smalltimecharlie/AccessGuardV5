package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A AgreementType.
 */
@Entity
@Table(name = "agreement_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgreementType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_url")
    private String agreementUrl;

    @Column(name = "agreement_type_name")
    private String agreementTypeName;

    @Column(name = "agreement_type_start_date")
    private Instant agreementTypeStartDate;

    @Column(name = "agreement_type_end_date")
    private Instant agreementTypeEndDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public AgreementType agreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
        return this;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public String getAgreementTypeName() {
        return agreementTypeName;
    }

    public AgreementType agreementTypeName(String agreementTypeName) {
        this.agreementTypeName = agreementTypeName;
        return this;
    }

    public void setAgreementTypeName(String agreementTypeName) {
        this.agreementTypeName = agreementTypeName;
    }

    public Instant getAgreementTypeStartDate() {
        return agreementTypeStartDate;
    }

    public AgreementType agreementTypeStartDate(Instant agreementTypeStartDate) {
        this.agreementTypeStartDate = agreementTypeStartDate;
        return this;
    }

    public void setAgreementTypeStartDate(Instant agreementTypeStartDate) {
        this.agreementTypeStartDate = agreementTypeStartDate;
    }

    public Instant getAgreementTypeEndDate() {
        return agreementTypeEndDate;
    }

    public AgreementType agreementTypeEndDate(Instant agreementTypeEndDate) {
        this.agreementTypeEndDate = agreementTypeEndDate;
        return this;
    }

    public void setAgreementTypeEndDate(Instant agreementTypeEndDate) {
        this.agreementTypeEndDate = agreementTypeEndDate;
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
        AgreementType agreementType = (AgreementType) o;
        if (agreementType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agreementType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgreementType{" +
            "id=" + getId() +
            ", agreementUrl='" + getAgreementUrl() + "'" +
            ", agreementTypeName='" + getAgreementTypeName() + "'" +
            ", agreementTypeStartDate='" + getAgreementTypeStartDate() + "'" +
            ", agreementTypeEndDate='" + getAgreementTypeEndDate() + "'" +
            "}";
    }
}
