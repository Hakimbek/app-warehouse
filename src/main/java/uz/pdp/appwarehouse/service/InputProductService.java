package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {
    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InputRepository inputRepository;

    // ADD
    public Result add(InputProductDto inputProductDto) {
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }
        Product product = optionalProduct.get();

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent()) {
            return new Result("Input not found", false);
        }
        Input input = optionalInput.get();

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(product);
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProduct.setInput(input);
        inputProductRepository.save(inputProduct);
        return new Result("Successfully added", true);
    }

    // GET
    public List<InputProduct> get() {
        return inputProductRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent()) {
            return new Result("InputProduct not found", false);
        }

        return new Result("Success", true, optionalInputProduct.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            inputProductRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, InputProductDto inputProductDto) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent()) {
            return new Result("InputProduct not found", false);
        }

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }
        Product product = optionalProduct.get();

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent()) {
            return new Result("Input not found", false);
        }
        Input input = optionalInput.get();


        InputProduct inputProduct = optionalInputProduct.get();
        inputProduct.setProduct(product);
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProduct.setInput(input);
        inputProductRepository.save(inputProduct);
        return new Result("Successfully edited", true);
    }
}
