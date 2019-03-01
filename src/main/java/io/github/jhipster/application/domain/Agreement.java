package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_name")
    private String agreementName;

    @Column(name = "agreement_start_date")
    private Instant agreementStartDate;

    @Column(name = "agreement_end_date")
    private Instant agreementEndDate;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "source")
    private String source;

    @ManyToOne
    @JsonIgnoreProperties("agreements")
    private AgreementType agreenmentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public Agreement agreementName(String agreementName) {
        this.agreementName = agreementName;
        return this;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public Instant getAgreementStartDate() {
        return agreementStartDate;
    }

    public Agreement agreementStartDate(Instant agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
        return this;
    }

    public void setAgreementStartDate(Instant agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
    }

    public Instant getAgreementEndDate() {
        return agreementEndDate;
    }

    public Agreement agreementEndDate(Instant agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
        return this;
    }

    public void setAgreementEndDate(Instant agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    public String getSourceId() {
        return sourceId;
    }

    public Agreement sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public Agreement source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public AgreementType getAgreenmentType() {
        return agreenmentType;
    }

    public Agreement agreenmentType(AgreementType agreementType) {
        this.agreenmentType = agreementType;
        return this;
    }

    public void setAgreenmentType(AgreementType agreementType) {
        this.agreenmentType = agreementType;
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
        Agreement agreement = (Agreement) o;
        if (agreement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agreement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agreement{" +
            "id=" + getId() +
            ", agreementName='" + getAgreementName() + "'" +
            ", agreementStartDate='" + getAgreementStartDate() + "'" +
            ", agreementEndDate='" + getAgreementEndDate() + "'" +
            ", sourceId='" + getSourceId() + "'" +
            ", source='" + getSource() + "'" +
            "}";
    }
}
