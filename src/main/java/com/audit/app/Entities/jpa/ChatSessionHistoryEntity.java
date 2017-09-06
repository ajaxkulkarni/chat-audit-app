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


import javax.persistence.*;

/**
 * Persistent class for entity stored in table "chat_session_history"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="chat_session_history", catalog="chat_data" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ChatSessionHistoryEntity.countAll", query="SELECT COUNT(x) FROM ChatSessionHistoryEntity x" )
} )
public class ChatSessionHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //---------------------------------------------------------------------
    @Id
   	@GeneratedValue(strategy = IDENTITY)
   	@Column(name = "id", unique = true, nullable = false)
   	private int id;
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="chat_sess_hist_id", nullable=false)
    private Integer    chatSessHistId ;

    @Column(name="sender_id_ref", nullable=false)
    private Integer    senderIdRef  ;

    @Column(name="receiver_id_ref", nullable=false)
    private Integer    receiverIdRef ;

    @Column(name="reason_code_ref", nullable=false)
    private Integer    reasonCodeRef ;

    @Column(name="rsn_vec_attr_map_id_ref", nullable=false)
    private Integer    rsnVecAttrMapIdRef ;

    @Column(name="message_id_ref", nullable=false)
    private Integer    messageIdRef ;

    @Column(name="other_info_automation", nullable=false, length=2000)
    private String     otherInfoAutomation ;

    @Column(name="other_info_notes", nullable=false, length=2000)
    private String     otherInfoNotes ;

    @Column(name="sender_feedback", nullable=false, length=2000)
    private String     senderFeedback ;

    @Column(name="receiver_feedback", nullable=false, length=2000)
    private String     receiverFeedback ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ChatSessionHistoryEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : chat_sess_hist_id ( INT ) 
    public void setChatSessHistId( Integer chatSessHistId ) {
        this.chatSessHistId = chatSessHistId;
    }
    public Integer getChatSessHistId() {
        return this.chatSessHistId;
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

    //--- DATABASE MAPPING : rsn_vec_attr_map_id_ref ( INT ) 
    public void setRsnVecAttrMapIdRef( Integer rsnVecAttrMapIdRef ) {
        this.rsnVecAttrMapIdRef = rsnVecAttrMapIdRef;
    }
    public Integer getRsnVecAttrMapIdRef() {
        return this.rsnVecAttrMapIdRef;
    }

    //--- DATABASE MAPPING : message_id_ref ( INT ) 
    public void setMessageIdRef( Integer messageIdRef ) {
        this.messageIdRef = messageIdRef;
    }
    public Integer getMessageIdRef() {
        return this.messageIdRef;
    }

    //--- DATABASE MAPPING : other_info_automation ( VARCHAR ) 
    public void setOtherInfoAutomation( String otherInfoAutomation ) {
        this.otherInfoAutomation = otherInfoAutomation;
    }
    public String getOtherInfoAutomation() {
        return this.otherInfoAutomation;
    }

    //--- DATABASE MAPPING : other_info_notes ( VARCHAR ) 
    public void setOtherInfoNotes( String otherInfoNotes ) {
        this.otherInfoNotes = otherInfoNotes;
    }
    public String getOtherInfoNotes() {
        return this.otherInfoNotes;
    }

    //--- DATABASE MAPPING : sender_feedback ( VARCHAR ) 
    public void setSenderFeedback( String senderFeedback ) {
        this.senderFeedback = senderFeedback;
    }
    public String getSenderFeedback() {
        return this.senderFeedback;
    }

    //--- DATABASE MAPPING : receiver_feedback ( VARCHAR ) 
    public void setReceiverFeedback( String receiverFeedback ) {
        this.receiverFeedback = receiverFeedback;
    }
    public String getReceiverFeedback() {
        return this.receiverFeedback;
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
        sb.append("]:"); 
        sb.append(chatSessHistId);
        sb.append("|");
        sb.append(senderIdRef);
        sb.append("|");
        sb.append(receiverIdRef);
        sb.append("|");
        sb.append(reasonCodeRef);
        sb.append("|");
        sb.append(rsnVecAttrMapIdRef);
        sb.append("|");
        sb.append(messageIdRef);
        sb.append("|");
        sb.append(otherInfoAutomation);
        sb.append("|");
        sb.append(otherInfoNotes);
        sb.append("|");
        sb.append(senderFeedback);
        sb.append("|");
        sb.append(receiverFeedback);
        return sb.toString(); 
    } 

}
