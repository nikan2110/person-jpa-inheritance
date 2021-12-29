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
import telran.b7a.person.dto.exception.UnknownPersonTypeException;
import telran.b7a.person.dto.exception.UserExistException;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	private static final String MODEL_PACKAGE = "telran.b7a.person.model.";
	private static final String DTO_SUFFIX = "Dto";
	private static final String DTO_PACKAGE = "telran.b7a.person.dto.";
	
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
		if (personRepository.existsById(personDto.getId())) {
			throw new UserExistException(personDto.getId());
		}
		personRepository.save(modelMapper.map(personDto, getModelClass(personDto)));
		return true;
	}


	@Override
	public PersonDto findPersonById(Integer id) {
	Person person = getPerson(id);
	return modelMapper.map(person, getDtoClass(person));
	}

	@Override
	@Transactional
	public PersonDto removePersonById(Integer id) {
		Person person =	getPerson(id);
		personRepository.delete(person);
		return modelMapper.map(person, getDtoClass(person));
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(Integer id, String name) {
		Person person =	getPerson(id);
		person.setName(name);
		return modelMapper.map(person, getDtoClass(person));
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
		Person person =	getPerson(id);
		Address address = person.getAddress();
		checkAddress(address, newAddress);
		return modelMapper.map(person, getDtoClass(person));
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
		return 	personRepository.findByNameIgnoreCase(name.toUpperCase())
				.map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBeetwenAges(Integer minAge, Integer maxAge) {
		LocalDate dateFrom = LocalDate.now().minusYears(maxAge);
		LocalDate dateTo = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(dateFrom, dateTo)
				.map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city.toUpperCase())
				.map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}


	@Override
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personRepository.getPopulationCity();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
		return personRepository.findBySalaryBetween(min, max)
				.map(m -> modelMapper.map(m, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> getChildren() {
		return personRepository.findChildrenBy()
				.map(c -> modelMapper.map(c, ChildDto.class))
				.collect(Collectors.toList());
	}

	
	private Person getPerson(Integer id) {
		return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Person> getModelClass(PersonDto personDto) {
		String modelClassName = personDto.getClass().getSimpleName();
		modelClassName = modelClassName.substring(0, modelClassName.length()-3);
		try {
			Class<? extends Person> clazz = (Class<? extends Person>) Class.forName(MODEL_PACKAGE + modelClassName);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		} 
		
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends PersonDto> getDtoClass(Person person) {
		String dtoClassName = person.getClass().getSimpleName();
		dtoClassName = dtoClassName + DTO_SUFFIX;
		try {
			Class<? extends PersonDto> clazz = (Class<? extends PersonDto>) Class.forName(DTO_PACKAGE + dtoClassName);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		} 
	}

}
