package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.services.DataSnapshotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/snapshots")
public class DataSnapshotsController {
    @Autowired
    private DataSnapshotsService dataSnapshotsService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<Data_Snapshots> getAllDataSnapshots() {
        return dataSnapshotsService.getAllDataSnapshots();
    }

    @PostMapping("/create")
    public Data_Snapshots createDataSnapshot(@RequestBody Data_Snapshots dataSnapshot) {
        return dataSnapshotsService.createDataSnapshot(dataSnapshot);
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
