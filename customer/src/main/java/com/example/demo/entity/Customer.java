package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EXT_CUSTOMER_ID")
    String extCustomerId;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Address> addresses = new LinkedHashSet<>();

    @ElementCollection
    @Column(name = "products_id")
    @CollectionTable(name = "customer_product", joinColumns = @JoinColumn(name = "customer_id"))
    private Set<String> products = new HashSet<>();

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddresses(Set<Address> addresses) {
        for (Address address : addresses) {
            if (this.addresses.contains(address)) {
                this.addresses.remove(address);
                this.addresses.add(address);
            } else {
                this.addresses.add(address);
            }
            address.setCustomer(this);
        }
    }

    public void addProducts(Set<String> products) {
        for (String product : products) {
            if (this.products.contains(product)) {
                this.products.remove(product);
                this.products.add(product);
            } else {
                this.products.add(product);
            }
            //product.setCustomer(this);
        }
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
    }

    public Customer() {
    }

    public Customer(String extCustomerId, String firstName, String lastName) {
        this.extCustomerId = extCustomerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Long id, String extCustomerId, String firstName, String lastName, Set<Address> addresses) {
        this.id = id;
        this.extCustomerId = extCustomerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtCustomerId() {
        return extCustomerId;
    }

    public void setExtCustomerId(String extCustomerId) {
        this.extCustomerId = extCustomerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getProducts() {
        return products;
    }

    public void setProducts(Set<String> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "extCustomerId = " + extCustomerId + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ")";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}