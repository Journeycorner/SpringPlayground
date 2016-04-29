package de.medhelfer.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private EntityManager em;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(EntityManager em, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
        findAllUsers();
    }

    @Override
    public Collection<User> findAllUsers() {
        return em.createNamedQuery(User.QUERY_FIND_ALL, User.class)
                .getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return em.createNamedQuery(User.QUERY_FIND_BY_USERNAME, User.class)
                .setParameter(User.PARAM_USERNAME, username)
                .getSingleResult();
    }
}
