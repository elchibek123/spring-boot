package java15.springboot.service;

import jakarta.transaction.Transactional;
import java15.springboot.entities.Programmer;
import java15.springboot.exceptions.NotFoundException;
import java15.springboot.repository.ProgrammerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgrammerServiceImpl implements ProgrammerService{
    private final ProgrammerRepository programmerRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Programmer> findAll() {
        return programmerRepository.findAll();
    }

    @Override
    public Programmer save(Programmer programmer) {
        return programmerRepository.save(programmer);
    }

    @Override
    @Transactional
    public Programmer update(Long id, Programmer newProgrammer) {
        Programmer programmer = findById(id);
        programmer.setName(newProgrammer.getName());
        programmer.setLastName(newProgrammer.getLastName());
        programmer.setEmail(newProgrammer.getEmail());
        programmer.setSalary(newProgrammer.getSalary());
        programmer.setProgrammingLanguage(newProgrammer.getProgrammingLanguage());
        return programmer;
    }

    @Override
    public Programmer findById(Long id) {
        return programmerRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Programmer with ID: %s not found", id))
        );
    }

    @Override
    public void deleteById(Long id) {
        Programmer programmer = findById(id);
        programmerRepository.deleteProgrammerById(programmer.getId());
    }

    @Override
    public List<Programmer> search(String keyword) {
        return programmerRepository.search(keyword);
    }

    @Override
    public List<Programmer> sortBySalary(String order) {
        return sortBySalaryAscOrDesc(order);
    }

    public RowMapper<Programmer> programmerRowMapper() {
        return (resultSet, rowNum) -> {
            Programmer programmer = new Programmer();
            programmer.setId(resultSet.getLong("id"));
            programmer.setName(resultSet.getString("name"));
            programmer.setSalary(resultSet.getBigDecimal("salary"));
            programmer.setProgrammingLanguage(resultSet.getString("programming_language"));
            programmer.setEmail(resultSet.getString("email"));
            programmer.setLastName(resultSet.getString("last_name"));
            return programmer;
        };
    }

    public List<Programmer> sortBySalaryAscOrDesc(String order) {
        String sql = """
            select id, name, last_name, email, salary, programming_language
            from programmers  order by salary""";
        StringBuilder query = new StringBuilder(sql);
        if (order.equalsIgnoreCase("asc")) query.append(" asc");
        else if (order.equalsIgnoreCase("desc")) query.append(" desc");
        return  jdbcTemplate.query(query.toString(), programmerRowMapper());
    }
}
