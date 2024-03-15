package eu.tonkov.rfidmaven.rfid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class RfidEntry {
    private String operator;
    private String device_ip;

    @Id
    private String rfid_ex;

    protected RfidEntry(){}

    public String getOperator() {
        return operator;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public String getRfid_ex() {
        return rfid_ex;
    }

    public RfidEntry(String operator, String device_ip, String rfid_ex) {
        this.operator = operator;
        this.device_ip = device_ip;
        this.rfid_ex = rfid_ex;
    }

    @Override
    public String toString() {
        return "RfidEntry{" +
                "operator='" + operator + '\'' +
                ", device_ip='" + device_ip + '\'' +
                ", rfid_ex='" + rfid_ex + '\'' +
                '}';
    }
}