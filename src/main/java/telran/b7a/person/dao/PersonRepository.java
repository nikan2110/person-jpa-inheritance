package telran.b7a.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	Stream<Person> findByNameIgnoreCase(String name);
	
//	@Query(value = "SELECT * FROM persons WHERE UPPER(city) =?1",nativeQuery = true)
	Stream<Person> findByAddressCityIgnoreCase(String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);
	
	@Query("select new telran.b7a.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getPopulationCity();

}
