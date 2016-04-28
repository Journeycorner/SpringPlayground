package de.medhelfer.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private EntityManager em;

    @Autowired
    public UserServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public Collection<User> findAllUsers() {
        return em.createNamedQuery(User.QUERY_FIND_ALL, User.class)
                .getResultList();
    }
}
