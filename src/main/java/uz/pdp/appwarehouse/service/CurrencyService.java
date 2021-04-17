package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    // ADD
    public Result add(Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            return new Result("Currency already exist", false);
        }

        Currency addCurrency = new Currency();
        addCurrency.setName(currency.getName());
        currencyRepository.save(addCurrency);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Currency> get() {
        return currencyRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent()) {
            return new Result("Currency not found", false);
        }

        return new Result("Success", true, optionalCurrency.get());
    }

    // DELETE
    public  Result delete(Integer id) {
        try {
            currencyRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            return new Result("Currency already exist", false);
        }

        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent()) {
            return new Result("Currency not found", false);
        }

        Currency editedCurrency = optionalCurrency.get();
        editedCurrency.setName(currency.getName());
        editedCurrency.setActive(currency.isActive());
        currencyRepository.save(currency);
        return new Result("Successfully edited", true);
    }
}
