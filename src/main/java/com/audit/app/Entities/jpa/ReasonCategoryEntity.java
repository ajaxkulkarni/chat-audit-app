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
 * Persistent class for entity stored in table "reason_category"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="reason_category", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ReasonCategoryEntity.countAll", query="SELECT COUNT(x) FROM ReasonCategoryEntity x" )
} )
public class ReasonCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ReasonCategory_ID", nullable=false)
    private Integer    reasoncategoryId ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="ReasonCategory_Name", nullable=false, length=100)
    private String     reasoncategoryName ;

    @Column(name="ReasonCategory_Nickname", nullable=false, length=50)
    private String     reasoncategoryNickname ;

    @Column(name="status", nullable=false, length=15)
    private String     status       ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable=false)
    private Date       createdDate  ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="reasonCategory", targetEntity=ReasonGroupEntity.class)
    private List<ReasonGroupEntity> listOfReasonGroup;

    @OneToMany(mappedBy="reasonCategory", targetEntity=DeptSkillsMapEntity.class)
    private List<DeptSkillsMapEntity> listOfDeptSkillsMap;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ReasonCategoryEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setReasoncategoryId( Integer reasoncategoryId ) {
        this.reasoncategoryId = reasoncategoryId ;
    }
    public Integer getReasoncategoryId() {
        return this.reasoncategoryId;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : ReasonCategory_Name ( VARCHAR ) 
    public void setReasoncategoryName( String reasoncategoryName ) {
        this.reasoncategoryName = reasoncategoryName;
    }
    public String getReasoncategoryName() {
        return this.reasoncategoryName;
    }

    //--- DATABASE MAPPING : ReasonCategory_Nickname ( VARCHAR ) 
    public void setReasoncategoryNickname( String reasoncategoryNickname ) {
        this.reasoncategoryNickname = reasoncategoryNickname;
    }
    public String getReasoncategoryNickname() {
        return this.reasoncategoryNickname;
    }

    //--- DATABASE MAPPING : status ( VARCHAR ) 
    public void setStatus( String status ) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
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
    public void setListOfReasonGroup( List<ReasonGroupEntity> listOfReasonGroup ) {
        this.listOfReasonGroup = listOfReasonGroup;
    }
    public List<ReasonGroupEntity> getListOfReasonGroup() {
        return this.listOfReasonGroup;
    }

    public void setListOfDeptSkillsMap( List<DeptSkillsMapEntity> listOfDeptSkillsMap ) {
        this.listOfDeptSkillsMap = listOfDeptSkillsMap;
    }
    public List<DeptSkillsMapEntity> getListOfDeptSkillsMap() {
        return this.listOfDeptSkillsMap;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(reasoncategoryId);
        sb.append("]:"); 
        sb.append(reasoncategoryName);
        sb.append("|");
        sb.append(reasoncategoryNickname);
        sb.append("|");
        sb.append(status);
        sb.append("|");
        sb.append(createdDate);
        return sb.toString(); 
    } 

}