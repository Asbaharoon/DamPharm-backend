package run.dampharm.app.domain.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class DateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CreatedDate
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@LastModifiedDate
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

}
