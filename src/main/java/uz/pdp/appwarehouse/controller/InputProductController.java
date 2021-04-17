package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    // ADD
    @PostMapping
    public Result add(@RequestBody InputProductDto inputProductDto) {
        return inputProductService.add(inputProductDto);
    }

    // GET
    @GetMapping
    public List<InputProduct> get() {
        return inputProductService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return inputProductService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return inputProductService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto) {
        return inputProductService.edit(id, inputProductDto);
    }
}
