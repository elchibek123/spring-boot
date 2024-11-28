package java15.springboot.repository;

import jakarta.transaction.Transactional;
import java15.springboot.entities.Programmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProgrammerRepository extends JpaRepository<Programmer, Long> {
    @Modifying
    @Query("delete from Programmer p where p.id = :id")
    void deleteProgrammerById(Long id);

    @Query("select p from Programmer p where p.name ilike %:keyword% or p.lastName ilike %:keyword%")
    List<Programmer> search(String keyword);
}
