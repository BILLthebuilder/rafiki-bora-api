package rafikibora.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.Response;
import rafikibora.dto.TerminalDto;
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.TerminalRepository;
import rafikibora.services.TerminalInterface;
import rafikibora.services.TerminalService;

import java.util.List;


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
        Response response;
        try{
            terminalService.save(terminal);
            response = new Response(Response.responseStatus.SUCCESS,"Terminal created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            response = new Response(Response.responseStatus.SUCCESS,"Duplicate Entry is not Allowed!!");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

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
     List all terminals for a given merchant not assigned to an agent
     */

    @GetMapping(value ="/merchant/{merchantID}/unassigned",produces = {"application/json"})
    public ResponseEntity<List<Terminal>> unassignedTerminals(@PathVariable("merchantID") String merchantID) {
        List<Terminal> terminals = terminalService.agentUnassignedTerminals(merchantID);
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
    public ResponseEntity<?> approve(@PathVariable("id") Long id) {
        Response response;
        terminalService.approve(id);
        response = new Response(Response.responseStatus.SUCCESS,"Terminal approved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
        //System.out.println(terminalDto.getId());
//        System.out.println("================================ " + id);
//        terminalService.approve(id);
//        return new ResponseEntity<>("Terminal approved successfully", HttpStatus.OK);
    }


    /**
     Delete Terminal by ID
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Response response;
        terminalService.deleteById(id);
        response = new Response(Response.responseStatus.SUCCESS, "Terminal Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}






