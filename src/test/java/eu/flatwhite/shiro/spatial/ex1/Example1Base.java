package eu.flatwhite.shiro.spatial.ex1;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.Person.Gender;
import eu.flatwhite.shiro.spatial.ex1.domain.PersonDao;
import eu.flatwhite.shiro.spatial.ex1.domain.PersonRoleDao;
import eu.flatwhite.shiro.spatial.ex1.domain.ProtectedVendingMachine;
import eu.flatwhite.shiro.spatial.ex1.domain.SimpleVendingMachine;
import eu.flatwhite.shiro.spatial.ex1.domain.VendingMachine;

/**
 * Example1 "base" implementation: FunnyCorp (with one vending machine and
 * strange manager)
 * 
 * @author cstamas
 * 
 */
public abstract class Example1Base extends TestCase {

    protected IniSecurityManagerFactory config;

    protected SecurityManager securityManager;

    protected PersonDao personDao;

    protected PersonRoleDao personRoleDao;

    protected VendingMachine vendingMachine;

    protected void setUp() throws Exception {
	super.setUp();

	config = new IniSecurityManagerFactory(getIniPath());

	securityManager = config.getInstance();

	SecurityUtils.setSecurityManager(securityManager);

	personDao = (PersonDao) config.getBeans().get("personDao");

	personRoleDao = (PersonRoleDao) config.getBeans().get("personRoleDao");

	vendingMachine = new ProtectedVendingMachine(new SimpleVendingMachine());
    }

    protected void tearDown() throws Exception {
	super.tearDown();
    }

    // Protected resources simulated with canI* methods

    protected boolean canIHaveCoffe(Subject subject) {
	try {
	    System.out.print(String.format("[%s] asked for coffee...",
		    subject.getPrincipal()));
	    vendingMachine.serveCoffe();
	    System.out.println(" served.");
	    return true;
	} catch (UnauthorizedException e) {
	    System.out.println(" not authorized to get some.");
	    return false;
	}
    }

    protected boolean canIHaveCoke(Subject subject) {
	try {
	    System.out.print(String.format("[%s] asked for coke...",
		    subject.getPrincipal()));
	    vendingMachine.serveCoke();
	    System.out.println(" served.");
	    return true;
	} catch (UnauthorizedException e) {
	    System.out.println(" not authorized to get some.");
	    return false;
	}
    }

    protected boolean canIHaveBeer(Subject subject) {
	try {
	    System.out.print(String.format("[%s] asked for beer...",
		    subject.getPrincipal()));
	    vendingMachine.serveBeer();
	    System.out.println(" served.");
	    return true;
	} catch (UnauthorizedException e) {
	    System.out.println(" not authorized to get some.");
	    return false;
	}
    }

    // Utility methods for checks

    protected boolean checkRules(Person person, Subject subject) {
	try {
	    // rule1: girls may have coffee
	    boolean rule1satisfied = Gender.FEMALE.equals(person.getGender()) == canIHaveCoffe(subject);

	    // rule2: boys may have coke
	    boolean rule2satisfied = Gender.MALE.equals(person.getGender()) == canIHaveCoke(subject);

	    // rule3: merited employees may have beer
	    boolean rule3satisfied = person.getBadgeNo() <= 10 == canIHaveBeer(subject);

	    Assert.assertTrue(rule1satisfied && rule2satisfied
		    && rule3satisfied);

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

    protected void assertChecks(int expectedFailures)
	    throws AssertionFailedError {
	Set<String> failures = checkAllPersonRules();

	if (expectedFailures != failures.size()) {
	    throw new AssertionFailedError("We expected " + expectedFailures
		    + " failures, but got " + failures.size()
		    + " ones, with people:" + failures.toString());
	}
    }

    // Test preparation methods

    protected abstract String getIniPath();

    protected abstract void applyManagerDecrete();

    protected void populateInitialEmployees() {
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
    }

    protected abstract void populateInitialRoles();

    protected void performEmployeeChanges() {
	// fire some of them (this case is not really working, since fired will
	// be removed
	// from DB, hence, will not be able to login at all and will not be
	// checked at all)
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
    }

    // === The test (same for both cases, we will override or implement
    // differently some methods only)

    public void testFunnyCorp() {
	populateInitialEmployees();

	// check them: they should be all able to login (PersonDao does have
	// their creds)
	// but they have not any perms at all, so all our employee database must
	// fail
	assertChecks(personDao.findAll().size());

	// now populate the roles
	populateInitialRoles();

	// apply manager decrete, but updating Persons and Roles databases
	// (PersonDao and PersonRoleDao)
	applyManagerDecrete();

	// check them: they should now all obey the manager decrete
	assertChecks(0);

	// make employee changes
	performEmployeeChanges();

	// check them: they will fail, we modified 4 persons (2 added, 1
	// promoted, 1 gender changed), roles are messed up
	assertChecks(4);

	// to "fix", we need to batch reapply manager decrete (to update person
	// roles)
	applyManagerDecrete();

	// check them: they will pass now
	assertChecks(0);
    }

}
