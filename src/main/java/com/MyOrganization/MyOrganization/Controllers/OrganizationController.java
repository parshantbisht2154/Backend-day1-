package com.MyOrganization.MyOrganization.Controllers;


import com.MyOrganization.MyOrganization.DTO.LoginDTO;
import com.MyOrganization.MyOrganization.DTO.OrganizationDTO;

import com.MyOrganization.MyOrganization.Model.Organization;
import com.MyOrganization.MyOrganization.Model.OrganizationCredential;
import com.MyOrganization.MyOrganization.Services.OrganizationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

   
    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        Organization newOrganization = organizationService.createOrganization(organizationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrganization);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {
        Optional<Organization> organization = organizationService.getOrganizationById(id);
        return organization.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDto) {
    Optional<OrganizationCredential> credential = organizationService.login(loginDto);
    
    if (credential.isPresent()) {
        return ResponseEntity.ok("Login successful!"); 
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
    }
}

   

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable String id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

   
}

