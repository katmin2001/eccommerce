package com.vti.ecommerce.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_payment")
public class UserPayment extends BaseEntity{
    @Column(name = "provider")
    private String provider;
    @Column(name = "number_card")
    private String number_card;
    @Column(name = "user_id")
    private Long user_id;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getNumber_card() {
        return number_card;
    }

    public void setNumber_card(String number_card) {
        this.number_card = number_card;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
