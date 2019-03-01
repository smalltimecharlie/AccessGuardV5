package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EndpointType.
 */
@Entity
@Table(name = "endpoint_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EndpointType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endpoint_type_name")
    private String endpointTypeName;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpointTypeName() {
        return endpointTypeName;
    }

    public EndpointType endpointTypeName(String endpointTypeName) {
        this.endpointTypeName = endpointTypeName;
        return this;
    }

    public void setEndpointTypeName(String endpointTypeName) {
        this.endpointTypeName = endpointTypeName;
    }

    public Boolean isActive() {
        return active;
    }

    public EndpointType active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        EndpointType endpointType = (EndpointType) o;
        if (endpointType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endpointType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndpointType{" +
            "id=" + getId() +
            ", endpointTypeName='" + getEndpointTypeName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
