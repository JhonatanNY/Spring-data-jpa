package com.example.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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

		personalizedQueries();
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("=========================== Consulta solo el nombre por el id ===========================");
		System.out.print("Ingrese el id que desea eliminar: ");
		Long id = scanner.nextLong();


		String name = repository.getNameById(id);
		System.out.println(name);
		scanner.close();
	}

	@Transactional
	public void delete2() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id que desea eliminar: ");
		repository.findAll().forEach(valor -> System.out.println(valor));
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(person -> repository.delete(person), ()-> System.out.println("Lo sentimos no existe la persona con ese id"));
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
