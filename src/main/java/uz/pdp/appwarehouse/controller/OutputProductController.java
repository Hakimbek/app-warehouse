package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;

    // ADD
    @PostMapping
    public Result add(@RequestBody OutputProductDto outputProductDto) {
        return outputProductService.add(outputProductDto);
    }

    // GET
    @GetMapping
    public List<OutputProduct> get() {
        return outputProductService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return outputProductService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return outputProductService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto) {
        return outputProductService.edit(id, outputProductDto);
    }
}
