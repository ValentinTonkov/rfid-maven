package eu.tonkov.rfidmaven.rfid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RfidController {
    private RfidEntry lastEntry = null;
    private final RfidRepository repository;
    public RfidController(RfidRepository repository){
        this.repository = repository;
    }
    @RequestMapping
    public String getScannedRfidInfo(){
        if (repository.count() > 0) {
            StringBuilder builder = new StringBuilder();
            repository.findAll().forEach((entry -> builder.append(entry.toString()).append("<br>")));
            return builder.toString();
        } else {
            return "<center><h2>No scanned tags yet!</h2></center>";
        }
    }

    @RequestMapping("/lastScanned")
    public String getLastScannedTag(){
        if (lastEntry != null) {
            return "<center><h2>" + lastEntry + "</h2></center>";
        }
        return "<center><h2>No scanned tags yet!</h2></center>";
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

