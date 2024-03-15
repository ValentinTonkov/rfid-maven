package eu.tonkov.rfidmaven.rfid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RfidController {
    private RfidEntry lastEntry;
    private final RfidRepository repository;
    public RfidController(RfidRepository repository){
        this.repository = repository;
    }
    @RequestMapping
    public String getScannedRfidInfo(){
        StringBuilder builder = new StringBuilder();
        repository.findAll().forEach((entry -> builder.append(entry.toString()).append("<br>")));
        return builder.toString();
    }

    @RequestMapping("/lastScanned")
    public String getLastScannedTag(){
        return "<center><h2>" + lastEntry.toString() + "</h2></center>";
    }


    @PostMapping("/epc")
    public ResponseEntity<String> handleRfidInfoPost(@RequestBody RfidEntry entry){
        System.out.printf("epc: %s, operator: %s, dev_ip: %s\n", entry.getRfid_ex(), entry.getOperator(), entry.getDevice_ip());

        lastEntry = entry;

        final String REFUSED_EPC = "E2000016601301261120A9B1";

        if (entry.getRfid_ex().equals(REFUSED_EPC)){
            System.out.println("Invalid epc!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            repository.save(entry);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

