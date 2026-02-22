package com.alamara.ecommerce.product;

import com.alamara.ecommerce.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public @Nullable Integer createProduct(@Valid ProductRequest request) {
        var product= mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public @Nullable List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds=request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProduct=repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProduct.size()){
            throw new ProductPurchaseException("One or more products does not exists");
        }
        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0;i<storedProduct.size();i++){
        var product=storedProduct.get(i);
        var productRequest=storedRequest.get(i);
        if( product.getAvailableQuantity() < productRequest.quantity()){
            throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: "+productRequest.productId());
        }
        var newAvailableQuantity= product.getAvailableQuantity()-productRequest.quantity();
        product.setAvailableQuantity(newAvailableQuantity);
        repository.save(product);
        purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public @Nullable ProductResponse findById(Integer productId) {
        return repository.findById(productId).map(mapper::toProductResponse).
                orElseThrow(()-> new EntityNotFoundException("Product not found with the ID:: "+productId));
    }

    public @Nullable List<ProductResponse> findAll() {
        return repository.findAll().stream().map(mapper::toProductResponse).collect(Collectors.toList());
    }
}
