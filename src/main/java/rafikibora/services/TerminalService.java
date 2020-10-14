package rafikibora.services;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.CustomUserDetails;
import rafikibora.dto.TerminalDto;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.User;
import rafikibora.repository.TerminalRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;


@Service
@AllArgsConstructor
public class TerminalService implements TerminalInterface {
    @Autowired
    private TerminalRepository terminalRepository;

    //Create Tid
    public String createTID(){
        return UUID.randomUUID().toString().substring(0,16);
    }

    //Create Terminal
    @Transactional
    public Terminal save(Terminal terminal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
//      Terminal terminal = new Terminal();
        terminal.setModelType(terminal.getModelType());
        terminal.setSerialNo(terminal.getSerialNo());
        terminal.setDeleted(false);
        terminal.setDateCreated(new Date());
        terminal.setDateUpdated(new Date());
        terminal.setStatus(true);
        terminal.setTid(createTID());
        terminal.setTerminalMaker(user.getUser());
        return terminalRepository.save(terminal);
    }

    //List All Terminals
    @Transactional
    public List<Terminal> list() {
        return terminalRepository.findAll();

    }



//List Terminal by Id
    @Transactional
    public Terminal getById(Long id) {
        Terminal terminal = terminalRepository.findById(id).get();
        return terminal;

    }




    //update Terminal by Id
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


    //Delete Terminal by Id
    @Transactional
    public void deleteById(Long id) {
        try {
            terminalRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){

        }

    }


}