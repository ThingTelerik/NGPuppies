package com.example.demo.config;

import com.example.demo.data.GenericRepository;
import com.example.demo.data.HibernateRepository;
import com.example.demo.data.HibernateUtils;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class DIConfig {

    @Bean(name = "User")
    @Autowired
    public GenericRepository<User> provideUserGenericRepository(SessionFactory sessionFactory){
        GenericRepository<User> userRepo = new HibernateRepository<>(sessionFactory);
         userRepo.setEntityClass(User.class);
         return userRepo;
    }

    @Bean
    @Autowired
    public GenericRepository<Role> provideRoleGenericRepository(SessionFactory sessionFactory){
        GenericRepository<Role> roleRepo = new HibernateRepository<>(sessionFactory);
        roleRepo.setEntityClass(Role.class);

        return roleRepo;

    }
    @Bean
    public SessionFactory  provideSessionFactory(){
        return HibernateUtils.sessionFactory;
    }
}
