package com.example.rensourcevideorentals.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTLOGS")
public class RentRequests extends BaseEntity {

    private String customerName;

    private Double rentFee;

    @OneToOne(cascade = CascadeType.ALL)
    private Video videoRented;
}
