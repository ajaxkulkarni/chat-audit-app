/*
 * Created on 13 Jul 2017 ( Time 13:22:22 )
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
 * Persistent class for entity stored in table "dept_location"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="dept_location", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="DeptLocationEntity.countAll", query="SELECT COUNT(x) FROM DeptLocationEntity x" )
} )
public class DeptLocationEntity implements Serializable {

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
    @Column(name="address", length=255)
    private String     address      ;

    @Column(name="primary_contact", length=50)
    private String     primaryContact ;

    @Column(name="secondary_contact", length=50)
    private String     secondaryContact ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable=false)
    private Date       createdDate  ;

    @Column(name="status", length=15)
    private String     status       ;

	// "deptId" (column "dept_id") is not defined by itself because used as FK in a link 
	// "locationId" (column "location_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="deptLocation", targetEntity=DeptSkillsMapEntity.class)
    private List<DeptSkillsMapEntity> listOfDeptSkillsMap;

    @OneToMany(mappedBy="deptLocation", targetEntity=DeptLocHoursEntity.class)
    private List<DeptLocHoursEntity> listOfDeptLocHours;

    @OneToMany(mappedBy="deptLocation", targetEntity=DeptLocUsersEntity.class)
    private List<DeptLocUsersEntity> listOfDeptLocUsers;

    @OneToMany(mappedBy="deptLocation", targetEntity=AttributesMasterEntity.class)
    private List<AttributesMasterEntity> listOfAttributesMaster;

    @ManyToOne
    @JoinColumn(name="dept_id", referencedColumnName="id")
    private DepartmentsEntity departments ;

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName="id")
    private LocationsEntity locations   ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public DeptLocationEntity() {
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
    //--- DATABASE MAPPING : address ( VARCHAR ) 
    public void setAddress( String address ) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }

    //--- DATABASE MAPPING : primary_contact ( VARCHAR ) 
    public void setPrimaryContact( String primaryContact ) {
        this.primaryContact = primaryContact;
    }
    public String getPrimaryContact() {
        return this.primaryContact;
    }

    //--- DATABASE MAPPING : secondary_contact ( VARCHAR ) 
    public void setSecondaryContact( String secondaryContact ) {
        this.secondaryContact = secondaryContact;
    }
    public String getSecondaryContact() {
        return this.secondaryContact;
    }

    //--- DATABASE MAPPING : created_date ( TIMESTAMP ) 
    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }

    //--- DATABASE MAPPING : status ( VARCHAR ) 
    public void setStatus( String status ) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfDeptSkillsMap( List<DeptSkillsMapEntity> listOfDeptSkillsMap ) {
        this.listOfDeptSkillsMap = listOfDeptSkillsMap;
    }
    public List<DeptSkillsMapEntity> getListOfDeptSkillsMap() {
        return this.listOfDeptSkillsMap;
    }

    public void setListOfDeptLocHours( List<DeptLocHoursEntity> listOfDeptLocHours ) {
        this.listOfDeptLocHours = listOfDeptLocHours;
    }
    public List<DeptLocHoursEntity> getListOfDeptLocHours() {
        return this.listOfDeptLocHours;
    }

    public void setListOfDeptLocUsers( List<DeptLocUsersEntity> listOfDeptLocUsers ) {
        this.listOfDeptLocUsers = listOfDeptLocUsers;
    }
    public List<DeptLocUsersEntity> getListOfDeptLocUsers() {
        return this.listOfDeptLocUsers;
    }

    public void setListOfAttributesMaster( List<AttributesMasterEntity> listOfAttributesMaster ) {
        this.listOfAttributesMaster = listOfAttributesMaster;
    }
    public List<AttributesMasterEntity> getListOfAttributesMaster() {
        return this.listOfAttributesMaster;
    }

    public void setDepartments( DepartmentsEntity departments ) {
        this.departments = departments;
    }
    public DepartmentsEntity getDepartments() {
        return this.departments;
    }

    public void setLocations( LocationsEntity locations ) {
        this.locations = locations;
    }
    public LocationsEntity getLocations() {
        return this.locations;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(address);
        sb.append("|");
        sb.append(primaryContact);
        sb.append("|");
        sb.append(secondaryContact);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(status);
        return sb.toString(); 
    } 

}