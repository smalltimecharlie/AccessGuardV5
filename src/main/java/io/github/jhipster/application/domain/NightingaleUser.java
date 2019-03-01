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
 * A NightingaleUser.
 */
@Entity
@Table(name = "nightingale_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NightingaleUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "cognito_username")
    private String cognitoUsername;

    @Lob
    @Column(name = "cognito_event")
    private byte[] cognitoEvent;

    @Column(name = "cognito_event_content_type")
    private String cognitoEventContentType;

    @Column(name = "cognito_signup_date")
    private Instant cognitoSignupDate;

    @ManyToOne
    @JsonIgnoreProperties("nightingaleUsers")
    private AgreementOrganisation agreementOrganisation;

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

    public NightingaleUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {
        return active;
    }

    public NightingaleUser active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCognitoUsername() {
        return cognitoUsername;
    }

    public NightingaleUser cognitoUsername(String cognitoUsername) {
        this.cognitoUsername = cognitoUsername;
        return this;
    }

    public void setCognitoUsername(String cognitoUsername) {
        this.cognitoUsername = cognitoUsername;
    }

    public byte[] getCognitoEvent() {
        return cognitoEvent;
    }

    public NightingaleUser cognitoEvent(byte[] cognitoEvent) {
        this.cognitoEvent = cognitoEvent;
        return this;
    }

    public void setCognitoEvent(byte[] cognitoEvent) {
        this.cognitoEvent = cognitoEvent;
    }

    public String getCognitoEventContentType() {
        return cognitoEventContentType;
    }

    public NightingaleUser cognitoEventContentType(String cognitoEventContentType) {
        this.cognitoEventContentType = cognitoEventContentType;
        return this;
    }

    public void setCognitoEventContentType(String cognitoEventContentType) {
        this.cognitoEventContentType = cognitoEventContentType;
    }

    public Instant getCognitoSignupDate() {
        return cognitoSignupDate;
    }

    public NightingaleUser cognitoSignupDate(Instant cognitoSignupDate) {
        this.cognitoSignupDate = cognitoSignupDate;
        return this;
    }

    public void setCognitoSignupDate(Instant cognitoSignupDate) {
        this.cognitoSignupDate = cognitoSignupDate;
    }

    public AgreementOrganisation getAgreementOrganisation() {
        return agreementOrganisation;
    }

    public NightingaleUser agreementOrganisation(AgreementOrganisation agreementOrganisation) {
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
        NightingaleUser nightingaleUser = (NightingaleUser) o;
        if (nightingaleUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nightingaleUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NightingaleUser{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", active='" + isActive() + "'" +
            ", cognitoUsername='" + getCognitoUsername() + "'" +
            ", cognitoEvent='" + getCognitoEvent() + "'" +
            ", cognitoEventContentType='" + getCognitoEventContentType() + "'" +
            ", cognitoSignupDate='" + getCognitoSignupDate() + "'" +
            "}";
    }
}
