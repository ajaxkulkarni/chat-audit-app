/*
 * Created on 13 Jul 2017 ( Time 13:22:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.audit.app.Entities.jpa;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "chat_audit_log"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="chat_audit_log", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ChatAuditLogEntity.countAll", query="SELECT COUNT(x) FROM ChatAuditLogEntity x" )
} )
public class ChatAuditLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
    
    @Column(name="audit_log_id", nullable=false)
    private Integer    auditLogId   ;

    @Column(name="session_id", nullable=false)
    private Integer    sessionId    ;

    @Column(name="sender_id_ref", nullable=false)
    private Integer    senderIdRef  ;

    @Column(name="receiver_id_ref", nullable=false)
    private Integer    receiverIdRef ;

    @Column(name="reason_code_ref", nullable=false)
    private Integer    reasonCodeRef ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_chat_start", nullable=false)
    private Date       tsChatStart  ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_de_rq_received", nullable=false)
    private Date       tsDeRqReceived ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_de_rq_completed", nullable=false)
    private Date       tsDeRqCompleted ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_receiver_chat_start", nullable=false)
    private Date       tsReceiverChatStart ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_chat_complete", nullable=false)
    private Date       tsChatComplete ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_sender_feedback_complete", nullable=false)
    private Date       tsSenderFeedbackComplete ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts_receiver_feedback_complete", nullable=false)
    private Date       tsReceiverFeedbackComplete ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ChatAuditLogEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : audit_log_id ( INT ) 
    public void setAuditLogId( Integer auditLogId ) {
        this.auditLogId = auditLogId;
    }
    public Integer getAuditLogId() {
        return this.auditLogId;
    }

    //--- DATABASE MAPPING : session_id ( INT ) 
    public void setSessionId( Integer sessionId ) {
        this.sessionId = sessionId;
    }
    public Integer getSessionId() {
        return this.sessionId;
    }

    //--- DATABASE MAPPING : sender_id_ref ( INT ) 
    public void setSenderIdRef( Integer senderIdRef ) {
        this.senderIdRef = senderIdRef;
    }
    public Integer getSenderIdRef() {
        return this.senderIdRef;
    }

    //--- DATABASE MAPPING : receiver_id_ref ( INT ) 
    public void setReceiverIdRef( Integer receiverIdRef ) {
        this.receiverIdRef = receiverIdRef;
    }
    public Integer getReceiverIdRef() {
        return this.receiverIdRef;
    }

    //--- DATABASE MAPPING : reason_code_ref ( INT ) 
    public void setReasonCodeRef( Integer reasonCodeRef ) {
        this.reasonCodeRef = reasonCodeRef;
    }
    public Integer getReasonCodeRef() {
        return this.reasonCodeRef;
    }

    //--- DATABASE MAPPING : ts_chat_start ( TIMESTAMP ) 
    public void setTsChatStart( Date tsChatStart ) {
        this.tsChatStart = tsChatStart;
    }
    public Date getTsChatStart() {
        return this.tsChatStart;
    }

    //--- DATABASE MAPPING : ts_de_rq_received ( TIMESTAMP ) 
    public void setTsDeRqReceived( Date tsDeRqReceived ) {
        this.tsDeRqReceived = tsDeRqReceived;
    }
    public Date getTsDeRqReceived() {
        return this.tsDeRqReceived;
    }

    //--- DATABASE MAPPING : ts_de_rq_completed ( TIMESTAMP ) 
    public void setTsDeRqCompleted( Date tsDeRqCompleted ) {
        this.tsDeRqCompleted = tsDeRqCompleted;
    }
    public Date getTsDeRqCompleted() {
        return this.tsDeRqCompleted;
    }

    //--- DATABASE MAPPING : ts_receiver_chat_start ( TIMESTAMP ) 
    public void setTsReceiverChatStart( Date tsReceiverChatStart ) {
        this.tsReceiverChatStart = tsReceiverChatStart;
    }
    public Date getTsReceiverChatStart() {
        return this.tsReceiverChatStart;
    }

    //--- DATABASE MAPPING : ts_chat_complete ( TIMESTAMP ) 
    public void setTsChatComplete( Date tsChatComplete ) {
        this.tsChatComplete = tsChatComplete;
    }
    public Date getTsChatComplete() {
        return this.tsChatComplete;
    }

    //--- DATABASE MAPPING : ts_sender_feedback_complete ( TIMESTAMP ) 
    public void setTsSenderFeedbackComplete( Date tsSenderFeedbackComplete ) {
        this.tsSenderFeedbackComplete = tsSenderFeedbackComplete;
    }
    public Date getTsSenderFeedbackComplete() {
        return this.tsSenderFeedbackComplete;
    }

    //--- DATABASE MAPPING : ts_receiver_feedback_complete ( TIMESTAMP ) 
    public void setTsReceiverFeedbackComplete( Date tsReceiverFeedbackComplete ) {
        this.tsReceiverFeedbackComplete = tsReceiverFeedbackComplete;
    }
    public Date getTsReceiverFeedbackComplete() {
        return this.tsReceiverFeedbackComplete;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append("]:"); 
        sb.append(auditLogId);
        sb.append("|");
        sb.append(sessionId);
        sb.append("|");
        sb.append(senderIdRef);
        sb.append("|");
        sb.append(receiverIdRef);
        sb.append("|");
        sb.append(reasonCodeRef);
        sb.append("|");
        sb.append(tsChatStart);
        sb.append("|");
        sb.append(tsDeRqReceived);
        sb.append("|");
        sb.append(tsDeRqCompleted);
        sb.append("|");
        sb.append(tsReceiverChatStart);
        sb.append("|");
        sb.append(tsChatComplete);
        sb.append("|");
        sb.append(tsSenderFeedbackComplete);
        sb.append("|");
        sb.append(tsReceiverFeedbackComplete);
        return sb.toString(); 
    } 

}
