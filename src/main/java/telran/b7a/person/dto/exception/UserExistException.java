package telran.b7a.person.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(code = HttpStatus.CONFLICT)
@NoArgsConstructor
public class UserExistException extends RuntimeException {/**
	 * 
	 */
	private static final long serialVersionUID = -6878819084308767014L;
	public UserExistException(Integer login) {
		super("Person " + login + " already exist");
	}
}
