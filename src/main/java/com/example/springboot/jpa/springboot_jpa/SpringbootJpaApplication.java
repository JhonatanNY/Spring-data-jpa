package com.example.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.example.springboot.jpa.springboot_jpa.entities.Person;
import com.example.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		subQueries();
	}

	@Transactional
	public void subQueries() {
		System.out.println("============== consulta por el nombre mas corto y su largo ==============");
		List<Object[]> registers = repository.getShorterName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name = " + name + ", length = " + length);
		});
	}

	@Transactional
	public void queriesFunctionAggregation() {

		System.out.println("============== consulta con el total de registros de la tabla persona ==============");
		Long count = repository.gettotalPerson();
		System.out.println(count);

		System.out.println("============== consulta con el valor minimo id ==============");
		Long min = repository.getminId();
		System.out.println(min);

		System.out.println("============== consulta con el valor maximo id ==============");
		Long max = repository.getmaxId();
		System.out.println(max);

		System.out.println("============== consulta con el valor maximo id ==============");
		List<Object[]> regs = repository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name = " + name + ", length = " + length);
		});

		System.out.println("============== consulta con el nombre mas corto ==============");
		Integer minLengthName = repository.getMinLengthName();
		System.out.println(minLengthName);

		System.out.println("============== consulta con el nombre mas largo ==============");
		Integer maxLengthName = repository.getMaxLengthName();
		System.out.println(maxLengthName);

		System.out.println(
				"============== consulta resumen de funciones de agregacion min, max, sum, avg, count ==============");
		Object[] resumeReg = (Object[]) repository.getResumeAggregationFunction();
		System.out.println(" min=" + resumeReg[0] + ", max=" + resumeReg[1] + ", sum=" + resumeReg[2] + ", avg="
				+ resumeReg[3] + ", counts=" + resumeReg[4]);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween() {
		System.out.println("============== consulta por rangos id con between ==============");
		List<Person> persons = repository.findByIdBetweenOrderByIdDesc(2L, 5L);
		persons.forEach(p -> System.out.println(p));

		System.out.println("============== consulta por rangos nombres con between ==============");
		persons = repository.findAllBetweenName("J", "P");
		persons.forEach(p -> System.out.println(p));

		System.out.println("============== consulta con order by ==============");
		persons = repository.findAllByOrderByNameDesc();
		persons.forEach(p -> System.out.println(p));

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {
		System.out.println("============== consulta nombres y apellidos de personas ==============");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(p -> System.out.println(p));

		System.out.println("============== consulta nombres y apellidos en mayusculas ==============");
		names = repository.findAllFullNameConcatUpper();
		names.forEach(p -> System.out.println(p));

		System.out.println("============== consulta nombres y apellidos en minusculas ==============");
		names = repository.findAllFullNameConcatLower();
		names.forEach(p -> System.out.println(p));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {
		System.out.println("============== Consultas con nombres de personas ==============");
		List<String> names = repository.findAllNames();
		names.forEach(p -> System.out.println(p));
		System.out.println("============== Consulta con nombres unicos de personas 'distinct' ============== ");
		names = repository.findAllNamesDistinct();
		names.forEach(p -> System.out.println(p));
		System.out.println("============== Consulta con nombres unicos de personas 'distinct' ============== ");
		Long totalLanguage = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("Total de lenguajes unicos:" + totalLanguage);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {

		System.out.println("============== Consulta por objeto persona y lenguaje de programacion ==============");
		List<Object[]> personsRegs = repository.findAllMixPerson();

		personsRegs.forEach(reg -> {
			System.out.println("programmingLanguage= " + reg[1] + ", person =" + reg[0]);
		});

		System.out.println(
				"============== Consulta que puebla y devuelve objeto entity de una instancia personalizada ==============");
		List<Person> persons = repository.findAllObjectPersonalized();
		persons.forEach(p -> System.out.println(p));

		System.out.println("============== Consulta y devulve objeto dto de una clase personalizada ==============");
		List<PersonDto> personDto = repository.findAllPersonDto();
		personDto.forEach(p -> System.out.println(p));

	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("=========================== Consulta solo el nombre por el id ===========================");
		System.out.print("Ingrese el id: ");
		Long id = scanner.nextLong();

		System.out.println("============== Mostrando el nombre ==============");
		String name = repository.getNameById(id);
		System.out.println(name);
		System.out.println("============== Mostrando el nombre completo============== ");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);
		System.out.println("============== Consulta por campos persona por el id ==============");
		Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);
		System.out.println("id=" + personReg[0] + ", nombre:" + personReg[1] + ", apellido=" + personReg[2]
				+ ", lenguaje=" + personReg[3]);
		System.out.println(
				"============== Consulta por campos personalizador por el id pero devuelve lista ==============");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(p -> System.out.println(p[0] + ", nombre:" + p[1] + ",apellido=" + p[2] + ",lenguaje=" + p[3]));

		scanner.close();
	}

	@Transactional
	public void delete2() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id que desea eliminar: ");
		repository.findAll().forEach(valor -> System.out.println(valor));
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(person -> repository.delete(person),
				() -> System.out.println("Lo sentimos no existe la persona con ese id"));
		repository.deleteById(id);
		repository.findAll().forEach(valor -> System.out.println(valor));

		scanner.close();
	}

	@Transactional
	public void delete() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id que desea eliminar: ");
		repository.findAll().forEach(valor -> System.out.println(valor));
		Long id = scanner.nextLong();
		repository.deleteById(id);
		repository.findAll().forEach(valor -> System.out.println(valor));

		scanner.close();
	}

	@Transactional
	public void update() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresent(person -> {
			System.out.println(person);
			System.out.println("Ingrese el lenguaje de programación: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person personDb = repository.save(person);
			System.out.println(personDb);
		});

		scanner.close();
	}

	@Transactional
	public void create() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre: ");
		String name = scanner.next();
		System.out.println("Ingrese el apellido: ");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programación: ");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);
		Person newPerson = repository.save(person);
		System.out.println(newPerson);

		repository.findById(newPerson.getId()).ifPresent(p -> System.out.println(p));
	}

	@Transactional(readOnly = true)
	public void findOne() {

		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if(optionalPerson.isPresent()){
		// person = optionalPerson.get();
		// }
		// System.out.println(person);

		repository.findByNameContaining("res").ifPresent(person -> System.out.println(person));
	}

	@Transactional(readOnly = true)
	public void list() {

		// List<Person> persons = (List<Person>) repository.findAll();
		List<Person> persons = (List<Person>) repository.findByprogrammingLanguageAndName("Python", "Pepe");

		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsValues = repository.obtenerPersonData("pepe");

		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}

}
