package eu.flatwhite.shiro.spatial.ex1;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.Person.Gender;
import eu.flatwhite.shiro.spatial.ex1.domain.PersonDao;
import eu.flatwhite.shiro.spatial.ex1.domain.PersonRoleDao;

public class Example1Test extends TestCase {

    protected IniSecurityManagerFactory config;

    protected SecurityManager securityManager;

    protected PersonDao personDao;

    protected PersonRoleDao personRoleDao;

    protected void setUp() throws Exception {
	super.setUp();

	config = new IniSecurityManagerFactory(
		"classpath:shiro-ex1-classic.ini");

	securityManager = config.getInstance();

	SecurityUtils.setSecurityManager(securityManager);

	personDao = (PersonDao) config.getBeans().get("personDao");

	personRoleDao = (PersonRoleDao) config.getBeans().get("personRoleDao");
    }

    protected void applyManagerDecrete() {
	// we create three roles to cover the three points of the manager
	// decrete
	// these roles are acoutally covering each point by having proper
	// permission
	personRoleDao.addRole("rule1", new String[] { "coffee" });
	personRoleDao.addRole("rule2", new String[] { "coke" });
	personRoleDao.addRole("rule3", new String[] { "beer" });

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

    protected boolean canIHaveCoffe(Subject subject) {
	return subject.isPermitted("coffee");
    }

    protected boolean canIHaveCoke(Subject subject) {
	return subject.isPermitted("coke");
    }

    protected boolean canIHaveBeer(Subject subject) {
	return subject.isPermitted("beer");
    }

    protected boolean checkRules(Person person, Subject subject) {
	try {
	    // rule1: girls may have coffee
	    Assert.assertEquals(Gender.FEMALE.equals(person.getGender()),
		    canIHaveCoffe(subject));
	    // rule2: boys may have coke
	    Assert.assertEquals(Gender.MALE.equals(person.getGender()),
		    canIHaveCoke(subject));
	    // rule3: merited employees may have beer
	    Assert.assertEquals(person.getBadgeNo() <= 10,
		    canIHaveBeer(subject));

	    return true;
	} catch (AssertionFailedError e) {
	    return false;
	}
    }

    protected Set<String> checkAllPersonRules() {
	Set<String> failedPersons = new HashSet<String>();

	for (Person person : personDao.findAll()) {
	    Subject currentUser = SecurityUtils.getSubject();

	    UsernamePasswordToken token = new UsernamePasswordToken(
		    person.getUsername(), person.getPassword());

	    currentUser.login(token);

	    if (!checkRules(person, currentUser)) {
		failedPersons.add(person.getUsername());
	    }

	    currentUser.logout();
	}
	return failedPersons;
    }

    public void testClassic() {
	// add employees to our "DB" (PersonDao), virgin, without any roles
	// given yet
	personDao.add(new Person("jvanzyl", "woohoo", "Jason Van Zyl",
		Gender.MALE, 1, null));
	personDao.add(new Person("cstamas", "123", "Tamas Cservenak",
		Gender.MALE, 5, null));
	personDao.add(new Person("kristine", "honeybee", "Kristine Workflow",
		Gender.FEMALE, 12, null));
	personDao.add(new Person("damian", "ilovems", "Damian Windowsguy",
		Gender.MALE, 10, null));
	personDao.add(new Person("toby", "iloveapple", "Toby Proxyhouse",
		Gender.MALE, 15, null));
	personDao.add(new Person("linda", "magic", "Linda Extmaster",
		Gender.FEMALE, 22, null));

	// check them: they should be all able to login (PersonDao does have
	// their creds)
	// but they have not any perms at all, so all our employee database must
	// fail
	Assert.assertEquals(personDao.findAll().size(), checkAllPersonRules().size());

	// apply manager decrete, but updating Persons and Roles databases
	// (PersonDao and PersonRoleDao)
	applyManagerDecrete();

	// check them: they should now all obey the manager decrete
	Assert.assertEquals(0, checkAllPersonRules().size());

	// fire some of them (this case is not really working, since fired will be removed
	// from DB, hence, will not be able to login at all and will not be checked at all)
	personDao.removeByUsername("toby");

	// hire new employees
	personDao.add(new Person("tim", "babygo", "Tim O'Reader", Gender.MALE,
		25, null));
	personDao.add(new Person("diane", "wannabeceo", "Diana Walker",
		Gender.FEMALE, 26, null));

	// promote some employees
	{
	    Person p = personDao.findByUsername("linda");
	    p.setBadgeNo(9);
	    personDao.update(p);
	}

	// perform a gender change on Jason
	{
	    Person p = personDao.findByUsername("jvanzyl");
	    p.setFullName("Jacqueline Van Zyl");
	    p.setGender(Gender.FEMALE);
	    personDao.update(p);
	}

	// check them: they will fail, we modified 4 persons (2 added, 1
	// promoted, 1 gender changed), roles are messed up
	Assert.assertEquals(4, checkAllPersonRules().size());

	// to "fix", we need to batch reapply manager decrete (to update person
	// roles)
	applyManagerDecrete();

	// check them: they will pass now
	Assert.assertEquals(0, checkAllPersonRules().size());
    }
}
