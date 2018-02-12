package com.unico.rest.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by baybora on 2/10/18.
 */

@Entity
@Table(name = "PARAMETER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARAMETER_1")
    private Integer parameter1;

    @Column(name = "PARAMETER_2")
    private Integer parameter2;

    @Column(name = "INSERT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date insertTimestamp=new Date();

}
