package com.michaelwillemse.mwrestuser.persistence;

import com.michaelwillemse.mwrestuser.model.User;
import com.michaelwillemse.mwrestuser.utils.MD5Hash;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Michael on 24/08/14.
 */

@Stateless
public class UserDao {
    @PersistenceContext
    EntityManager em;

    public User getUserById(long id){
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getResultList();
        if(users == null || users.size() == 0){
            return null;
        }else {
            return users.get(0);
        }
    }

    public User create(User user) {
        user.setPassword(MD5Hash.getMD5Hash(user.getActualPassword()));
        em.persist(user);
        em.flush();
        return user;
    }

    public User update(long id, User user){
        User userToBeUpdated = getUserById(id);
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setPassword(MD5Hash.getMD5Hash(user.getActualPassword()));
        return em.merge(user);
    }

    public String delete(long id){
        User user = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
        em.remove(user);
        return "ok";
    }

    public List<User> getAllUsers(){
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public List<User> findUsersByName(String name){
        return em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class).setParameter("name", name).getResultList();
    }

    public List<User> findUserByEmail(String email){
        return em.createQuery("SELECT u FROM User u WHERE lower(u.email) LIKE lower(:email)", User.class).setParameter("email", email).getResultList();
    }

    public List<User> findUsersByNameAndEmail(String name, String email){
        return em.createQuery("SELECT u FROM User u WHERE lower(u.email) LIKE :email AND u.name LIKE :name", User.class).setParameter("email", email).setParameter("name", name).getResultList();
    }

    public Boolean passwordCheck(String email, String password){
        String hashedPassword = MD5Hash.getMD5Hash(password);
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE lower(u.email) LIKE lower(:email) AND u.password LIKE :password", User.class).setParameter("email", email).setParameter("password", hashedPassword);
        return query.getResultList().size() == 1;
    }

}
