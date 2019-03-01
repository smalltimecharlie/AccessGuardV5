package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_url", nullable = false)
    private String productUrl;

    @OneToOne(mappedBy = "products")
    @JsonIgnore
    private ProductsUsers productsUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Products productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public Products productUrl(String productUrl) {
        this.productUrl = productUrl;
        return this;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public ProductsUsers getProductsUsers() {
        return productsUsers;
    }

    public Products productsUsers(ProductsUsers productsUsers) {
        this.productsUsers = productsUsers;
        return this;
    }

    public void setProductsUsers(ProductsUsers productsUsers) {
        this.productsUsers = productsUsers;
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
        Products products = (Products) o;
        if (products.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), products.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Products{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productUrl='" + getProductUrl() + "'" +
            "}";
    }
}
