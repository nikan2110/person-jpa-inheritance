package telran.b7a.person.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = -533851198717743153L;
	
	String company;
	int salary;
	
	public Employee(int id, String name, LocalDate birthDate, Address address, String company, int salary) {
		super(id, name, birthDate, address);
		this.company = company;
		this.salary = salary;
	}
	
	
	

}
