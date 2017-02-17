package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	public Long getId() {
		return id;
	}
	
	public String getFormattedCreateDate() {
		return getFormattedData(createDate, "yyyy.MM.dd HH:mm:ss");
	}

	public String getFormattedModifiedDate() {
		return getFormattedData(createDate, "yyyy.MM.dd HH:mm:ss");
	}
	
	/*
	 * 2017.02.10
	 * 날짜 포멧 ex)2017.02.10 10:10:10
	 * author : aron
	 */
	public String getFormattedData(LocalDateTime dateTime, String format) {
		if(dateTime == null) {
			return "";
		}
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
