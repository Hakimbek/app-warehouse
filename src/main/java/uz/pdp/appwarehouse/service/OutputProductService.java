package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OutputRepository outputRepository;

    // ADD
    public Result add(OutputProductDto outputProductDto) {
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }
        Product product = optionalProduct.get();

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent()) {
            return new Result("Output not found", false);
        }
        Output output = optionalOutput.get();

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setProduct(product);
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(output);
        outputProductRepository.save(outputProduct);
        return new Result("Successfully added", true);
    }

    // GET
    public List<OutputProduct> get() {
        return outputProductRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent()) {
            return new Result("OutputProduct not found", false);
        }

        return new Result("Success", true, optionalOutputProduct.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            outputProductRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, OutputProductDto outputProductDto) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent()) {
            return new Result("OutputProduct not found", false);
        }

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }
        Product product = optionalProduct.get();

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent()) {
            return new Result("Output not found", false);
        }
        Output output = optionalOutput.get();

        OutputProduct outputProduct = optionalOutputProduct.get();
        outputProduct.setProduct(product);
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(output);
        outputProductRepository.save(outputProduct);
        return new Result("Successfully edited", true);
    }
}
