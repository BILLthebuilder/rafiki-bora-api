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
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.TerminalRepository;
import rafikibora.services.TerminalService;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;
    private TerminalRepository terminalRepository;

//Create Terminal
    @PostMapping(value = "/", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> create(@RequestBody TerminalDto terminalDto) {
        System.out.println(terminalDto.toString());
        terminalService.save(terminalDto);
        return new ResponseEntity<>("Terminal creation successful", HttpStatus.CREATED);
    }

//List All Terminals
    @GetMapping(value = "/",consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<List<Terminal>> list() {
        List<Terminal> terminals = terminalService.list();
        return new ResponseEntity<>(terminals, HttpStatus.ACCEPTED);
    }

    //List by Id

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Terminal> listOne(@PathVariable("id") Long id) {
        System.out.println(id.toString());
        Terminal terminal = terminalService.getById(id);
        return new ResponseEntity<>(terminal, HttpStatus.ACCEPTED);
    }





//Update Terminal by Id
    @PatchMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody TerminalDto terminalDto) {
        System.out.println(id.toString());
        terminalService.update(id, terminalDto);
        return new ResponseEntity<>("Terminal updated successfully", HttpStatus.ACCEPTED);
    }


    //Delete Terminal by Id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        terminalService.deleteById(id);
        return new ResponseEntity<>("Terminal deletion successful", HttpStatus.ACCEPTED);
    }


}






