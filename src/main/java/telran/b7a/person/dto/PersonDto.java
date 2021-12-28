package telran.b7a.person.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = ChildDto.class, name = "child"),
	@JsonSubTypes.Type(value = EmployeeDto.class, name = "employee")
})
@ToString
public class PersonDto {

	Integer id;
	String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate birthDate;
	AddressDto address;

}
