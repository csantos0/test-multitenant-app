package br.com.synchro.service;

import java.io.Serializable;

import br.com.synchro.dao.OrgTenantDao;
import br.com.synchro.dao.OrgTenantDaoImpl;
import br.com.synchro.dao.UserDao;
import br.com.synchro.dao.UserDaoImpl;
import br.com.synchro.domain.User;
import br.com.synchro.tenant.resolver.FacesTenantResolver;

/**
 * @author cvs
 * @create Jul 8, 2015
 * 
 *         Simple Service implementation for Login operations
 */
public class LoginServiceImpl implements LoginService, Serializable {

    private static final long serialVersionUID = 1L;

    private UserDao userDao;

    private OrgTenantDao orgTenantDao;

    /**
     * Default Constructor to instantiate attributes
     */
    public LoginServiceImpl() {
	this.userDao = new UserDaoImpl();
	this.orgTenantDao = new OrgTenantDaoImpl();
    }

    /*
     * (non-Javadoc)
     * @see br.com.synchro.service.LoginService#validate(java.lang.String, java.lang.String)
     */
    @Override
    public User doLogin(final String pUser, final String pPassword) {
	final User user = this.userDao.validateUser(pUser, pPassword);
	if (user == null) {
	    return null;
	}
	final String tenantName = this.orgTenantDao.getTenantFromOrg(user.getOrganization().getId());
	FacesTenantResolver.begin(tenantName);
	return user;
    }

    /*
     * (non-Javadoc)
     * @see br.com.synchro.service.LoginService#doLogout()
     */
    @Override
    public void doLogout() {
	FacesTenantResolver.end();
    }
}
