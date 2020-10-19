package rafikibora.services;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.CustomUserDetails;
import rafikibora.dto.SignupResponse;
import rafikibora.dto.TerminalDto;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.User;
import rafikibora.repository.TerminalRepository;
import rafikibora.security.util.exceptions.ExceptionUtilService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class TerminalService implements TerminalInterface {
    @Autowired
    private TerminalRepository terminalRepository;
    /**
     Generate A Unique TID
     */

    public String createTID(){
        return UUID.randomUUID().toString().substring(0,16);
    }
//
//    //Generate a unique MID
//    public String createMID(){
//        return UUID.randomUUID().toString().substring(0,16);
//    }

    /**
     Create Terminal
     */

    @Transactional
    public Terminal save(Terminal terminal) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
//      Terminal terminal = new Terminal();
        terminal.setModelType(terminal.getModelType());
        terminal.setSerialNo(terminal.getSerialNo());
        terminal.setDeleted(false);
        terminal.setCreatedOn((LocalDateTime.now()));
        terminal.setUpdatedOn((LocalDateTime.now()));
        terminal.setTid(createTID());
//        terminal.setMid(user.getUser());
//        terminal.setMid(user.getUser());
//        terminal.setMid(createMID());
        terminal.setTerminalMaker(user.getUser());
//        return terminalRepository.save(terminal);


        //Catching Entry of Duplicate Values.

        try {
            terminalRepository.save(terminal);
        } catch (JpaSystemException e) {
            if (ExceptionUtilService.isSqlDuplicatedModelType(e)) {
                throw new Exception("Duplicate Entry is not Allowed");
            } else {}
            }
        return terminal;
        }

        /**
     List All Terminal
     */
    @Transactional
    public List<Terminal> list() {
        return terminalRepository.findAll();

    }

    //List All Unassigned Terminals
    @Transactional
    public List<Terminal> unassignedTerminals() {
      return  terminalRepository.findByMid_MidIsNull();
    }


    /**
     List Terminal by ID
     */

    @Transactional
    public Terminal getById(Long id) {
        Terminal terminal = terminalRepository.findById(id).get();
        return terminal;

    }


    /**
     Update Terminal by ID
     */


    @Transactional
    public void update(Long id, TerminalDto terminalDto) {
        Terminal terminal = terminalRepository.findById(id).get();
        if (terminalDto.getModelType() != null) {
            terminal.setModelType(terminalDto.getModelType());
        }
        if (terminalDto.getSerialNumber() !=null) {
            terminal.setSerialNo(terminalDto.getSerialNumber());
        }
        terminalRepository.save(terminal);
    }


    /**
     Approve Terminal by ID
     */

    @Transactional
    public void approve(TerminalDto terminalDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.parseLong(terminalDto.getId());
        Terminal terminal = terminalRepository.findById(id).get();
        Long checkerId = user.getUser().getUserid();
        Long makerId = terminal.getTerminalMaker().getUserid();
        System.out.println("******************************************");
        System.out.println("Maker id: "+makerId);
        System.out.println("Cehecker id: "+checkerId);
        System.out.println("******************************************");
        if (checkerId.equals(makerId))
            throw new Exception("Creator of resource is not allowed to approve.");
        else {
            terminal.setTerminalChecker(user.getUser());
            terminal.setStatus(true);
            terminalRepository.save(terminal);
            // }
        }
    }


    /**
     Delete Terminal by ID
     */

    @Transactional
    public void deleteById(Long id) {
        try {
            terminalRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){

        }

    }


}
