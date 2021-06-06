package run.dampharm.app.utils;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.validation.FieldError;

import org.springframework.validation.ObjectError;

import run.dampharm.app.model.ApiError;
import run.dampharm.app.model.ApiError.ApiSubError;
import run.dampharm.app.model.ApiError.ApiValidationError;

@Component

public class ApiErrorBuilder {

	@Autowired

	private ResourceBundleUtility resourceBundleUtility;

	public ApiError build(HttpStatus status, String msgKey) {

		ApiError apiError = ApiError.builder().status(status).message(resourceBundleUtility.getMessage(msgKey))

				.timestamp(LocalDateTime.now()).build();

		return apiError;

	}

	public ApiError build(HttpStatus status, String msgKey, Set<ConstraintViolation<?>> constraintViolations) {

		ApiError apiError = build(status, msgKey);

		addConstraintViolationErrors(apiError, constraintViolations);

		return apiError;

	}

	public ApiError build(HttpStatus status, String msgKey, List<FieldError> fieldErrors,
			List<ObjectError> globalErrors) {

		ApiError apiError = build(status, msgKey);

		addFieldValidationErrors(apiError, fieldErrors);

		addObjectValidationErrors(apiError, globalErrors);

		return apiError;

	}

	public ApiError build(HttpStatus status, String msgKey, List<FieldError> fieldErrors,
			List<ObjectError> globalErrors, Set<ConstraintViolation<?>> constraintViolations) {

		ApiError apiError = build(status, msgKey, fieldErrors, globalErrors);

		addConstraintViolationErrors(apiError, constraintViolations);

		return apiError;

	}

	public ApiError build(HttpStatus status, String message, Throwable ex) {

		ApiError apiError = build(status, message);

		apiError.setDebugMessage(ex.getLocalizedMessage());

		return apiError;

	}

	public ApiError build(HttpStatus status, Throwable ex) {

		return build(status, Constants.UN_EXPECTED_ERROR, ex);

	}

	public void addFieldValidationErrors(ApiError apiError, List<FieldError> fieldErrors) {

		fieldErrors.forEach(fieldError -> addValidationError(fieldError, apiError));

	}

	public void addObjectValidationErrors(ApiError apiError, List<ObjectError> globalErrors) {

		globalErrors.forEach(objectError -> addValidationError(objectError, apiError));

	}

	public void addConstraintViolationErrors(ApiError apiError, Set<ConstraintViolation<?>> constraintViolations) {

		constraintViolations.forEach(constraintViolation -> addValidationError(constraintViolation, apiError));

	}

	private void addSubError(ApiError apiError, ApiSubError subError) {

		if (apiError.getSubErrors() == null) {

			apiError.setSubErrors(new ArrayList<>());

		}

		apiError.getSubErrors().add(subError);

	}

	private void addValidationError(ApiError apiError, String object, String field, Object rejectedValue,

			String message) {

		addSubError(apiError, ApiValidationError.builder().object(object).field(field).rejectedValue(rejectedValue)

				.message(message).build());

	}

	private void addValidationError(ApiError apiError, String object, String message) {

		addSubError(apiError, ApiValidationError.builder().object(object).message(message).build());

	}

	private void addValidationError(FieldError fieldError, ApiError apiError) {

		this.addValidationError(apiError, fieldError.getObjectName(), fieldError.getField(),

				fieldError.getRejectedValue(), fieldError.getDefaultMessage());

	}

	private void addValidationError(ObjectError objectError, ApiError apiError) {

		this.addValidationError(apiError, objectError.getObjectName(), objectError.getDefaultMessage());

	}

	/**
	 * 
	 * Utility method for adding error of ConstraintViolation. Usually when
	 * 
	 * a @Validated validation fails.
	 *
	 * 
	 * 
	 * @param cv the ConstraintViolation
	 * 
	 */

	private void addValidationError(ConstraintViolation<?> cv, ApiError apiError) {

		this.addValidationError(apiError, cv.getRootBeanClass().getSimpleName(),

				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(),

				resourceBundleUtility.getMessage(cv.getMessage()));

	}

}
