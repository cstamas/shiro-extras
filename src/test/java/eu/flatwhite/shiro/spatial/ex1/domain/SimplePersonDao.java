package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class SimplePersonDao implements PersonDao {

    private final HashMap<String, Person> persons = new HashMap<String, Person>();

    @Override
    public Collection<Person> findAll() {
	return Collections.unmodifiableCollection(persons.values());
    }

    @Override
    public Person findByUsername(String username) {
	return persons.get(username);
    }

    @Override
    public void add(Person person) {
	persons.put(person.getUsername(), person);
    }

    @Override
    public void update(Person person) {
	// plain and dumb
	add(person);
    }

    @Override
    public void removeByUsername(String username) {
	persons.remove(username);
    }

}
