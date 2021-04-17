package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(Warehouse warehouse) {
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName) {
            return new Result("Warehouse already exist", false);
        }

        Warehouse addWarehouse = new Warehouse();
        addWarehouse.setName(warehouse.getName());
        warehouseRepository.save(addWarehouse);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Warehouse> get() {
        return warehouseRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }

        return new Result("Success", true, optionalWarehouse.get());
    }

    // DELETE
    public  Result delete(Integer id) {
        try {
            warehouseRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Warehouse warehouse) {
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName) {
            return new Result("Warehouse already exist", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }

        Warehouse editedWarehouse = optionalWarehouse.get();
        editedWarehouse.setName(warehouse.getName());
        editedWarehouse.setActive(warehouse.isActive());
        warehouseRepository.save(editedWarehouse);
        return new Result("Successfully edited", true);
    }
}
