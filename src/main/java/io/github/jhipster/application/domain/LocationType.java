package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LocationType.
 */
@Entity
@Table(name = "location_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocationType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_type_name")
    private String locationTypeName;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationTypeName() {
        return locationTypeName;
    }

    public LocationType locationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
        return this;
    }

    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
    }

    public Boolean isActive() {
        return active;
    }

    public LocationType active(Boolean active) {
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
        LocationType locationType = (LocationType) o;
        if (locationType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationType{" +
            "id=" + getId() +
            ", locationTypeName='" + getLocationTypeName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
