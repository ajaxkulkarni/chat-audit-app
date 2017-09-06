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

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "dept_skills_map"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="dept_skills_map", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="DeptSkillsMapEntity.countAll", query="SELECT COUNT(x) FROM DeptSkillsMapEntity x" )
} )
public class DeptSkillsMapEntity implements Serializable {

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
    @Column(name="priority")
    private Integer    priority     ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable=false)
    private Date       createdDate  ;

    @Column(name="status", length=15)
    private String     status       ;

	// "deptLocId" (column "dept_loc_id") is not defined by itself because used as FK in a link 
	// "reasonId" (column "reason_id") is not defined by itself because used as FK in a link 
	// "reasonGroupId" (column "reason_group_id") is not defined by itself because used as FK in a link 
	// "reasonCategoryId" (column "reason_category_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="reason_id", referencedColumnName="id")
    private ReasonsEntity reasons     ;

    @ManyToOne
    @JoinColumn(name="reason_category_id", referencedColumnName="ReasonCategory_ID")
    private ReasonCategoryEntity reasonCategory;

    @ManyToOne
    @JoinColumn(name="reason_group_id", referencedColumnName="ReasonGroup_Id")
    private ReasonGroupEntity reasonGroup ;

    @ManyToOne
    @JoinColumn(name="dept_loc_id", referencedColumnName="id")
    private DeptLocationEntity deptLocation;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public DeptSkillsMapEntity() {
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
    //--- DATABASE MAPPING : priority ( INT ) 
    public void setPriority( Integer priority ) {
        this.priority = priority;
    }
    public Integer getPriority() {
        return this.priority;
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
    public void setReasons( ReasonsEntity reasons ) {
        this.reasons = reasons;
    }
    public ReasonsEntity getReasons() {
        return this.reasons;
    }

    public void setReasonCategory( ReasonCategoryEntity reasonCategory ) {
        this.reasonCategory = reasonCategory;
    }
    public ReasonCategoryEntity getReasonCategory() {
        return this.reasonCategory;
    }

    public void setReasonGroup( ReasonGroupEntity reasonGroup ) {
        this.reasonGroup = reasonGroup;
    }
    public ReasonGroupEntity getReasonGroup() {
        return this.reasonGroup;
    }

    public void setDeptLocation( DeptLocationEntity deptLocation ) {
        this.deptLocation = deptLocation;
    }
    public DeptLocationEntity getDeptLocation() {
        return this.deptLocation;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(priority);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(status);
        return sb.toString(); 
    } 

}