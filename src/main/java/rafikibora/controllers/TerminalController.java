package rafikibora.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.TerminalDto;
import rafikibora.exceptions.NotFoundException;
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.TerminalRepository;
import rafikibora.services.TerminalInterface;
import rafikibora.services.TerminalService;

import java.awt.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/api/terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;
    private TerminalRepository terminalRepository;
    private TerminalInterface terminalInterface;

    /**
     Create Terminal
     */

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Terminal terminal) {
        System.out.println(terminal.getId());
        String msg = "";
        try{
            terminalService.save(terminal);
            msg = "Terminal created successfully";
        }catch (Exception ex){
            msg = "Duplicate Entry is not Allowed!!";
        }finally {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
//        System.out.println(terminal.toString());
//        Terminal t = terminalService.save(terminal);
//        return new ResponseEntity<Terminal>(t, HttpStatus.CREATED);
    }


    /**
     List All Terminals
     */


    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Terminal>> list() {
        List<Terminal> terminals = terminalService.list();
        return new ResponseEntity<>(terminals, HttpStatus.OK);
    }


    /**
     List All Unassigned Terminals
     */

    @GetMapping(value ="/fetch",produces = {"application/json"})
    public ResponseEntity<List<Terminal>> unassignedTerminals() {
        List<Terminal> terminals = terminalService.unassignedTerminals();
        return new ResponseEntity<>(terminals, HttpStatus.OK);
    }

    /**
     List Terminals by ID
     */

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Terminal> listOne(@PathVariable("id") Long id) {
        System.out.println(id.toString());
        Terminal terminal = terminalService.getById(id);
        return new ResponseEntity<>(terminal, HttpStatus.OK);
    }


    /**
     Update Terminal by ID
     */

    @PatchMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody TerminalDto terminalDto) {
//        System.out.println(id.toString());
        terminalService.update(id, terminalDto);
        return new ResponseEntity<>("Terminal updated successfully", HttpStatus.OK);
    }


    /**
    Approve Terminal by ID
     */

    @PatchMapping(value = "/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable("id") Long id) {
        //System.out.println(terminalDto.getId());
        System.out.println("================================ " + id);
        terminalService.approve(id);
        return new ResponseEntity<>("Terminal approved successfully", HttpStatus.OK);
    }


    /**
     Delete Terminal by ID
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        terminalService.deleteById(id);
        return new ResponseEntity<>("Terminal deletion successful", HttpStatus.OK);
    }


}






