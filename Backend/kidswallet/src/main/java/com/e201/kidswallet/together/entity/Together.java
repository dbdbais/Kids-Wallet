package com.e201.kidswallet.together.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Together {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
