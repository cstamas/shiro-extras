package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.Collection;

/**
 * A simple person DAO. This class would be part of Shiro integrator's
 * EIS implementation. This is "below" Shiro, and is just helping interfacing
 * with it.
 * 
 * @author cstamas
 */
public interface PersonDao {

    Collection<Person> findAll();

    Person findByUsername(String username);

    void add(Person person);

    void update(Person person);

    void removeByUsername(String username);

}
