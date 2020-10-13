package rafikibora.dto;

public class TerminalDto {
    String modelType;
    String serialNumber;
    String Id;
//    String DateCreated;
//    String Deleted;
//    String Status;
//    String DateUpdated;


    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}