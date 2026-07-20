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

        if (inventory.getProduct() != null) {
            dto.setProduct(convertToDTO(inventory.getProduct()));
        }

        if (inventory.getInventoryMovement() != null) {
            dto.setInventoryMovements(convertToDto(inventory.getInventoryMovement()));
        }

        if (inventory.getStatus() != null) {
            dto.setStatus(convertToDTO(inventory.getStatus()));
        }

        return dto;
    }

    public static StatusDTO convertToDTO(Status status) {

       StatusDTO dto=new StatusDTO();

       dto.setId(status.getId());
       dto.setName(status.getName());
       dto.setCreatedAt(status.getCreatedAt());
        if (status.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(status.getCreatedBy()));
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

        if (orders.getCreatedBy() != null) {
            dto.setCreatedBy(convertToDTO(orders.getCreatedBy()));
        }

        if (orders.getPaymentMethod() != null) {
            dto.setPaymentMethod(convertToDTO(orders.getPaymentMethod()));
        }




        return dto;
    }


    public static PaymentMethodsDTO convertToDTO(PaymentMethods paymentMethods) {

        PaymentMethodsDTO dto = new PaymentMethodsDTO();

        dto.setId(paymentMethods.getId());

        dto.setName(paymentMethods.getName());

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

        return dto;
    }
}
