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
@JsonTypeName("child")
public class ChildDto extends PersonDto {
	
	String kindergarten;

}
