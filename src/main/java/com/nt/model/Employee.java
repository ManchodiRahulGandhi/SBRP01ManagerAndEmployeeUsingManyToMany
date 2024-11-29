//Employee.java
package com.nt.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="rbac01employee")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    /*PERSIST: Propagates the persist operation from a parent to its child entities. 
      If the parent is saved, the child entities are also saved.*/
    
    //--@joinTable- represent create a table with below info
    @JoinTable(name = "rbac01employee_roles",
    joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
              )
    //@JsonManagedReference --- this for stack overfloe
    @Column(nullable=true)
    private Set<Role> role;
   

  
}
