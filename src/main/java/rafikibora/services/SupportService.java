package rafikibora.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rafikibora.dto.CustomUserDetails;
import rafikibora.dto.SupportDto;
import rafikibora.dto.TerminalDto;
import rafikibora.model.support.Support;
import rafikibora.model.terminal.Terminal;
import rafikibora.repository.SupportRepository;
import rafikibora.repository.TerminalRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class SupportService {
    @Autowired
    private SupportRepository supportRepository;

    //Create Tid
    public String createTID(){
        return UUID.randomUUID().toString().substring(0,16);
    }

    //Create Support
    @Transactional
    public Support save(Support support) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        support.setName(support.getName());
        support.setReason(( support.getReason()));
        support.setTid(createTID());
        support.setDate(new Date());
        return supportRepository.save(support);
    }

    //List All Support
    @Transactional
    public List<Support> list() {
        return supportRepository.findAll();

    }


    //List Support by Id
    @Transactional
    public Support getById(Long id) {
        Support support = supportRepository.findById(id).get();
        return support;

    }


    //update Support by Id
    @Transactional
    public void update(Long id, SupportDto supportDto) {
        Support support = supportRepository.findById(id).get();
        if (supportDto.getName() != null) {
            support.setName(supportDto.getName());
        }
        if (supportDto.getReason() !=null) {
            support.setReason(supportDto.getReason());
        }
        supportRepository.save(support);

    }


    //Delete Support by Id
    @Transactional
    public void deleteById(Long id) {
        try {
            supportRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){

        }

    }


}


