package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    // ADD
    public Result add(ProductDto productDto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId) {
            return new Result("Product already exist", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new Result("Category not found", false);
        }
        Category category = optionalCategory.get();

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()) {
            return new Result("Photo not found", false);
        }
        Attachment photo = optionalAttachment.get();

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) {
            return new Result("Measurement not found", false);
        }
        Measurement measurement = optionalMeasurement.get();

        String code = UUID.randomUUID().toString();

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPhotoId(photo);
        product.setCode(code);
        product.setMeasurement(measurement);
        productRepository.save(product);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Product> get() {
        return productRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }

        return new Result("Success", true, optionalProduct.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, ProductDto productDto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId) {
            return new Result("Product already exist", false);
        }

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return new Result("Product not found", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new Result("Category not found", false);
        }
        Category category = optionalCategory.get();

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()) {
            return new Result("Photo not found", false);
        }
        Attachment photo = optionalAttachment.get();

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) {
            return new Result("Measurement not found", false);
        }
        Measurement measurement = optionalMeasurement.get();

        Product editedProduct = optionalProduct.get();
        editedProduct.setName(productDto.getName());
        editedProduct.setCategory(category);
        editedProduct.setPhotoId(photo);
        editedProduct.setMeasurement(measurement);
        editedProduct.setActive(productDto.isActive());
        productRepository.save(editedProduct);
        return new Result("Successfully edited", true);
    }
}
