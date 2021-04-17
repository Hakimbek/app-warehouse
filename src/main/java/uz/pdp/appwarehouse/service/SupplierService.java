package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    // ADD
    public Result add(Supplier supplier) {
        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Supplier already exist", false);
        }

        Supplier addSupplier = new Supplier();
        addSupplier.setName(supplier.getName());
        addSupplier.setPhoneNumber(supplier.getPhoneNumber());
        supplierRepository.save(addSupplier);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Supplier> get() {
        return supplierRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (!optionalSupplier.isPresent()) {
            return new Result("Supplier not found", false);
        }

        return new Result("Success", true, optionalSupplier.get());
    }

    // DELETE
    public  Result delete(Integer id) {
        try {
            supplierRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Supplier supplier) {
        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Supplier already exist", false);
        }

        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (!optionalSupplier.isPresent()) {
            return new Result("Supplier not found", false);
        }

        Supplier editedSupplier = optionalSupplier.get();
        editedSupplier.setName(supplier.getName());
        editedSupplier.setPhoneNumber(supplier.getPhoneNumber());
        editedSupplier.setActive(supplier.isActive());
        supplierRepository.save(editedSupplier);
        return new Result("Successfully edited", true);
    }
}
