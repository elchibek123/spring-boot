package java15.springboot.api;

import java15.springboot.entities.Programmer;
import java15.springboot.service.ProgrammerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programmers")
@RequiredArgsConstructor
public class ProgrammerAPI {
    private final ProgrammerService programmerService;

    @GetMapping
    public List<Programmer> findAll() {
        return programmerService.findAll();
    }

    @PostMapping
    public Programmer save(@RequestBody Programmer programmer) {
        return programmerService.save(programmer);
    }

    @PutMapping("/{id}")
    public Programmer update(@PathVariable Long id, @RequestBody Programmer programmer) {
        return programmerService.update(id, programmer);
    }

    @GetMapping("/{id}")
    public Programmer findById(@PathVariable Long id) {
        return programmerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        programmerService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Programmer> search(@RequestParam String keyword) {
        return programmerService.search(keyword);
    }

    @GetMapping("/sort")
    public List<Programmer> sort(@RequestParam String order) {
        return programmerService.sortBySalary(order);
    }
}
