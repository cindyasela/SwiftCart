package com.tek.swiftcarts.utils;

import com.tek.swiftcarts.dto.*;
import com.tek.swiftcarts.model.*;

public class DTOConverters {

    public static InventoryDTO convertToDTO(Inventory inventory) {

        InventoryDTO dto = new InventoryDTO();

        dto.setId(inventory.getId());

        dto.setName(inventory.getName());

        dto.setDescription(inventory.getDescription());

        dto.setCreatedAt(inventory.getCreatedAt());

        dto.setUpdatedAt(inventory.getUpdatedAt());

        dto.setQuantityInStock(inventory.getQuantityInStock());

        dto.setUnitPrice(inventory.getUnitPrice());

        if (inventory.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(inventory.getCreatedBy()));
        }

        if (inventory.getUpdatedBy() != null) {
            dto.setUpdatedBy(convertToDTO(inventory.getUpdatedBy()));
        }

        if (inventory.getProduct() != null) {
            dto.setProduct(convertToDTO(inventory.getProduct()));
        }

        if (inventory.getProductCategories() != null) {
            dto.setProductCategories(convertToDTO(inventory.getProductCategories()));
        }

        if (inventory.getInventoryMovement() != null) {
            dto.setInventoryMovements(convertToDto(inventory.getInventoryMovement()));
        }

        if (inventory.getStatus() != null) {
            dto.setStatus(convertToDTO(inventory.getStatus()));
        }

