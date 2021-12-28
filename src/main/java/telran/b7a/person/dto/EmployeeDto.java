package telran.b7a.person.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonTypeName("employee")
public class EmployeeDto extends PersonDto {

	String company;
	Integer salary;

}
