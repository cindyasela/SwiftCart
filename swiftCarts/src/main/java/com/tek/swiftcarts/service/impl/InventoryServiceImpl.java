package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.InventoryDTO;
import com.tek.swiftcarts.dto.InventoryMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.InventoryService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private static final DateTimeFormatter NAME_KEY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final ProductCategoriesRepository categoriesRepository;
    private final StatusRepository statusRepository;
    private final InventoryMovementRepository inventoryMovementsRepository;
    private final InventoryRepository inventoryRepository;
    private final SupplierTransactionRepository supplierTransactionRepository;


    public SystemResponse createInventory(InventoryDTO dto, SwiftCartPrincipal principal){

        Inventory inventory=new Inventory();


        String uniqueName = dto.getName() + " - " + LocalDateTime.now().format(NAME_KEY_FORMAT);
        inventory.setName(uniqueName);

        inventory.setDescription(dto.getDescription());
        inventory.setUnitPrice(dto.getUnitPrice());
        inventory.setQuantityInStock(dto.getQuantityInStock());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        inventory.setCreatedBy(user);
        inventory.setUpdatedBy(user);
        inventory.setCreatedAt(LocalDateTime.now());

        if (dto.getProduct() == null || dto.getProduct().getId() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Product Is Required");
        }

        Products products = productRepository.findById(dto.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Product Not Found"));
        inventory.setProduct(products);

        // Inventory.quantityInStock is this entry's own ledger row - it never
        // touched the product's own stock count, so restocking (creating a
        // new entry) never actually raised what the Products screen shows.
        // Keep them in sync: every stock-in entry adds to the product total
        // and stamps when it was last restocked.
        products.setNumberOfProducts(products.getNumberOfProducts() + dto.getQuantityInStock());
        products.setLastRestockedAt(LocalDateTime.now());
        productRepository.save(products);

        // Stock has now actually arrived - record what it cost against the
        // product's supplier automatically, so spend tracking doesn't
        // depend on someone separately logging it on the Suppliers page.
        // NOTE: this is spend already incurred, not a SupplierRequest (an
        // ask sent TO the supplier for more stock, tracked separately).
        if (products.getSupplier() != null) {
            SupplierTransaction transaction = new SupplierTransaction();
            transaction.setSupplier(products.getSupplier());
            transaction.setProduct(products);
            transaction.setAmount(dto.getUnitPrice().multiply(java.math.BigDecimal.valueOf(dto.getQuantityInStock())));
            transaction.setQuantity(dto.getQuantityInStock());
            transaction.setNotes("Restock via Inventory - " + uniqueName);
            transaction.setCreatedBy(user);
            transaction.setCreatedAt(LocalDateTime.now());
            supplierTransactionRepository.save(transaction);
        }

        if (dto.getProductCategories() == null || dto.getProductCategories().getId() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Product Category Is Required");
        }

        ProductCategories categories = categoriesRepository.findById(dto.getProductCategories().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Product Category Not Found"));
        inventory.setProductCategories(categories);



        Status status = products.getNumberOfProducts() < 20
                ? statusRepository.findByName("LOW STOCK").orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "LOW STOCK status not seeded"))
                : statusRepository.findByName("IN STOCK").orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "IN STOCK status not seeded"));
        inventory.setStatus(status);

        InventoryMovements movement = inventoryMovementsRepository.findByName("STOCK_IN")
                .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "STOCK_IN movement not seeded"));
        inventory.setInventoryMovement(movement);

        inventoryRepository.save(inventory);

        return new SystemResponse("InventoryCreated Successfully",inventory.getId(),"Inventory Added", ResponseState.SUCCESS);
    }

    @Override
    public SystemResponse deleteInventory(String id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Inventory Entry Not Found"));

        // Mirror image of createInventory's increment - removing an entry
        // takes its quantity back off the product's total too, so the two
        // stay in sync in both directions.
        if (inventory.getProduct() != null) {
            Products products = inventory.getProduct();
            products.setNumberOfProducts(
                    Math.max(0, products.getNumberOfProducts() - inventory.getQuantityInStock()));
            productRepository.save(products);
        }

        inventoryRepository.deleteById(id);

        return new SystemResponse("Deleted", id, "Inventory entry removed", ResponseState.SUCCESS);
    }

    @Override
    public List<InventoryDTO> fetchAllStock() {

        return inventoryRepository.findAll()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    public List<InventoryDTO>fetchByStatusName(InventoryDTO dto){

     List<Inventory>inventories=inventoryRepository.findByStatusName(dto.getStatus().getName());

     return inventories
             .stream()
             .map(DTOConverters::convertToDTO)
             .toList();

    }

    public InventoryMetricsDTO getInventoryMetrics(){

        long quantityInStock=inventoryRepository.count();

        return new InventoryMetricsDTO(quantityInStock);

    }




}
