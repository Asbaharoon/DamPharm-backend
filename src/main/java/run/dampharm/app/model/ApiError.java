package run.dampharm.app.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "error")
public class ApiError {

	private HttpStatus status;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
	private LocalDateTime timestamp;

	private String message;

	private String debugMessage;

	private List<ApiSubError> subErrors;

	public static abstract class ApiSubError {

	}

	@Data
	@Builder
	@EqualsAndHashCode(callSuper = false)
	public static class ApiValidationError extends ApiSubError {

		private String object;

		private String field;

		private Object rejectedValue;

		private String message;

	}

}