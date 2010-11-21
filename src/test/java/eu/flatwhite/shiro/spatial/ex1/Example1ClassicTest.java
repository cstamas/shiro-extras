package eu.flatwhite.shiro.spatial.ex1;

import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.Person.Gender;

/**
 * Example1 "classic" implementation: FunnyCorp (with one vending machine and
 * strange manager)
 * 
 * @author cstamas
 * 
 */
public class Example1ClassicTest extends Example1Base {

    @Override
    protected String getIniPath() {
	return "classpath:shiro-ex1-classic.ini";
    }

    // "Classic" helpers

    @Override
    protected void populateInitialRoles() {
	// we create three roles to cover the three points of the manager
	// decrete
	// these roles are acoutally covering each point by having proper
	// permission
	personRoleDao.addRole("rule1", new String[] { "coffee" });
	personRoleDao.addRole("rule2", new String[] { "coke" });
	personRoleDao.addRole("rule3", new String[] { "beer" });
    }

    @Override
    protected void applyManagerDecrete() {
	// we iterate over persons in DB and just "attach" proper roles to them
	for (Person person : personDao.findAll()) {
	    // rule1
	    if (Gender.FEMALE.equals(person.getGender())) {
		person.getRoles().add("rule1");
	    } else {
		person.getRoles().remove("rule1");
	    }
	    // rule2
	    if (Gender.MALE.equals(person.getGender())) {
		person.getRoles().add("rule2");
	    } else {
		person.getRoles().remove("rule2");
	    }
	    // rule3
	    if (person.getBadgeNo() <= 10) {
		person.getRoles().add("rule3");
	    } else {
		person.getRoles().remove("rule3");
	    }

	    personDao.update(person);
	}
    }
}
