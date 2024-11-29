//Role.java
package com.nt.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="rbac01Role")
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;  
    @ManyToMany(mappedBy = "role")
    @Column(nullable=true)
    private Set<Employee> employe;
}
