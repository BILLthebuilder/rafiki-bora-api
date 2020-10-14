package rafikibora.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.SupportDto;
import rafikibora.dto.TerminalDto;
import rafikibora.model.support.Support;
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.SupportRepository;
import rafikibora.repository.TerminalRepository;
import rafikibora.services.SupportService;
import rafikibora.services.TerminalService;

import java.util.List;
@RestController
@Slf4j
@RequestMapping("/api/support")
public class SupportController {
        @Autowired
        private SupportService supportService;
        private SupportRepository supportRepository;

        //Create support
        @PostMapping
        public ResponseEntity<?> create(@RequestBody Support support) {
            System.out.println(support.toString());
            Support s = supportService.save(support);
            return new ResponseEntity<Support>(s, HttpStatus.CREATED);
        }

        //List support
        @GetMapping(produces = {"application/json"})
        public ResponseEntity<List<Support>> list() {
            List<Support> support = supportService.list();
            return new ResponseEntity<>(support, HttpStatus.OK);
        }

        //List by Id

        @GetMapping(value = "/{id}", produces = {"application/json"})
        public ResponseEntity<Support> listOne(@PathVariable("id") Long id) {
            System.out.println(id.toString());
            Support support = supportService.getById(id);
            return new ResponseEntity<>(support, HttpStatus.OK);
        }



        //Update support by Id

        @PatchMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
        public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody SupportDto supportDto) {
            System.out.println(id.toString());
            supportService.update(id, supportDto);
            return new ResponseEntity<>("Support updated successfully", HttpStatus.OK);
        }



        //Delete support by Id

        @DeleteMapping(value = "/{id}")
        public ResponseEntity<String> delete(@PathVariable("id") Long id) {
            supportService.deleteById(id);
            return new ResponseEntity<>("Support deleted successful", HttpStatus.OK);
        }


    }


