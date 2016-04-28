package de.medhelfer.data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.QUERY_FIND_ALL,
                query = "SELECT u " +
                        "FROM User u"),
        @NamedQuery(name = User.QUERY_FIND_BY_USERNAME,
                query = "SELECT u " +
                        "FROM User u " +
                        "WHERE username LIKE :username")
})
public class User {

    public final static String QUERY_FIND_ALL = "User.findAll";
    public final static String QUERY_FIND_BY_USERNAME = "User.findByUsername";
    public final static String PARAM_USERNAME = ":username";

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "users_role")
    @Column(name = "role")
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
