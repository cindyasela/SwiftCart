package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.ShipmentsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.service.ShipmentsService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShipmentsServiceImpl implements ShipmentsService {

    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;
    private final ShipmentsRepository shipmentsRepository;
    private final DeliveryTypeRepository typeRepository;
    private final StatusRepository statusRepository;


    public SystemResponse createNewDelivery(ShipmentsDTO dto) {

        Shipments shipments = new Shipments();

        shipments.setId(dto.getId());
        shipments.setNotes(dto.getNotes());
        shipments.setDispatchedAt(LocalDateTime.now());
        shipments.setShipmentStatus(ShipmentStatus.PENDING);
        shipments.setUpdatedAt(LocalDateTime.now());
        shipments.setReason(dto.getNotes());

        Optional<Users> users = usersRepository.findById(dto.getEntryBy().getId());

        if (users.isEmpty()) {
            throw new AppException(
                    ErrorCodes.RESOURCE_NOT_FOUND,
                    "Users Not Found");
        }

        shipments.setEntryBy(users.get());

        Optional<Orders> orders = orderRepository.findById(dto.getOrders().getId());

        if (orders.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found");
        }

        shipments.setOrders(orders.get());
        shipments.setDispatchedAt(LocalDateTime.now());
        shipments.setReason(dto.getReason());


        shipmentsRepository.save(shipments);



        return new SystemResponse("New Delivery Has Been Added","Delivery Done","Done", ResponseState.SUCCESS);


    }

    public SystemResponse dispatchDelivery(ShipmentsDTO dto) {

        Shipments shipment = shipmentsRepository.findById(dto.getId()).orElseThrow(() -> new AppException(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "Shipment not found"));

        shipment.setDeliveryNo(dto.getDeliveryNo());
        shipment.setShipmentStatus(ShipmentStatus.DISPATCHED);
        shipment.setDispatchedAt(LocalDateTime.now());
        shipment.setReason(dto.getReason());

        Users user = usersRepository.findById(dto.getEntryBy().getId()).orElseThrow(() -> new AppException(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "User not found"));

        shipment.setEntryBy(user);

        DeliveryType deliveryType = typeRepository.findById(dto.getDeliveryType().getId()).orElseThrow(() -> new AppException(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "Delivery type not found"));

        shipment.setDeliveryType(deliveryType);

        Orders order = orderRepository.findById(dto.getOrders().getId()).orElseThrow(() -> new AppException(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "Order not found"));

        shipment.setOrders(order);

        Status inTransitStatus = statusRepository.findByName("In Transit").orElseThrow(() -> new AppException(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "Processed status not found"));

        shipment.setStatus(inTransitStatus);

        shipmentsRepository.save(shipment);

        return new SystemResponse("Done","Shipment id"+shipment.getId(),"Successfully Dispatched",ResponseState.SUCCESS);
    }

    public SystemResponse deliverShipments(ShipmentsDTO dto){

        Shipments shipments=shipmentsRepository.findById(dto.getId()).orElseThrow(() -> new AppException(
              ErrorCodes.RESOURCE_NOT_FOUND,"Shipments Not Found"));


      shipments.setNotes(dto.getNotes());


    Optional<Status>deliveredStatus=statusRepository.findByName("DELIVERED");

    if (deliveredStatus.isEmpty()){throw  new AppException(
            ErrorCodes.RESOURCE_NOT_FOUND,"Delivered Status Not Found");
    }
    shipments.setStatus(deliveredStatus.get());

    shipments.setShipmentStatus(ShipmentStatus.DELIVERED);


    DeliveryType type=typeRepository.findById(dto.getDeliveryType().getId()).orElseThrow(() ->
                    new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Delivery Type Not Found"));


    Users users=usersRepository.findById(dto.getEntryBy().getId()).orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Users Not Found"));

    shipments.setEntryBy(users);

    shipments.setUpdatedAt(LocalDateTime.now());
    shipments.setCreatedAt(LocalDateTime.now());
    shipments.setReason(dto.getReason());

    shipmentsRepository.save(shipments);

    return new SystemResponse("Done","Shipment of Id "+shipments.getId(),"Shipments Delivered SuccessFully ",ResponseState.SUCCESS);


    }


    public List<ShipmentsDTO>findShipmentsByStatus(ShipmentsDTO dto){

      List<Shipments>shipments=shipmentsRepository.findShipmentByStatusId(dto.getStatus().getId());

      return  shipments.stream()
              .map(DTOConverters::convertToDTO)
              .toList();

    }


}
