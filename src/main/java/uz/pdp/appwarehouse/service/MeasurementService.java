package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    // ADD
    public Result add(Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) {
            return new Result("Measurement already exist", false);
        }

        Measurement addMeasurement = new Measurement();
        addMeasurement.setName(measurement.getName());
        measurementRepository.save(addMeasurement);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Measurement> get() {
        return measurementRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent()) {
            return new Result("Measurement not found", false);
        }

        return new Result("Success", true, optionalMeasurement.get());
    }

    // DELETE
    public  Result delete(Integer id) {
        try {
            measurementRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) {
            return new Result("Measurement already exist", false);
        }

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent()) {
            return new Result("Measurement not found", false);
        }

        Measurement editedMeasurement = optionalMeasurement.get();
        editedMeasurement.setName(measurement.getName());
        editedMeasurement.setActive(measurement.isActive());
        measurementRepository.save(editedMeasurement);
        return new Result("Successfully edited", true);
    }
}
