package telran.b7a.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.AgeDto;
import telran.b7a.person.dto.ChangeNameDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@PostMapping("/add")
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}
	
	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto removePersonById(@PathVariable Integer id) {
		return personService.removePersonById(id);
	}
	
	@PutMapping("/name/{id}")
	public PersonDto updatePersonName(@PathVariable Integer id, @RequestBody ChangeNameDto newName) {
		return personService.updatePersonName(id, newName.getName());
	}
	
	@PutMapping("/address/{id}")
	public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto newAddress) {
		return personService.updatePersonAddress(id, newAddress);
	}
	
	@GetMapping("/persons/name/{name}")
	public Iterable<PersonDto> findPersonsByName(@PathVariable String name){
		return personService.findPersonsByName(name);
	}
	
	@GetMapping("/persons/city/{address}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String address){
		return personService.findPersonsByCity(address);
	}
	
	@GetMapping("/persons/age")
	public Iterable<PersonDto> findPersonsByAge(@RequestBody AgeDto ages){
		return personService.findPersonsBeetwenAges(ages.getMinAge(), ages.getMaxAge());
	}
	
	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCityPopulationDto() {
		return personService.getCityPopulation();
	}
	
	
}
