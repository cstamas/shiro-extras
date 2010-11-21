package eu.flatwhite.shiro.spatial.ex1.domain;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

public class ProtectedVendingMachine implements VendingMachine {

    private final VendingMachine target;

    public ProtectedVendingMachine(final VendingMachine target) {
	this.target = target;
    }

    @Override
    public void serveCoffe() {
	Subject subject = SecurityUtils.getSubject();

	if (subject.isPermitted("coffee")) {
	    target.serveCoffe();
	} else {
	    throw new UnauthorizedException("You are not allowed to do that!");
	}
    }

    @Override
    public void serveCoke() {
	Subject subject = SecurityUtils.getSubject();

	if (subject.isPermitted("coke")) {
	    target.serveCoke();
	} else {
	    throw new UnauthorizedException("You are not allowed to do that!");
	}
    }

    @Override
    public void serveBeer() {
	Subject subject = SecurityUtils.getSubject();
	if (subject.isPermitted("beer")) {
	    target.serveBeer();
	} else {
	    throw new UnauthorizedException("You are not allowed to do that!");
	}
    }

}
