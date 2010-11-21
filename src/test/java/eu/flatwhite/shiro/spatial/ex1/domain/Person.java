package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple person abstraction. This class would be part of Shiro integrator's
 * EIS implementation. This is "below" Shiro, and is just helping interfacing
 * with it.
 * 
 * @author cstamas
 */
public class Person {

    public enum Gender {
	FEMALE, MALE
    };

    private final String username;

    private String password;

    private String fullName;

    private Gender gender;

    private int badgeNo;

    private final Set<String> roles;

    public Person(final String username, final String password,
	    final String fullName, final Gender gender, final int badgeNo,
	    final Set<String> roles) {
	this.username = username;
	this.password = password;
	this.fullName = fullName;
	this.gender = gender;
	this.badgeNo = badgeNo;
	this.roles = new HashSet<String>();

	if (roles != null) {
	    this.roles.addAll(roles);
	}
    }

    public String getUsername() {
	return username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public int getBadgeNo() {
	return badgeNo;
    }

    public void setBadgeNo(int badgeNo) {
	this.badgeNo = badgeNo;
    }

    public Set<String> getRoles() {
	return roles;
    }
}
