package telran.b7a.person.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "child")
@ToString
public class Child extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5628700669034255070L;
	
	String kindergarten;

	public Child(int id, String name, LocalDate birthDate, Address address, String kindergarten) {
		super(id, name, birthDate, address);
		this.kindergarten = kindergarten;
	}
	
	
}
