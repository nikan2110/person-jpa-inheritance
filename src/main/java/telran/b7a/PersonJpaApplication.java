package telran.b7a;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.b7a.person.dao.PersonRepository;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Child;
import telran.b7a.person.model.Employee;

@SpringBootApplication
public class PersonJpaApplication implements CommandLineRunner {
	
	@Autowired
	PersonRepository personRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PersonJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() != 0) {
			return; 
		}
			Employee p1 = new Employee(1000, "John", LocalDate.of(1990, 5, 12), new Address("Rehovot", "Herzl", 18),
					"Motorola", 20000);
			Employee p2 = new Employee(2000, "Mary", LocalDate.of(1994, 11, 23), new Address("Lod", "Laskov", 13),
					"IBM", 21000);
			Child p3 = new Child(3000, "Peter", LocalDate.of(2018, 3, 26), new Address("Ashkelon", "Bar Kohba", 117),
					"Shalom");
			personRepository.save(p1);
			personRepository.save(p2);
			personRepository.save(p3);
		
	}

}
