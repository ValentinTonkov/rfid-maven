package eu.tonkov.rfidmaven.entities;

public record RfidEntry(String operator, String device_ip, String rfid_ex) {
    @Override
    public String toString() {
        return  "operator='" + operator + '\'' +
                ", device_ip='" + device_ip + '\'' +
                ", rfid_ex='" + rfid_ex;
    }
}