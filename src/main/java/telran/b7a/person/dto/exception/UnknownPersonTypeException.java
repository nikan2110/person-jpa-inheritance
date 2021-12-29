package telran.b7a.person.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownPersonTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2505806089840225237L;

}
