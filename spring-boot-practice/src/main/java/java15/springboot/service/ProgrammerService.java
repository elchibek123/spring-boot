package java15.springboot.service;

import java15.springboot.entities.Programmer;

import java.util.List;

public interface ProgrammerService {
    List<Programmer> findAll();

    Programmer save(Programmer programmer);

    Programmer update(Long id, Programmer programmer);

    Programmer findById(Long id);

    void deleteById(Long id);

    List<Programmer> search(String keyword);

    List<Programmer> sortBySalary(String order);
}
