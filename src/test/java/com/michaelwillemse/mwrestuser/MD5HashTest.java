package com.michaelwillemse.mwrestuser;

import com.michaelwillemse.mwrestuser.utils.MD5Hash;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * Created by Michael on 25/08/14.
 */

@RunWith(Arquillian.class)
public class MD5HashTest {
    @Deployment
    public static WebArchive createDeployment() {
        File[] lib = Maven.resolver()
                .resolve("commons-codec:commons-codec:1.9")
                .withTransitivity().as(File.class);

        return ShrinkWrap.create(WebArchive.class)
                .addClass(MD5Hash.class)
                .addAsLibraries(lib)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void md5testPassword(){
        Assert.assertEquals(MD5Hash.getMD5Hash("password"), "5f4dcc3b5aa765d61d8327deb882cf99");
    }
}
