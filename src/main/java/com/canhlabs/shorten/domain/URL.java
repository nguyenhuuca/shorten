package com.canhlabs.shorten.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "URL")
public class URL {
    @Id
    @Column(name = "hash")
    String hash;
    @Column(name = "original_url")
    String originalURL;
    @CreationTimestamp
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "expirationDate")
    Date expirationDate;

}
