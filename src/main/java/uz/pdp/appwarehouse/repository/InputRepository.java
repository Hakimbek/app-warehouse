package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Employee;
import uz.pdp.appwarehouse.entity.Input;

import java.util.Date;

public interface InputRepository extends JpaRepository<Input, Integer> {
    boolean existsByFactureNumberAndDate(String factureNumber, Date date);
}
