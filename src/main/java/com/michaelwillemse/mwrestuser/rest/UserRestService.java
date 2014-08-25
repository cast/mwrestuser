package com.michaelwillemse.mwrestuser.rest;

import com.michaelwillemse.mwrestuser.model.User;
import com.michaelwillemse.mwrestuser.persistence.UserDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Michael on 22/08/14.
 */

@Path("/users")
@Stateless
public class UserRestService {
    @Inject
    UserDao userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public User getUserById(@PathParam("id") long id){
        return userService.getUserById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user) {
        return userService.create(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public User update(@PathParam("id") long id, User user){
        return userService.update(id, user);
    }

    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") long id){
        userService.delete(id);
        return "ok";
    }

    /*Only one GET per endpoint, different query params still cause errors because of ambiguity. Promotes RPC design */
    @GET
    public List<User> findByNameOrEmail(@QueryParam("name") String name,@QueryParam("email") String email){
        TypedQuery<User> query;
        if(email != null && name != null){
            return userService.findUsersByNameAndEmail(name, email);
        }else if(email != null){
            return userService.findUserByEmail(email);
        }else if(name != null){
            return userService.findUsersByName(name);
        }else{
            return userService.getAllUsers();
        }
    }

    @Path("/pwdcheck")
    @GET
    public Boolean passwordCheck(@QueryParam("email") String email,@QueryParam("pwd") String password){
        return userService.passwordCheck(email, password);
    }

}