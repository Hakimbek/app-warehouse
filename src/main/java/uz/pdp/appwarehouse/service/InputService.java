package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    // ADD
    public Result add(InputDto inputDto) {
        boolean existsByFactureNumberAndDate = inputRepository.existsByFactureNumberAndDate(inputDto.getFactureNumber(), inputDto.getDate());
        if (existsByFactureNumberAndDate) {
            return new Result("Input already exist", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent()) {
            return new Result("Supplier not found", false);
        }
        Supplier supplier = optionalSupplier.get();

        Optional<Currency> currencyOptional = currencyRepository.findById(inputDto.getCurrencyId());
        if (!currencyOptional.isPresent()) {
            return new Result("Currency not found", false);
        }
        Currency currency = currencyOptional.get();

        String code = UUID.randomUUID().toString();

        Input input = new Input();
        input.setDate(inputDto.getDate());
        input.setWarehouse(warehouse);
        input.setSupplier(supplier);
        input.setCurrency(currency);
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setCode(code);
        inputRepository.save(input);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Input> get() {
        return inputRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent()) {
            return new Result("Input not found", false);
        }

        return new Result("Success", true, optionalInput.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            inputRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, InputDto inputDto) {
        boolean existsByFactureNumberAndDate = inputRepository.existsByFactureNumberAndDate(inputDto.getFactureNumber(), inputDto.getDate());
        if (existsByFactureNumberAndDate) {
            return new Result("Input already exist", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent()) {
            return new Result("Supplier not found", false);
        }
        Supplier supplier = optionalSupplier.get();

        Optional<Currency> currencyOptional = currencyRepository.findById(inputDto.getCurrencyId());
        if (!currencyOptional.isPresent()) {
            return new Result("Currency not found", false);
        }
        Currency currency = currencyOptional.get();

        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent()) {
            return new Result("Input not found", false);
        }

        Input input = optionalInput.get();
        input.setDate(inputDto.getDate());
        input.setWarehouse(warehouse);
        input.setSupplier(supplier);
        input.setCurrency(currency);
        input.setFactureNumber(inputDto.getFactureNumber());
        inputRepository.save(input);
        return new Result("Successfully edited", true);
    }


}
