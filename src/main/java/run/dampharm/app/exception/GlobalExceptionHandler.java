package run.dampharm.app.exception;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.model.ApiError;
import run.dampharm.app.utils.ApiErrorBuilder;
import run.dampharm.app.utils.Constants;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired

	private ApiErrorBuilder apiErrorBuilder;

	@ExceptionHandler(value = { ConstraintViolationException.class })

	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,

			WebRequest request) {

		log.error(">>>>>>>>>Request Body Validation Exception : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA,

				ex.getConstraintViolations());

		return buildResponseEntity(apiError);

	}

//	@ExceptionHandler(value = { InvalidUsernamePasswordException.class })
//	public ResponseEntity<Object> unAuthorised(InvalidUsernamePasswordException ex) {
//
//		log.error(">>>>>>>>>InvalidUsernamePassword Exception : " + ExceptionUtils.getFullStackTrace(ex));
//
//		ApiError apiError = apiErrorBuilder.build(HttpStatus.UNAUTHORIZED, ex.getMessage());
//
//		return buildResponseEntity(apiError);
//
//	}

//	@ExceptionHandler(value = { BadRequestException.class })
//
//	public ResponseEntity<Object> invalidRequest(BadRequestException ex) {
//
//		log.error(">>>>>>>>>BadRequestException : " + ExceptionUtils.getFullStackTrace(ex));
//
//		ApiError apiError = apiErrorBuilder.build(HttpStatus.UNAUTHORIZED, ex.getMessage());
//
//		return buildResponseEntity(apiError);
//
//	}

	@ExceptionHandler(value = { BadCredentialsException.class })
	protected ResponseEntity<Object> handleUsernameNotFoundException(BadCredentialsException ex) {

		log.error(">>>>>>>>>BadCredentialsException : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST, Constants.INVALID_CRED);

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { ServiceException.class })
	protected ResponseEntity<Object> handleServiceException(ServiceException ex) {

		log.error(">>>>>>>>>ServiceException : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { ResourceAlreadyExistException.class })

	protected ResponseEntity<Object> handleConstraintViolationException(ResourceAlreadyExistException ex,

			WebRequest request) {

		log.error(">>>>>>>>>Resource Already Exist Exception : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage());

		return buildResponseEntity(apiError);

	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
		log.error(">>>>>>>>>Resource Not Found Exception : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.NOT_FOUND, ex.getMessage());

		return buildResponseEntity(apiError);

	}

	/**
	 * 
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * 
	 * validation.
	 *
	 * 
	 * 
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 * 
	 *                validation fails
	 * 
	 * @param headers HttpHeaders
	 * 
	 * @param status  HttpStatus
	 * 
	 * @param request WebRequest
	 * 
	 * @return the ApiError object
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(">>>>>>>>>Method Argument Not Valid Exception : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA,

				ex.getBindingResult().getFieldErrors(), ex.getBindingResult().getGlobalErrors());

		return buildResponseEntity(apiError);

	}

	/**
	 * 
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * 
	 * request parameter is missing.
	 *
	 * 
	 * 
	 * @param ex      MissingServletRequestParameterException
	 * 
	 * @param headers HttpHeaders
	 * 
	 * @param status  HttpStatus
	 * 
	 * @param request WebRequest
	 * 
	 * @return the ApiError object
	 * 
	 */

	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(">>>>>>>>>handle Missing Servlet Request Parameter : " + ExceptionUtils.getFullStackTrace(ex));

		String error = ex.getParameterName() + " parameter is missing";

		return buildResponseEntity(apiErrorBuilder.build(HttpStatus.BAD_REQUEST, error, ex));

	}

	/**
	 * 
	 * Handle Exception, handle generic Exception.class
	 *
	 * 
	 * 
	 * @param ex the Exception
	 * 
	 * @return the ApiError object
	 * 
	 */

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)

	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,

			WebRequest request) {

		log.error(">>>>>>>>>MethodArgumentTypeMismatchException : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST,

				String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(),

						ex.getValue(), ex.getRequiredType().getSimpleName()));

		return buildResponseEntity(apiError);

	}

	/**
	 * 
	 * Handle NoHandlerFoundException.
	 *
	 * 
	 * 
	 * @param ex
	 * 
	 * @param headers
	 * 
	 * @param status
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 */

	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		log.error(">>>>>>>>>handleNoHandlerFoundException : " + ExceptionUtils.getFullStackTrace(ex));

		ApiError apiError = apiErrorBuilder.build(HttpStatus.BAD_REQUEST,

				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		return buildResponseEntity(apiError);

	}

	/**
	 * 
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * 
	 * malformed.
	 *
	 * 
	 * 
	 * @param ex      HttpMessageNotReadableException
	 * 
	 * @param headers HttpHeaders
	 * 
	 * @param status  HttpStatus
	 * 
	 * @param request WebRequest
	 * 
	 * @return the ApiError object
	 * 
	 */

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(">>>>>>>>>handleHttpMessageNotReadable : " + ExceptionUtils.getFullStackTrace(ex));

		ServletWebRequest servletWebRequest = (ServletWebRequest) request;

		log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());

		String error = "Malformed JSON request";

		return buildResponseEntity(apiErrorBuilder.build(HttpStatus.BAD_REQUEST, error, ex));

	}

	/**
	 * 
	 * Handle HttpMessageNotWritableException.
	 *
	 * 
	 * 
	 * @param ex      HttpMessageNotWritableException
	 * 
	 * @param headers HttpHeaders
	 * 
	 * @param status  HttpStatus
	 * 
	 * @param request WebRequest
	 * 
	 * @return the ApiError object
	 * 
	 */

	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(">>>>>>>>>handleHttpMessageNotWritable : " + ExceptionUtils.getFullStackTrace(ex));

		String error = "Error writing JSON output";

		return buildResponseEntity(apiErrorBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));

	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {

		return new ResponseEntity<>(apiError, apiError.getStatus());

	}

}
