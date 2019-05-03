package com.batook.ex2.data.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name="V$VERSION")
public class Banner {
    @Id
    @Column(name="banner")
    private String banner;

    public String getBanner() {
        return banner;
    }

    @Override
    public String toString() {
        return banner;
    }
}

