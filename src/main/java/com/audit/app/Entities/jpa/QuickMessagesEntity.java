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
 * Persistent class for entity stored in table "quick_messages"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="quick_messages", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="QuickMessagesEntity.countAll", query="SELECT COUNT(x) FROM QuickMessagesEntity x" )
} )
public class QuickMessagesEntity implements Serializable {

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
    @Column(name="quick_message", length=245)
    private String     quickMessage ;

    @Column(name="quick_message_short", length=45)
    private String     quickMessageShort ;

    @Column(name="user_id")
    private Integer    userId       ;

    @Column(name="dept_id")
    private Integer    deptId       ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private Date       createdDate  ;

    @Column(name="status", length=15)
    private String     status       ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public QuickMessagesEntity() {
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
    //--- DATABASE MAPPING : quick_message ( VARCHAR ) 
    public void setQuickMessage( String quickMessage ) {
        this.quickMessage = quickMessage;
    }
    public String getQuickMessage() {
        return this.quickMessage;
    }

    //--- DATABASE MAPPING : quick_message_short ( VARCHAR ) 
    public void setQuickMessageShort( String quickMessageShort ) {
        this.quickMessageShort = quickMessageShort;
    }
    public String getQuickMessageShort() {
        return this.quickMessageShort;
    }

    //--- DATABASE MAPPING : user_id ( INT ) 
    public void setUserId( Integer userId ) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return this.userId;
    }

    //--- DATABASE MAPPING : dept_id ( INT ) 
    public void setDeptId( Integer deptId ) {
        this.deptId = deptId;
    }
    public Integer getDeptId() {
        return this.deptId;
    }

    //--- DATABASE MAPPING : created_date ( DATETIME ) 
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

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(quickMessage);
        sb.append("|");
        sb.append(quickMessageShort);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(deptId);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(status);
        return sb.toString(); 
    } 

}
