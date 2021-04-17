package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Output;

import java.util.Date;

public interface OutputRepository extends JpaRepository<Output, Integer> {
    boolean existsByFactureNumberAndDate(String factureNumber, Date date);
}
