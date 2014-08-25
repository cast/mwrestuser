package com.michaelwillemse.mwrestuser.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 22/08/14.
 */
@ApplicationPath("/")
public class UserApplication extends Application
{
    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(UserRestService.class);
        return s;
    }
}