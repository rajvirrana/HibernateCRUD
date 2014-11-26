package com.hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CERTIFICATE")
public class Certificate{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
  
    @Size(max = 30)
    @Column(name = "certificate_name")
    private String certificateName;

    public Certificate() {
    
    }

    public Certificate(String certificate) {
        this.certificateName = certificate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }
}