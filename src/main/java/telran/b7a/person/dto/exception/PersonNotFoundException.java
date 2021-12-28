package telran.b7a.person.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class PersonNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5529855164824002775L;
	
	public PersonNotFoundException(Integer id) {
		super("Person with id " + id + " not found");
	}
	

}
