package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.PaymentsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.PaymentsService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final PaymentsStatusRepository paymentsStatusRepository;
    private final StatusRepository statusRepository;

    private Users resolveActor(SwiftCartPrincipal principal) {

        return usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public PaymentsDTO recordPayment(PaymentsDTO dto, SwiftCartPrincipal principal) {

        Payments payment = new Payments();

        payment.setAmount(dto.getAmount());
        payment.setNotes(dto.getNotes());
        payment.setOperatorReceipt(dto.getOperatorReceipt());
        payment.setPaidAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(dto.getStatus() != null ? dto.getStatus() : TransactionStatus.PAID);
        payment.setTransactionId(generateTransactionId());

        PaymentMethods paymentMethods = paymentMethodsRepository.findById(dto.getPaymentMethods().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Payment Method Not Found"));
        payment.setPaymentMethods(paymentMethods);

        Orders order = orderRepository.findById(dto.getOrder().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found"));
        payment.setOrder(order);

        payment.setReceivedBy(resolveActor(principal));

        paymentsRepository.save(payment);

        // recordPayment (hit by the real checkout flow) never touched the
        // order's own status - so a fully paid order stayed "PENDING"
        // forever everywhere else in the app (order lists, the "Completed"
        // count on the overview, filters), even though its payment record
        // clearly showed PAID. Keep the two in sync.
        if (payment.getStatus() == TransactionStatus.PAID) {
            PaymentStatus paidStatus = paymentsStatusRepository.findByName("PAID")
                    .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "PAID payment status not seeded"));
            order.setPaymentStatus(paidStatus);

            Status completedStatus = statusRepository.findByName("Completed")
                    .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "Completed status not seeded"));
            order.setStatus(completedStatus);

            orderRepository.save(order);
        }

        return DTOConverters.convertToDTO(payment);
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }


    public SystemResponse createPayment(PaymentsDTO dto, SwiftCartPrincipal principal) {

        Payments payment = new Payments();

        payment.setAmount(dto.getAmount());
        payment.setNotes(dto.getNotes());
        payment.setPaidAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setTransactionId(generateTransactionId());

        PaymentMethods paymentMethods = paymentMethodsRepository.findById(dto.getPaymentMethods().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Payment Method Not Found"));
        payment.setPaymentMethods(paymentMethods);

        Orders order = orderRepository.findById(dto.getOrder().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found"));
        payment.setOrder(order);

        payment.setReceivedBy(resolveActor(principal));

        payment.setStatus(TransactionStatus.CREATED);

        paymentsRepository.save(payment);

        return new SystemResponse("OK", "Payment Created", "Payment of " + dto.getAmount() + " recorded successfully", ResponseState.SUCCESS);
    }



    @Override
    public List<PaymentsDTO> findAll() {

        return paymentsRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }
}
