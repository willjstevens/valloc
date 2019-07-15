/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.ApplicationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wstevens
 */
@Entity
@Table(name="application_error")
@SequenceGenerator(
        name="id_seq",
        allocationSize=1,
        sequenceName="application_error_id_seq"
)
public class PersistentApplicationError extends DomainObject implements ApplicationError
{
    private String customMessage;
    private String toString;
    private String causeMessage;
    private String simpleClassName;
    private String fullClassName;
    private Date occuranceTimestamp;

    @Column(name="custom_message", nullable=true)
    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    @Column(name="to_string", nullable=true)
    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    @Column(name="cause_message", nullable=true)
    public String getCauseMessage() {
        return causeMessage;
    }

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }

    @Column(name="simple_class_name", nullable=true)
    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    @Column(name="full_class_name", nullable=true)
    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    @Column(name="occurance_timestamp", nullable=true)
    public Date getOccuranceTimestamp() {
        return occuranceTimestamp;
    }

    public void setOccuranceTimestamp(Date occuranceTimestamp) {
        this.occuranceTimestamp = occuranceTimestamp;
    }
}
