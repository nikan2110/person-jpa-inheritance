package telran.b7a.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.b7a.person.dao.PersonRepository;
import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.ChildDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.EmployeeDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.dto.exception.PersonNotFoundException;
import telran.b7a.person.dto.exception.UserExistException;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Child;
import telran.b7a.person.model.Employee;
import telran.b7a.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	PersonRepository personRepository;
	ModelMapper modelMapper;
	
	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		EmployeeDto employeeDto = modelMapper.map(personDto, EmployeeDto.class);
		ChildDto childDto = modelMapper.map(personDto, ChildDto.class);
		if (personRepository.existsById(personDto.getId())) {
			throw new UserExistException(personDto.getId());
		}
		if (employeeDto.getCompany() != null && employeeDto.getSalary() != null) {
			personRepository.save(modelMapper.map(employeeDto, Employee.class));
			return true;
		}
		if (childDto.getKindergarten() != null) {
			personRepository.save(modelMapper.map(childDto, Child.class));
			return true;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
	Person person = getPerson(id);
	EmployeeDto employeeDto = modelMapper.map(person, EmployeeDto.class);
	ChildDto childDto = modelMapper.map(person, ChildDto.class);
	if (employeeDto.getCompany() != null && employeeDto.getSalary() != null) {
		return employeeDto; 
	}
	if (childDto.getKindergarten() != null) {
		return childDto;
	}
	
	return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto removePersonById(Integer id) {
		Person person =	getPerson(id);
		EmployeeDto employeeDto = modelMapper.map(person, EmployeeDto.class);
		ChildDto childDto = modelMapper.map(person, ChildDto.class);
		if (employeeDto.getCompany() != null && employeeDto.getSalary() != null) {
			personRepository.delete(person);
			return employeeDto; 
		}
		if (childDto.getKindergarten() != null) {
			personRepository.delete(person);
			return childDto;
		}
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(Integer id, String name) {
		Person person =	getPerson(id);
		person.setName(name);
		EmployeeDto employeeDto = modelMapper.map(person, EmployeeDto.class);
		ChildDto childDto = modelMapper.map(person, ChildDto.class);
		if (employeeDto.getCompany() != null && employeeDto.getSalary() != null) {
			return employeeDto; 
		}
		if (childDto.getKindergarten() != null) {
			return childDto;
		}
		
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
		Person person =	getPerson(id);
		Address address = person.getAddress();
		checkAddress(address, newAddress);
		EmployeeDto employeeDto = modelMapper.map(person, EmployeeDto.class);
		ChildDto childDto = modelMapper.map(person, ChildDto.class);
		if (employeeDto.getCompany() != null && employeeDto.getSalary() != null) {
			return employeeDto; 
		}
		if (childDto.getKindergarten() != null) {
			return childDto;
		}
		return modelMapper.map(person, PersonDto.class);
	}

	private void checkAddress(Address address, AddressDto newAddress) {
		if (newAddress.getBuilding() != null) {
			address.setBuilding(newAddress.getBuilding());
		}
		if (newAddress.getCity() != null) {
			address.setCity(newAddress.getCity());
		}
		if (newAddress.getStreet() != null) {
			address.setStreet(newAddress.getStreet());
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return 	personRepository.findByNameIgnoreCase(name)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBeetwenAges(Integer minAge, Integer maxAge) {
		LocalDate dateFrom = LocalDate.now().minusYears(maxAge);
		LocalDate dateTo = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(dateFrom, dateTo)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city.toUpperCase())
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}


	@Override
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personRepository.getPopulationCity();
	}

	
	private Person getPerson(Integer id) {
		return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}
}
