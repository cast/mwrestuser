package com.michaelwillemse.mwrestuser;

import com.michaelwillemse.mwrestuser.model.User;
import com.michaelwillemse.mwrestuser.persistence.UserDao;
import com.michaelwillemse.mwrestuser.rest.Result;
import com.michaelwillemse.mwrestuser.rest.UserApplication;
import com.michaelwillemse.mwrestuser.rest.UserRestService;
import com.michaelwillemse.mwrestuser.utils.MD5Hash;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.io.File;

/**
 * Created by Michael on 25/08/14.
 */
@RunWith(Arquillian.class)
public class UserServiceTest {
    @Inject
    private UserDao userDao;

    @Deployment
    public static WebArchive createDeployment() {
        File[] lib = Maven.resolver()
                .resolve("commons-codec:commons-codec:1.9")
                .withTransitivity().as(File.class);
        return ShrinkWrap.create(WebArchive.class)
                .addClass(User.class)
                .addClass(UserDao.class)
                .addClass(UserApplication.class)
                .addClass(UserRestService.class)
                .addClass(MD5Hash.class)
                .addClass(Result.class)
                .addAsLibraries(lib)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void createDeleteTest(){
        User user = new User("tom", "tom@gmail.com", "passwd");
        userDao.create(user);
        Assert.assertNotNull(user.getId());
        userDao.delete(user.getId());
        Assert.assertNull(userDao.getUserById(user.getId()));
    }

    @Test(expected = EJBException.class)
    public void testUniqueness(){
        User user = new User("test1", "test@gmail.com", "passwd");
        userDao.create(user);
        User user2 = new User("test2", "test@gmail.com", "passwd2");
        userDao.create(user2);
    }

    @Test
    public void showNoPasswordTest(){
        User user = new User("rudy", "rudy@gmail.com", "passwd");
        Assert.assertEquals(user.getPassword(), "");
    }

    @Test
    public void checkpasswordTest() {
        User user = new User("michael", "michael@gmail.com", "passwd");
        user = userDao.create(user);
        Assert.assertTrue(userDao.passwordCheck("michael@gmail.com", "passwd"));
    }
}
