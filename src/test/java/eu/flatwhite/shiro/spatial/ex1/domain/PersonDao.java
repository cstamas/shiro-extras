package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.Collection;

public interface PersonDao {

    Collection<Person> findAll();

    Person findByUsername(String username);

    void add(Person person);

    void update(Person person);

    void removeByUsername(String username);

}
