package com.example.demo.services;

import com.example.demo.data.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImplement  {
    private static final String INVALID_USER = "Invalid user";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  RoleRepository roleRepository;

    public UserServiceImplement() {
    }
//
//    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
//        List<Role> roles = roleRepository.findAll();
//        List<User> users = userRepository.findAll();
//
//        if(
//                //findByUsername(signUpRequest.getUsername())
//                users.stream()
//                .anyMatch(user -> user.getUsername().equals(signUpRequest.getUsername()))){
//            return new ResponseEntity<>(new ApiResponse(false, "Username already taken!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//        if(users.stream()
//                .anyMatch(user -> user.getEmail().equals(signUpRequest.getEmail()))){
//            return new ResponseEntity<>(new ApiResponse(false, "Email already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//
//        User userToRegister = new User(signUpRequest.getUsername(), signUpRequest.getPassword());
//
//       userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
//
//        Role role = roles.stream()
//                .filter(x->x.getName().equalsIgnoreCase("user"))
//                .findFirst()
//                .orElse(null);
//
//        if(role==null){
//            role = new Role();
//            role.setName("user");
//            roleRepository.save(role);
//        }
//        role.setUsers(Collections.singleton(userToRegister));
//        userToRegister.setRole(role);
//
//
//       User result = userRepository.save(userToRegister);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("api/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
//
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User successfully registered"));
//    }



//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<User> allUsers = userRepository.findAll();
//
//        User user = allUsers.stream()
//                .filter(x->x.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//
//        if(user ==null){
//            throw  new UsernameNotFoundException("User not found");
//
//        }
//
//        Role role = user.getRole();
//
//        ///Set<SimpleGrantedAuthority> granatedAuthorities =  Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//
//        return CustomUserDetails.create(user);
//
//    }

//    public UserDetails loadUserById(Long id){
////        User user = userRepository.findById(id)
////                .orElseThrow(
////                        NoSuchElementException::new
////                );
//
//        if(user ==null){
//            throw new IllegalArgumentException("User not found by id");
//        }
//
//        return CustomUserDetails.create(user);
//    }
}
