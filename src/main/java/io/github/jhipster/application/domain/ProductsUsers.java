package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductsUsers.
 */
@Entity
@Table(name = "products_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductsUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private Products products;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ProductsUsers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {
        return active;
    }

    public ProductsUsers active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Products getProducts() {
        return products;
    }

    public ProductsUsers products(Products products) {
        this.products = products;
        return this;
    }

    public void setProducts(Products products) {
        this.products = products;
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
        ProductsUsers productsUsers = (ProductsUsers) o;
        if (productsUsers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productsUsers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductsUsers{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
