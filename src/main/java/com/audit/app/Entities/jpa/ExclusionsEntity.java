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

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "exclusions"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="exclusions", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ExclusionsEntity.countAll", query="SELECT COUNT(x) FROM ExclusionsEntity x" )
} )
public class ExclusionsEntity implements Serializable {

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
    @Column(name="reason_id")
    private Integer    reasonId     ;

    @Column(name="exclusion_value", length=45)
    private String     exclusionValue ;

    @Column(name="exclusion_type", length=45)
    private String     exclusionType ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private Date       createdDate  ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ExclusionsEntity() {
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
    //--- DATABASE MAPPING : reason_id ( INT ) 
    public void setReasonId( Integer reasonId ) {
        this.reasonId = reasonId;
    }
    public Integer getReasonId() {
        return this.reasonId;
    }

    //--- DATABASE MAPPING : exclusion_value ( VARCHAR ) 
    public void setExclusionValue( String exclusionValue ) {
        this.exclusionValue = exclusionValue;
    }
    public String getExclusionValue() {
        return this.exclusionValue;
    }

    //--- DATABASE MAPPING : exclusion_type ( VARCHAR ) 
    public void setExclusionType( String exclusionType ) {
        this.exclusionType = exclusionType;
    }
    public String getExclusionType() {
        return this.exclusionType;
    }

    //--- DATABASE MAPPING : created_date ( DATETIME ) 
    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(reasonId);
        sb.append("|");
        sb.append(exclusionValue);
        sb.append("|");
        sb.append(exclusionType);
        sb.append("|");
        sb.append(createdDate);
        return sb.toString(); 
    } 

}