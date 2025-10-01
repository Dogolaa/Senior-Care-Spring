package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.seniorcare.shared.infrastructure.persistence.Auditable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
public class UserModel extends Auditable implements UserDetails {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "address_id")
    private UUID addressId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleModel role;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public UserModel() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"
                + role.getName().toUpperCase()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModel userModel)) return false;
        if (!super.equals(o)) return false;
        return isActive == userModel.isActive && Objects.equals(id, userModel.id) && Objects.equals(name, userModel.name) && Objects.equals(email, userModel.email) && Objects.equals(phone, userModel.phone) && Objects.equals(addressId, userModel.addressId) && Objects.equals(password, userModel.password) && Objects.equals(role, userModel.role) && Objects.equals(deletedAt, userModel.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, email, phone, isActive, addressId, password, role, deletedAt); // Inclui o hash da classe pai
    }
}