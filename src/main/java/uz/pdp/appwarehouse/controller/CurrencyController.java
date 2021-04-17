package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    // ADD
    @PostMapping
    public Result add(@RequestBody Currency currency) {
        return currencyService.add(currency);
    }

    // GET
    @GetMapping
    public List<Currency> get() {
        return currencyService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return currencyService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return currencyService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Currency currency) {
        return currencyService.edit(id, currency);
    }
}
