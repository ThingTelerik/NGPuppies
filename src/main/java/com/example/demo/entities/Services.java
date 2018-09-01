package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "name")
    private String  name;



    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL, targetEntity = Subscriber.class)
    @JsonIgnore
    private Collection<Subscriber> subscribers;

    public Services() {
    }

    public Services(@NotNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Collection<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }


}
