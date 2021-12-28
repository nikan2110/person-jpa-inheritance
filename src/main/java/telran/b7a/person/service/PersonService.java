package telran.b7a.person.service;

import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.PersonDto;

public interface PersonService {
	
	boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto removePersonById(Integer id);
	
	PersonDto updatePersonName(Integer id, String name);
	
	PersonDto updatePersonAddress (Integer id, AddressDto newAddress);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<PersonDto> findPersonsBeetwenAges(Integer minAge, Integer maxAge);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<CityPopulationDto> getCityPopulation();

}
