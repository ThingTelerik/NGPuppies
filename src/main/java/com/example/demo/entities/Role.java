package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull
    @Enumerated()
    private RoleType roleType;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users;

    public Role() {
    }

    public Role(@NotNull RoleType roleType) {
        setRoleType(roleType);
    }

    public Role(Set<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
