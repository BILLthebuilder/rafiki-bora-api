package rafikibora.controllers;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import rafikibora.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rafikibora.model.users.Roles;
import rafikibora.services.AccountService;
import rafikibora.services.RoleService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping
    public ResponseEntity<?> addRole(@Valid @RequestBody Roles roles) {

        Roles newRole = service.saveRole(roles);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newEmployeeURI = ServletUriComponentsBuilder.fromCurrentRequest() // get the URI for this request
                .path("/{id}") // add to it a path variable
                .buildAndExpand(newRole.getRoleId()) // populate that path variable with the newly created restaurant id
                .toUri(); // convert that work into a human readable URI
        responseHeaders.setLocation(newEmployeeURI); // in the header, set the location location to that URI

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }


    @GetMapping
    public List<Roles> findAllRoles() {
        return service.getRoles();
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Roles> findRoleById(@PathVariable ("roleId") long roleId) {
        return (ResponseEntity<Roles>) service.getRoleById(roleId);
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<Roles> findRoleByName(@PathVariable @Valid String roleName) {
        return (ResponseEntity<Roles>) service.getRoleByName(roleName);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateRole(@RequestBody Roles roles, @PathVariable int roleId) {
        service.updateRole(roles, roleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable int id) {
        service.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

