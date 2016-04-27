package de.medhelfer.data;

import java.util.Collection;

public interface UserService {
    void addUser(User user);
    Collection<User> findAllUsers();
}
