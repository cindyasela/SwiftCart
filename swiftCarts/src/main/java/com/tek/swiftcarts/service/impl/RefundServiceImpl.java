package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.RefundsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.OrderRepository;
import com.tek.swiftcarts.repository.RefundRepository;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.RefundService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final RefundRepository refundRepository;

    private Users resolveActor(SwiftCartPrincipal principal) {

        return usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));
    }

    public RefundsDTO registerRefund(RefundsDTO dto, SwiftCartPrincipal principal) {

        Refunds refunds = new Refunds();

        refunds.setAmount(dto.getAmount());
        refunds.setCreatedAt(LocalDateTime.now());
        refunds.setNotes(dto.getNotes());
        refunds.setReason(dto.getReason());

        refunds.setCreatedBy(resolveActor(principal));

        Optional<Orders> orders = orderRepository.findById(dto.getOrder().getId());

        if (orders.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found");
        }

        refunds.setOrder(orders.get());

        refunds.setProcessedAt(dto.getProcessedAt());

        Optional<Status> pendingStatus = statusRepository.findByName("PENDING");

        if (pendingStatus.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Pending status not found");
        }

        refunds.setStatus(pendingStatus.get());

        refunds.setRefundStatus(RefundStatus.PENDING);

        refundRepository.save(refunds);

        return dto;
    }

    public RefundsDTO approveRefunds(RefundsDTO dto, SwiftCartPrincipal principal) {

        Refunds refunds = refundRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new AppException(
                                ErrorCodes.RESOURCE_NOT_FOUND,
                                "Refund Not Found"));

        refunds.setId(dto.getId());
        refunds.setAmount(dto.getAmount());
        refunds.setCreatedAt(LocalDateTime.now());
        refunds.setNotes(dto.getNotes());
        refunds.setReason(dto.getReason());

        refunds.setCreatedBy(resolveActor(principal));

        Optional<Orders> orders = orderRepository.findById(dto.getOrder().getId());

        if (orders.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found");
        }

        refunds.setOrder(orders.get());

        refunds.setProcessedAt(dto.getProcessedAt());

        Optional<Status> approvedStatus = statusRepository.findByName("APPROVED");

        if (approvedStatus.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Pending status not found");
        }

        refunds.setStatus(approvedStatus.get());

        refunds.setRefundStatus(RefundStatus.APPROVED);

        return dto;

    }

    public List<RefundsDTO> fetchAllRefunds() {

        List<Refunds> refunds = refundRepository.findAllByOrderByCreatedAtDesc();

        return refunds.stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }


    public RefundsDTO rejectRefunds(RefundsDTO dto, SwiftCartPrincipal principal) {

        Refunds refunds = refundRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new AppException(
                                ErrorCodes.RESOURCE_NOT_FOUND,
                                "Refund Not Found"));

        refunds.setId(dto.getId());
        refunds.setAmount(dto.getAmount());
        refunds.setCreatedAt(LocalDateTime.now());
        refunds.setNotes(dto.getNotes());
        refunds.setReason(dto.getReason());

        refunds.setCreatedBy(resolveActor(principal));

        Optional<Orders> orders = orderRepository.findById(dto.getOrder().getId());

        if (orders.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found");
        }

        refunds.setOrder(orders.get());

        refunds.setProcessedAt(dto.getProcessedAt());

        Optional<Status> rejectedStatus = statusRepository.findByName("REJECTED");

        if (rejectedStatus.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "REJECTED status not found");
        }

        refunds.setStatus(rejectedStatus.get());

        refunds.setRefundStatus(RefundStatus.REJECTED);

        return dto;

    }


    public RefundsDTO processRefunds(RefundsDTO dto, SwiftCartPrincipal principal) {

        Refunds refunds = refundRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new AppException(
                                ErrorCodes.RESOURCE_NOT_FOUND,
                                "Refund Not Found"));

        refunds.setId(dto.getId());
        refunds.setAmount(dto.getAmount());
        refunds.setCreatedAt(LocalDateTime.now());
        refunds.setNotes(dto.getNotes());
        refunds.setReason(dto.getReason());

        refunds.setCreatedBy(resolveActor(principal));

        Optional<Orders> orders = orderRepository.findById(dto.getOrder().getId());

        if (orders.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found");
        }

        Optional<Status> cancelledStatus = statusRepository.findByName("CANCELLED");

        if (cancelledStatus.isEmpty()) {
            throw new
                    AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "CANCELLED status not found");
        }

        if (!orders.get().getStatus().equals(cancelledStatus.get())) {
            throw new
                    AppException(ErrorCodes.INVALID_REQUEST,
                    "The Orders Must Be Cancelled");
        }

        refunds.setOrder(orders.get());

        refunds.setProcessedAt(dto.getProcessedAt());

        Optional<Status> approvedStatus = statusRepository.findByName("APPROVED");

        if (approvedStatus.isEmpty()) {
            throw new
                    AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "APPROVED status not found");
        }

        if (!refunds.getStatus().equals(approvedStatus.get())) {
            throw new
                    AppException(ErrorCodes.INVALID_REQUEST,
                    "You Cannot Process A Refund that is Not Approved");
        }

        Optional<Status> processedStatus = statusRepository.findByName("PROCESSED");

        if (processedStatus.isEmpty()) {
            throw new
                    AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "PROCESSED status not found");
        }


        refunds.setStatus(processedStatus.get());

        refunds.setRefundStatus(RefundStatus.PROCESSING);

        return dto;

    }


    public List<RefundsDTO> fetchRefundsByStatus(RefundStatus dto) {

        List<Refunds> refunds = refundRepository.findRefundByStatusName(dto);

        return refunds.stream()
                .map(DTOConverters::convertToDTO)
                .toList();


    }

    public List<RefundsDTO> fetchRefundsByOrder(RefundsDTO dto) {

        List<Refunds> refunds = refundRepository.findRefundByOrderId(dto.getOrder().getId());

        return refunds.stream()
                .map(DTOConverters::convertToDTO)
                .toList();


    }


}