        return dto;
    }

    public static ProductCategoriesDTO convertToDTO(ProductCategories category) {

        ProductCategoriesDTO dto = new ProductCategoriesDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                null,
                category.getUpdatedAt(),
                null,
                null
        );

        return dto;
    }

    public static StatusDTO convertToDTO(Status status) {

       StatusDTO dto=new StatusDTO();

       dto.setId(status.getId());
       dto.setName(status.getName());
       dto.setCreatedAt(status.getCreatedAt());
       if (status.getCreatedBy() != null) {
           dto.setCreatedById(status.getCreatedBy().getId());
       }

        return  dto;
    }



    public static UsersDTO convertToDTO(Users users) {

        UsersDTO dto=new UsersDTO();
        dto.setId(users.getId());
        dto.setFirstName(users.getFirstName());
        dto.setLastName(users.getLastName());
        dto.setPhoneNumber(users.getPhoneNumber());

        return dto;
    }

    public static InventoryMovementsDTO convertToDto(InventoryMovements inventoryMovement) {

        InventoryMovementsDTO dto=new InventoryMovementsDTO();

        dto.setId(inventoryMovement.getId());
        dto.setName(inventoryMovement.getName());

        return dto;
    }


    public static ProductDTO convertToDTO(Products product) {

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());

        dto.setName(product.getName());

        dto.setNumberOfProducts(product.getNumberOfProducts());

        dto.setPurchasedOn(product.getPurchasedOn());

        dto.setLastRestockedAt(product.getLastRestockedAt());

        dto.setSkuNumber(product.getSKUNumber());

        dto.setBuyingPrice(product.getBuyingPrice());

        dto.setSellingPrice(product.getSellingPrice());

        dto.setImageBase64(product.getImageBase64());

        if (product.getStatus() != null) {
            dto.setStatus(convertToDTO(product.getStatus()));
        }

        if (product.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(product.getCreatedBy()));
        }

        if (product.getUpdatedBy() != null) {
            dto.setUpdatedBy(convertToDTO(product.getUpdatedBy()));
        }

        if (product.getCategory() != null) {
            ProductCategoriesDTO categoryDTO = new ProductCategoriesDTO(
                    product.getCategory().getId(), product.getCategory().getName(),
                    product.getCategory().getDescription(), product.getCategory().getCreatedAt(),
                    null, product.getCategory().getUpdatedAt(), null, null);
            dto.setCategory(categoryDTO);
        }

        if (product.getBrand() != null) {
            ProductBrandDTO brandDTO = new ProductBrandDTO();
            brandDTO.setId(product.getBrand().getId());
            brandDTO.setName(product.getBrand().getName());
            brandDTO.setDescription(product.getBrand().getDescription());
            dto.setBrand(brandDTO);
        }

        if (product.getSupplier() != null) {
            dto.setSupplier(convertToDTO(product.getSupplier()));
        }

    return dto;

    }

    public static SupplierDTO convertToDTO(Supplier supplier) {

        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getDescription(),
                supplier.getPhoneNumber(),
                supplier.getEmail()
        );
    }

    public static SupplierTransactionDTO convertToDTO(SupplierTransaction transaction) {

        SupplierTransactionDTO dto = new SupplierTransactionDTO();

        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setQuantity(transaction.getQuantity());
        dto.setNotes(transaction.getNotes());
        dto.setCreatedAt(transaction.getCreatedAt());

        if (transaction.getSupplier() != null) {
            dto.setSupplier(convertToDTO(transaction.getSupplier()));
        }

        if (transaction.getProduct() != null) {
            dto.setProduct(convertToDTO(transaction.getProduct()));
        }

        if (transaction.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(transaction.getCreatedBy()));
        }

        return dto;
    }

    public static SupplierRequestDTO convertToDTO(SupplierRequest request) {

        SupplierRequestDTO dto = new SupplierRequestDTO();

        dto.setId(request.getId());
        dto.setQuantity(request.getQuantity());
        dto.setNotes(request.getNotes());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());

        if (request.getSupplier() != null) {
            dto.setSupplier(convertToDTO(request.getSupplier()));
        }

        if (request.getProduct() != null) {
            dto.setProduct(convertToDTO(request.getProduct()));
        }

        if (request.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(request.getCreatedBy()));
        }

        return dto;
    }

    public static OrderHistoryDTO convertToDTO(OrderHistory history) {

        OrderHistoryDTO dto=new OrderHistoryDTO();

        dto.setId(history.getId());
        dto.setComment(history.getComment());
        dto.setCreatedAt(history.getCreatedAt());
        if (history.getEntryBy() != null) {
            dto.setEntryBy(convertToDTO(history.getEntryBy()));
        }

        return  dto;
    }


    public static OrdersDTO convertToDTO(Orders orders) {

        OrdersDTO dto=new OrdersDTO();

        dto.setId(orders.getId());
        dto.setName(orders.getName());
        dto.setComments(orders.getComments());
        dto.setNotes(orders.getNotes());
        dto.setCreatedAt(orders.getCreatedAt());
        dto.setUpdatedAt(orders.getUpdatedAt());
        dto.setClosedAt(orders.getClosedAt());
        dto.setType(orders.getType());
        dto.setTotalAmount(orders.getTotalAmount());
        dto.setSubtotal(orders.getSubtotal());

        if (orders.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(orders.getCreatedBy()));
        }

        if (orders.getStatus() != null) {
            dto.setStatus(convertToDTO(orders.getStatus()));
        }

        if (orders.getPaymentMethod() != null) {
            dto.setPaymentMethod(convertToDTO(orders.getPaymentMethod()));
        }

        if (orders.getPaymentStatus() != null) {
            dto.setPaymentStatus(convertToDTO(orders.getPaymentStatus()));
        }

        if (orders.getOrderItems() != null) {
            dto.setOrderItems(orders.getOrderItems().stream().map(DTOConverters::convertToDTO).toList());
        }

        return dto;
    }


    public static OrderItemsDTO convertToDTO(OrderItems item) {

        OrderItemsDTO dto = new OrderItemsDTO();

        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotal(item.getTotal());
        dto.setNotes(item.getNotes());
        dto.setCreatedAt(item.getCreatedAt());

        if (item.getProduct() != null) {
            dto.setProduct(convertToDTO(item.getProduct()));
        }

        return dto;
    }


    public static PaymentMethodsDTO convertToDTO(PaymentMethods paymentMethods) {

        PaymentMethodsDTO dto = new PaymentMethodsDTO();

        dto.setId(paymentMethods.getId());

        dto.setName(paymentMethods.getName());

        return dto;

    }

    public static PaymentStatusDTO convertToDTO(PaymentStatus paymentStatus) {

        PaymentStatusDTO dto = new PaymentStatusDTO();

        dto.setId(paymentStatus.getId());
        dto.setName(paymentStatus.getName());
        dto.setDescription(paymentStatus.getDescription());

        return dto;
    }

    public static PaymentsDTO convertToDTO(Payments payments) {

        PaymentsDTO dto = new PaymentsDTO();

        dto.setId(payments.getId());

        dto.setNotes(payments.getNotes());

        dto.setAmount(payments.getAmount());

        dto.setPaidAt(payments.getPaidAt());

        if (payments.getStatus() != null) {
            dto.setStatus(convertToDTO(payments.getStatus()));
        }

        dto.setOperatorReceipt(payments.getOperatorReceipt());
        dto.setCreatedAt(payments.getCreatedAt());
        dto.setTransactionId(payments.getTransactionId());
        dto.setCheckoutRequestId(payments.getCheckoutRequestId());

        if (payments.getPaymentMethods() != null) {
            dto.setPaymentMethods(convertToDTO(payments.getPaymentMethods()));
        }

        if (payments.getReceivedBy() != null) {
            dto.setReceivedBy(convertToDTO(payments.getReceivedBy()));
        }

        if (payments.getOrder() != null) {
            dto.setOrder(convertToDTO(payments.getOrder()));
        }

        return dto;

    }

    private static TransactionStatus convertToDTO(TransactionStatus status) {

        return status;
    }

    public static RefundsDTO convertToDTO(Refunds refunds) {

        RefundsDTO dto = new RefundsDTO();

        dto.setId(refunds.getId());

        dto.setNotes(refunds.getNotes());

        dto.setAmount(refunds.getAmount());

        dto.setReason(refunds.getReason());

        dto.setProcessedAt(refunds.getProcessedAt());


        if (refunds.getOrder() != null) {
            dto.setOrder(convertToDTO(refunds.getOrder()));
        }

        if (refunds.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(refunds.getCreatedBy()));
        }


        return dto;
    }

    public static ShipmentsDTO convertToDTO(Shipments shipments) {

        ShipmentsDTO dto = new ShipmentsDTO();

        dto.setId(shipments.getId());
        dto.setNotes(shipments.getNotes());
        dto.setShipmentStatus(shipments.getShipmentStatus());
        dto.setCreatedAt(shipments.getCreatedAt());
        dto.setUpdatedAt(shipments.getUpdatedAt());
        dto.setDispatchedAt(shipments.getDispatchedAt());
        dto.setReason(shipments.getReason());
        dto.setDeliveryNo(shipments.getDeliveryNo());

        if (shipments.getOrders() != null) {
            dto.setOrders(convertToDTO(shipments.getOrders()));
        }

        if (shipments.getEntryBy() != null) {
            dto.setEntryBy(convertToDTO(shipments.getEntryBy()));
        }

        if (shipments.getStatus() != null) {
            dto.setStatus(convertToDTO(shipments.getStatus()));
        }

        if (shipments.getDeliveryType() != null) {
            dto.setDeliveryType(convertToDTO(shipments.getDeliveryType()));
        }

        return dto;
    }

    public static DeliveryTypeDTO convertToDTO(DeliveryType deliveryType) {

        DeliveryTypeDTO dto = new DeliveryTypeDTO();

        dto.setId(deliveryType.getId());
        dto.setName(deliveryType.getName());
        dto.setDescription(deliveryType.getDescription());

        return dto;
    }
}
