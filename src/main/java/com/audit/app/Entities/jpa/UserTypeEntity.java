/*
 * Created on 13 Jul 2017 ( Time 13:22:23 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.audit.app.Entities.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "user_type"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="user_type", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="UserTypeEntity.countAll", query="SELECT COUNT(x) FROM UserTypeEntity x" )
} )
public class UserTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="type_name", length=45)
    private String     typeName     ;

    @Column(name="type_alias", length=15)
    private String     typeAlias    ;

    @Column(name="type_description", length=45)
    private String     typeDescription ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable=false)
    private Date       createdDate  ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="userType", targetEntity=UsersEntity.class)
    private List<UsersEntity> listOfUsers ;

    @OneToMany(mappedBy="userType", targetEntity=UserTypeMapEntity.class)
    private List<UserTypeMapEntity> listOfUserTypeMap;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public UserTypeEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : type_name ( VARCHAR ) 
    public void setTypeName( String typeName ) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return this.typeName;
    }

    //--- DATABASE MAPPING : type_alias ( VARCHAR ) 
    public void setTypeAlias( String typeAlias ) {
        this.typeAlias = typeAlias;
    }
    public String getTypeAlias() {
        return this.typeAlias;
    }

    //--- DATABASE MAPPING : type_description ( VARCHAR ) 
    public void setTypeDescription( String typeDescription ) {
        this.typeDescription = typeDescription;
    }
    public String getTypeDescription() {
        return this.typeDescription;
    }

    //--- DATABASE MAPPING : created_date ( TIMESTAMP ) 
    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfUsers( List<UsersEntity> listOfUsers ) {
        this.listOfUsers = listOfUsers;
    }
    public List<UsersEntity> getListOfUsers() {
        return this.listOfUsers;
    }

    public void setListOfUserTypeMap( List<UserTypeMapEntity> listOfUserTypeMap ) {
        this.listOfUserTypeMap = listOfUserTypeMap;
    }
    public List<UserTypeMapEntity> getListOfUserTypeMap() {
        return this.listOfUserTypeMap;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(typeName);
        sb.append("|");
        sb.append(typeAlias);
        sb.append("|");
        sb.append(typeDescription);
        sb.append("|");
        sb.append(createdDate);
        return sb.toString(); 
    } 

}
