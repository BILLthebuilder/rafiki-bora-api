package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalDto {

    String modelType;
    String serialNumber;
    String id;
//    String DateCreated;
//    String Deleted;
//    String Status;
//    String DateUpdated;

}