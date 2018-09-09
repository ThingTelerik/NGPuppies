package com.example.demo.services;

import com.example.demo.data.AdminRepository;
import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.SignupAdminRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.base.AdminService;
import com.example.demo.services.base.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements GenericService<Admin, String>, AdminService {

    private AdminRepository adminRepository;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;

    private ClientRepository clientRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.clientRepository = clientRepository;
    }


    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin create(Admin entity) {
        return adminRepository.save(entity);
    }

    @Override
    public void delete(Admin entity) {
        adminRepository.delete(entity);
    }

    public  void deleteByID(Long id){
        adminRepository.deleteById(id);
    }

    @Override
    public Admin update(Admin entity, String param) {

        if (!adminRepository.existsByEmail(param)) {
            throw new ResourceNotFoundException("Admin", "Email", param);
        }
        Admin admin = adminRepository.findByEmail(param);
        admin.setUsername(entity.getUsername());
        admin.setPassword(passwordEncoder.encode(entity.getPassword()));
        admin.setEmail(entity.getEmail());

        return adminRepository.save(admin);

    }

    public ResponseEntity deleteClient(String username) {
        if (clientRepository.existsByUsername(username)) {
            clientRepository.deleteUserByUsername(username);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<?> registerAdmin( SignupAdminRequest signUpAdminRequest) {
        if (adminRepository.existsByEmail(signUpAdminRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (adminRepository.existsByUsername(signUpAdminRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already used!"),
                    HttpStatus.BAD_REQUEST);
        }

        Admin admin = new Admin(signUpAdminRequest.getUsername(), signUpAdminRequest.getPassword(), signUpAdminRequest.getEmail());

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));


        Role role = roleRepository.findAll().stream()
                .filter(x -> x.getRoleType().equals(RoleType.ROLE_ADMIN))
                .findFirst()
                .orElse(null);

        if (role == null) {
            role = new Role(RoleType.ROLE_ADMIN);
            roleRepository.save(role);
        }

        role.getUsers().add(admin);
        admin.setRole(role);

        Admin saveAdmin = adminRepository.save(admin);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/admins/{username}")
                .buildAndExpand(saveAdmin.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Client successfully registered"));
    }
}
