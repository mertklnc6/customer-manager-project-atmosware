package com.turkcell.customer_service.entities.concretes;

import com.turkcell.customer_service.core.entities.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@SQLRestriction(value = "deleted_date is null")
public class Customer extends BaseEntity<UUID> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "citizen_number", nullable = false, unique = true, updatable = false, length = 11)
    private String citizenNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @Override
    public void onCreate() {
        setCreatedDate(LocalDateTime.now());
        super.onCreate();
    }

    @Override
    public void onUpdate() {
        setUpdatedDate(LocalDateTime.now());
        super.onUpdate();
    }
}
