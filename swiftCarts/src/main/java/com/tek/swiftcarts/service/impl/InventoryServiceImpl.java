package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.InventoryDTO;
import com.tek.swiftcarts.dto.InventoryMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.service.InventoryService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final ProductCategoriesRepository categoriesRepository;
    private final StatusRepository statusRepository;
    private final InventoryMovementRepository inventoryMovementsRepository;
    private final InventoryRepository inventoryRepository;


    public SystemResponse createInventory(InventoryDTO dto){

        Inventory inventory=new Inventory();

        inventory.setId(dto.getId());
        inventory.setName(dto.getName());
        inventory.setDescription(dto.getDescription());
        inventory.setUnitPrice(dto.getUnitPrice());

        Optional<Users>users=usersRepository.findById(dto.getCreatedBy().getId());

        inventory.setCreatedBy(users.get());
        inventory.setUpdatedBy(users.get());
        inventory.setCreatedAt(LocalDateTime.now());

        Optional<Products>products=productRepository.findById(dto.getProduct().getId());
        inventory.setProduct(products.get());

        Optional<ProductCategories>categories=categoriesRepository.findById(dto.getProductCategories().getId());

        inventory.setProductCategories(categories.get());



        Optional<Status>status=statusRepository.findById(dto.getStatus().getId());

        Optional<Status> lowStock=statusRepository.findByName("LOW STOCK");

        Optional<Status> inStock=statusRepository.findByName("IN STOCK");


        if (dto.getQuantityInStock() < 20 ){

            inventory.setStatus(lowStock.get());

        }else
            inventory.setStatus(inStock.get());

        inventory.setStatus(status.get());

        Optional<InventoryMovements>inventoryMovements=inventoryMovementsRepository.findById(dto.getInventoryMovements().getId());
        inventory.setInventoryMovement(inventoryMovements.get());

        inventoryRepository.save(inventory);

        return new SystemResponse("InventoryCreated Successfully",inventory.getId(),"Inventory Added", ResponseState.SUCCESS);
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
