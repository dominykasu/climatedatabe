package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.services.DataSnapshotsService;
import lt.ca.javau10.climatedata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data/snapshots")
public class DataSnapshotsController {

    @Autowired
    private DataSnapshotsService dataSnapshotsService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<Data_Snapshots> getAllDataSnapshots() {
        return dataSnapshotsService.getAllDataSnapshots();
    }

    @PostMapping("/create")
    public Data_Snapshots createDataSnapshot(@RequestBody Map<String, Object> payload) {

        String region = (String) payload.get("region");
        String metric = (String) payload.get("metric");
        List<Map<String, Object>> values = (List<Map<String, Object>>) payload.get("value");
        String username = (String) payload.get("username");

        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }

        Data_Snapshots dataSnapshot = new Data_Snapshots(region, metric, values, LocalDate.now(), user);

        return dataSnapshotsService.createDataSnapshot(dataSnapshot);
    }
    @GetMapping("/all/{username}")
    public List<Data_Snapshots> getSnapshotsByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        return dataSnapshotsService.getSnapshotsByUser(user);
    }

    @GetMapping("/{id}")
    public Data_Snapshots getDataSnapshot(@PathVariable Long id) {
        return dataSnapshotsService.getDataSnapshotById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteDataSnapshot(@PathVariable Long id) {
        dataSnapshotsService.deleteDataSnapshot(id);
    }
}