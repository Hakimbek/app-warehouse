package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    // ADD
    public Result add(OutputDto outputDto) {
        boolean existsByFactureNumberAndDate = outputRepository.existsByFactureNumberAndDate(outputDto.getFactureNumber(), outputDto.getDate());
        if (existsByFactureNumberAndDate) {
            return new Result("Output already exist", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()) {
            return new Result("Client not found", false);
        }
        Client client = optionalClient.get();

        Optional<Currency> currencyOptional = currencyRepository.findById(outputDto.getCurrencyId());
        if (!currencyOptional.isPresent()) {
            return new Result("Currency not found", false);
        }
        Currency currency = currencyOptional.get();

        String code = UUID.randomUUID().toString();

        Output output = new Output();
        output.setDate(outputDto.getDate());
        output.setWarehouse(warehouse);
        output.setClient(client);
        output.setCurrency(currency);
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setCode(code);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Output> get() {
        return outputRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()) {
            return new Result("Output not found", false);
        }

        return new Result("Success", true, optionalOutput.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            outputRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, OutputDto outputDto) {
        boolean existsByFactureNumberAndDate = outputRepository.existsByFactureNumberAndDate(outputDto.getFactureNumber(), outputDto.getDate());
        if (existsByFactureNumberAndDate) {
            return new Result("Output already exist", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()) {
            return new Result("Warehouse not found", false);
        }
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()) {
            return new Result("Client not found", false);
        }
        Client client = optionalClient.get();

        Optional<Currency> currencyOptional = currencyRepository.findById(outputDto.getCurrencyId());
        if (!currencyOptional.isPresent()) {
            return new Result("Currency not found", false);
        }
        Currency currency = currencyOptional.get();

        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()) {
            return new Result("Output not found", false);
        }

        Output output = optionalOutput.get();
        output.setDate(outputDto.getDate());
        output.setWarehouse(warehouse);
        output.setCurrency(currency);
        output.setClient(client);
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);
        return new Result("Successfully edited", true);
    }


}
