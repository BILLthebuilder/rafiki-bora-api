package rafikibora.services;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.TerminalDto;
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.TerminalRepository;

import javax.transaction.Transactional;
import java.util.*;


@Service
@AllArgsConstructor
public class TerminalService implements TerminalInterface {
    @Autowired
    private TerminalRepository terminalRepository;


    //Create Terminal
    @Transactional
    public void save(TerminalDto terminalDto) {
        Terminal terminal = new Terminal();
        terminal.setModelType(terminalDto.getModelType());
        terminal.setSerialNo(terminalDto.getSerialNumber());
        terminal.setDeleted(false);
        terminal.setDateCreated(new Date());
        terminal.setDateUpdated(new Date());
        terminal.setStatus(true);
        terminal.setTid("jfhthakj");
        terminal.setTid("ghtyg");
        terminal.setTid("tttt");
        terminalRepository.save(terminal);

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
    public void delete(TerminalDto terminalDto) {
        Long id = Long.parseLong(terminalDto.getId());
        terminalRepository.deleteById(id);
        terminalRepository.deleteAll();
    }


}
