package eu.flatwhite.shiro.spatial;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;

public abstract class AbstractShiroFullConfigTest extends
	AbstractShiroExtrasTest {

    protected IniSecurityManagerFactory config;

    protected SecurityManager securityManager;

    protected SubjectThreadState shiroThreadState;

    protected void setUp() throws Exception {
	super.setUp();

	config = new IniSecurityManagerFactory(getIniPath());

	securityManager = config.getInstance();

	Subject testSubject = new Subject.Builder(securityManager)
		.buildSubject();

	shiroThreadState = new SubjectThreadState(testSubject);

	shiroThreadState.bind();

	SecurityUtils.setSecurityManager(securityManager);
    }

    protected void tearDown() throws Exception {
	shiroThreadState.clear();

	super.tearDown();
    }

    protected abstract String getIniPath();
}
