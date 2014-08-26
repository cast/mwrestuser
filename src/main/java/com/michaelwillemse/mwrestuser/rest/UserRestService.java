package com.michaelwillemse.mwrestuser.rest;

import com.michaelwillemse.mwrestuser.model.User;
import com.michaelwillemse.mwrestuser.persistence.UserDao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Michael on 22/08/14.
 */

@Path("/users")
@RequestScoped
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
    public Result delete(@PathParam("id") long id){
        userService.delete(id);
        return new Result("ok");
    }

    /*Only one GET per endpoint, different query params still cause errors because of ambiguity. Promotes RPC design */
    @GET
    public List<User> findByNameOrEmail(@QueryParam("name") String name,@QueryParam("email") String email){
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
    @Produces(MediaType.APPLICATION_JSON)
    public Result passwordCheck(@QueryParam("email") String email,@QueryParam("pwd") String password){
        return new Result(userService.passwordCheck(email, password).toString());
    }

}
