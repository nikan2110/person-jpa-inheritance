package telran.b7a.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SalaryDto {
	
	Integer minSalary;
	Integer maxSalary;

}
