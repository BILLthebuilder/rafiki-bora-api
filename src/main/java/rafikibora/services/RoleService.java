package rafikibora.services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.dto.Response;
import rafikibora.exceptions.ResourceNotFoundException;
import rafikibora.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rafikibora.model.users.Roles;
import rafikibora.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional
    public Roles saveRole(Roles roles) {
        return repository.save(roles);
    }

    public List<Roles> getRoles() {
        return repository.findAll();
    }

    public ResponseEntity<?> getRoleById(int id) {
        Response response;
        Optional<Roles> optional = repository.findById((long) id);
        Roles roles = null;
        if (optional.isPresent()) {
            roles = optional.get();
        } else {
            response = new Response(Response.responseStatus.FAILED," Role not found for id :: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    public ResponseEntity<?> getRoleByName(String name) {
        Response response;
        Optional <Roles> optional = repository.findByName(name);
        Roles roles = null;
        if (optional.isPresent()) {
            roles = optional.get();
            //response = new Response(Response.responseStatus.SUCCESS,"Successful account");
        } else {
//            throw new RuntimeException(" Account not found for name :: " + name);
            response = new Response(Response.responseStatus.FAILED,"No role found for :: " + name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Transactional
    public void deleteRole(int id) {
        if ( repository.findById((long) id).isPresent()) {
            repository.deleteById((long) id);
        } else {
            throw new ResourceNotFoundException("Roles " + id + " Not Found");
        }
    }

    @Transactional
    public Roles updateRole(Roles roles, int roleid) {
        Roles existingRoles = repository.findById((long) roleid).
                orElseThrow(
                        () -> new ResourceNotFoundException
                                ("Roles " + roleid + " Not Found"));

        if (roles.getRoleName() != null) {
            existingRoles.setRoleName(roles.getRoleName());
        }


        return repository.save(existingRoles);
    }


}
