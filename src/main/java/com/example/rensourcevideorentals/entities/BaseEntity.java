package com.example.rensourcevideorentals.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

        private static final long serialVersionUID = 1L;

        @Setter
        @Getter
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        protected Long id;

}
