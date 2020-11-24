/**
 * 
 */
package com.javateam.demoSpringSecurity.domain;

import java.math.BigDecimal;
/**
 * @author javateam
 *
 */
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
 
@Entity
@Table(name="auth_user") // 참고) oracle에서는 "user" 이름으로 테이블명 생성 불가 !
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class User {
	
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO) // h2 db
    // private Long id; // h2 db
    @GeneratedValue(strategy = GenerationType.AUTO) // oracle
    @Column(name = "user_id", precision=19, scale=0) // oracle
    // @Column(name="user_id", nullable=false, columnDefinition = "number(19,0)")
    private BigDecimal userId; // oracle
    
    @Column(name = "user_name", unique = true) // unique
    private String username;
    
    @Column(name = "password")
    private String password;
  
    // @ManyToMany(cascade=CascadeType.ALL)
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
