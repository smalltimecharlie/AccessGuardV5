package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OrganisationEndpoint.
 */
@Entity
@Table(name = "organisation_endpoint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrganisationEndpoint implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "organisation", nullable = false)
    private String organisation;

    @NotNull
    @Column(name = "connection_uri", nullable = false)
    private String connectionUri;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private LocationType locationType;

    @OneToOne
    @JoinColumn(unique = true)
    private EndpointType endpointType;

    @ManyToOne
    @JsonIgnoreProperties("organisationEndpoints")
    private AgreementOrganisation agreementOrganisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganisation() {
        return organisation;
    }

    public OrganisationEndpoint organisation(String organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getConnectionUri() {
        return connectionUri;
    }

    public OrganisationEndpoint connectionUri(String connectionUri) {
        this.connectionUri = connectionUri;
        return this;
    }

    public void setConnectionUri(String connectionUri) {
        this.connectionUri = connectionUri;
    }

    public Boolean isActive() {
        return active;
    }

    public OrganisationEndpoint active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public OrganisationEndpoint locationType(LocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public EndpointType getEndpointType() {
        return endpointType;
    }

    public OrganisationEndpoint endpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
        return this;
    }

    public void setEndpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
    }

    public AgreementOrganisation getAgreementOrganisation() {
        return agreementOrganisation;
    }

    public OrganisationEndpoint agreementOrganisation(AgreementOrganisation agreementOrganisation) {
        this.agreementOrganisation = agreementOrganisation;
        return this;
    }

    public void setAgreementOrganisation(AgreementOrganisation agreementOrganisation) {
        this.agreementOrganisation = agreementOrganisation;
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
        OrganisationEndpoint organisationEndpoint = (OrganisationEndpoint) o;
        if (organisationEndpoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisationEndpoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganisationEndpoint{" +
            "id=" + getId() +
            ", organisation='" + getOrganisation() + "'" +
            ", connectionUri='" + getConnectionUri() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
