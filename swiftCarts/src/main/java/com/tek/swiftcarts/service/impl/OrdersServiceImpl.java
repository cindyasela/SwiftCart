package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.OrderProccessingDTO;
import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.service.CartsService;
import com.tek.swiftcarts.service.OrderHistoryService;
import com.tek.swiftcarts.service.OrdersService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class OrdersServiceImpl  implements OrdersService {

    private final StatusRepository statusRepository;
    private final UsersRepository usersRepository;
    private final PaymentsStatusRepository paymentsStatusRepository;
    private  final PaymentMethodsRepository paymentMethodsRepository;
    private final OrderRepository orderRepository;
    private final OrderHistoryService orderHistoryService;
    private final CartItemsRepository itemsRepository;
    private final CartRepository cartRepository;
    private final  OrderItemsRepository orderItemsRepository;

    public SystemResponse createStockOrder(OrdersDTO dto){

        Orders order =new Orders();

        order.setName(dto.getName());
        order.setNotes(dto.getNotes());
        order.setUpdatedAt(LocalDateTime.now());
        order.setId(dto.getId());

        Optional<Status>pending=statusRepository.findByName("PENDING");

        if(pending.isEmpty()){
            throw new AppException(
                    ErrorCodes.RESOURCE_NOT_FOUND,
                    "Pending Status Not Found");
        }
        order.setStatus(pending.get());

        Optional<Users>users=usersRepository.findById(dto.getCreatedBy().getId());

        if (users.isEmpty()){
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "User is Not Available");
        }

        order.setCreatedBy(users.get());

        order.setSupportedBy(users.get());

        Optional<PaymentStatus>paymentStatus=paymentsStatusRepository.findById(dto.getPaymentStatus().getId());

                if (paymentStatus.isEmpty())
                    throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                            "PaymentStatus Not Found");

        order.setPaymentStatus(paymentStatus.get());

        Optional<PaymentMethods>paymentMethods=paymentMethodsRepository.findById(dto.getPaymentMethod().getId());

                if (paymentMethods.isEmpty())
                    throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Payment Method Not Found");

        order.setPaymentMethod(paymentMethods.get());

        orderRepository.save(order);

        orderHistoryService.registerOrderHistory(
                order,
                HistoryOperation.PENDING,
                users.get(),
                dto.getComments(),
                dto.getNotes(),
                dto.getDescription()

        );


        return new SystemResponse("Ok","Orders Placed Successfully","Your Order Has Been Placed Successfully",ResponseState.SUCCESS);

    }


    public SystemResponse createCustomerOrder (OrdersDTO dto){

        Orders order=new Orders();

        order.setName(dto.getName());
        order.setNotes(dto.getNotes());
        order.setUpdatedAt(LocalDateTime.now());
        order.setId(dto.getId());

        Optional<Status>pending=statusRepository.findByName("PENDING");

        if(pending.isEmpty()){
            throw new AppException(
                    ErrorCodes.RESOURCE_NOT_FOUND,
                    "Pending Status Not Found");
        }
        order.setStatus(pending.get());

        Optional<Users>users=usersRepository.findById(dto.getCreatedBy().getId());

        if (users.isEmpty()){
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "User is Not Available");
        }

        order.setCreatedBy(users.get());

        order.setSupportedBy(users.get());

        Optional<PaymentStatus>paymentStatus=paymentsStatusRepository.findById(dto.getPaymentStatus().getId());

        if (paymentStatus.isEmpty())
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "PaymentStatus Not Found");

        order.setPaymentStatus(paymentStatus.get());

        Optional<PaymentMethods>paymentMethods=paymentMethodsRepository.findById(dto.getPaymentMethod().getId());

        if (paymentMethods.isEmpty())
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Payment Method Not Found");

        order.setPaymentMethod(paymentMethods.get());

        orderRepository.save(order);

        orderHistoryService.registerOrderHistory(
                order,
                HistoryOperation.ORDER,
                users.get(),
                dto.getComments(),
                dto.getNotes(),
                dto.getDescription()

        );


        return new SystemResponse("Ok","Orders Placed Successfully","Your Order Has Been Placed Successfully",ResponseState.SUCCESS);

    }

    public OrderProccessingDTO processSale(OrderProccessingDTO dto){

        Orders order=orderRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Order Not Found"));


       Optional<Users> users=usersRepository.findById(dto.getCreatedBy().getId());

       if (!users.get().getId().equals(dto.getProcessedBy().getId())){
           throw new AppException(ErrorCodes.INVALID_REQUEST,
                   "Only the person who created the order can process the sale ");
       }


       dto.setCompletedAt(LocalDateTime.now());

       dto.setCreatedAt(LocalDateTime.now());

     Optional<PaymentStatus>  paymentStatus=paymentsStatusRepository.findById(dto.getPaymentStatus().getId());

     if(paymentStatus.isEmpty()) {
         throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Payment Status Not Found");}

     Optional<PaymentStatus>  paid =paymentsStatusRepository.findByName("PAID");

     if (paid.isEmpty()){
         throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Paid Status Not Found");}

        Status confirmedStatus = statusRepository.findByName("Confirmed")
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Confirmed Status Not Found"));


     if (order.getPaymentStatus().getName().equals("Paid")){
         order.setStatus(confirmedStatus);
     }

     order.setNotes(dto.getComments());


     orderRepository.save(order)
     ;
            OrderHistory history= orderHistoryService.registerOrderHistory(
                order,
                HistoryOperation.PENDING,
                users.get(),
                dto.getComments(),
                dto.getComments(),
                dto.getRemarks()

        );

        OrderProccessingDTO orderDto=new OrderProccessingDTO();

            orderDto.setHistoryDTO(DTOConverters.convertToDTO(history));
            orderDto.setStatus(DTOConverters.convertToDTO(confirmedStatus));
            orderDto.setProcessedBy(DTOConverters.convertToDTO(users.get()));




        return dto;
    }

public List<OrdersDTO>fetchOrdersByPaymentStatus(OrdersDTO dto){

        List <Orders>orders=orderRepository.findOrderByPaymentStatus(dto.getPaymentMethod());

        return orders
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();

}


    public OrderProccessingDTO completeOrder(OrderProccessingDTO dto){

        Orders order=orderRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Order Not Found"));


        Optional<Users> users=usersRepository.findById(dto.getCreatedBy().getId());

        if (!users.get().getId().equals(dto.getProcessedBy().getId())){
            throw new AppException(ErrorCodes.INVALID_REQUEST,
                    "Only the person who created the order can process the sale ");
        }


        dto.setCompletedAt(LocalDateTime.now());

        dto.setCreatedAt(LocalDateTime.now());

        Optional<PaymentStatus> paymentStatus=paymentsStatusRepository.findById(dto.getPaymentStatus().getId());

        if(paymentStatus.isEmpty()) {
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Payment Status Not Found");}

        Optional<PaymentStatus>paid =paymentsStatusRepository.findByName("PAID");

        if (paid.isEmpty()){
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Paid Status Not Found");}

        if (!paymentStatus.equals(paid)){
            throw new AppException(ErrorCodes.INVALID_REQUEST,"Payment Status Must Be Paid");
     }

        Status completeStatus = statusRepository.findByName("Completed")
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Completed Status Not Found"));


        if (order.getPaymentStatus().getName().equals("Paid")){
            order.setStatus(completeStatus);
        }

        order.setNotes(dto.getComments());


        orderRepository.save(order)
        ;
        OrderHistory history= orderHistoryService.registerOrderHistory(
                order,
                HistoryOperation.SALE_COMPLETED,
                users.get(),
                dto.getComments(),
                dto.getComments(),
                dto.getRemarks()

        );

        OrderProccessingDTO orderDto=new OrderProccessingDTO();

        orderDto.setHistoryDTO(DTOConverters.convertToDTO(history));
        orderDto.setStatus(DTOConverters.convertToDTO(completeStatus));
        orderDto.setProcessedBy(DTOConverters.convertToDTO(users.get()));


        return dto;
    }


    public List<OrdersDTO>fetchOrderByStatus(OrdersDTO dto){

        List<Orders>orders=orderRepository.findOrderByStatusName(dto.getStatus().getName());

        return orders.stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }


    public  SystemResponse cartCheckout(String cartId,OrdersDTO dto){

      Carts carts=cartRepository.findById(cartId) .orElseThrow(() ->
              new AppException(
                      ErrorCodes.RESOURCE_NOT_FOUND," Sorry The Cart Not Found")) ;


      Status status= statusRepository.findByName("PENDING").orElseThrow(() ->
              new AppException(
                      ErrorCodes.RESOURCE_NOT_FOUND," Status Not Found")) ;

      Users users= usersRepository.findById(dto.getCreatedBy().getId())
              .orElseThrow(() ->
              new AppException(
                      ErrorCodes.RESOURCE_NOT_FOUND," Sorry The User Not Found")) ;


      if (carts.getCartItems().isEmpty()) {throw
              new AppException(
                      ErrorCodes.INVALID_REQUEST,"You Cannot Checkout An Empty Cart");}




        Orders order=new Orders();

        order.setName(dto.getName());
        order.setNotes(dto.getNotes());
        order.setUpdatedAt(LocalDateTime.now());
        order.setId(dto.getId());

        Optional<Status>pending=statusRepository.findByName("PENDING");

        if(pending.isEmpty()){
            throw new AppException(
                    ErrorCodes.RESOURCE_NOT_FOUND,
                    "Pending Status Not Found");
        }
        order.setStatus(pending.get());



        order.setCreatedBy(users);

        order.setSupportedBy(users);

        Optional<PaymentStatus>paymentStatus=paymentsStatusRepository.findById(dto.getPaymentStatus().getId());

        if (paymentStatus.isEmpty())
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,
                    "PaymentStatus Not Found");

        order.setPaymentStatus(paymentStatus.get());

        Optional<PaymentMethods>paymentMethods=paymentMethodsRepository.findById(dto.getPaymentMethod().getId());

        if (paymentMethods.isEmpty())
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Payment Method Not Found");

        order.setPaymentMethod(paymentMethods.get());


        orderRepository.save(order);

        orderHistoryService.registerOrderHistory(
                order,
                HistoryOperation.ORDER,
                users,
                dto.getComments(),
                dto.getNotes(),
                dto.getDescription()

        );


        List<OrderItems> orderItems =
                carts.getCartItems()
                        .stream()
                        .map(cartItem -> {


                            OrderItems item = new OrderItems();


                            item.setOrder(order);


                            item.setProduct(
                                    cartItem.getProduct()
                            );


                            item.setName(
                                    cartItem.getProduct().getName()
                            );


                            item.setQuantity(
                                    cartItem.getQuantity()
                            );


                            item.setUnitPrice(cartItem.getUnitPrice());


                            item.setTotal(
                                    cartItem.getUnitPrice()
                                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                            );


                            item.setCreatedBy(users);


                            item.setCreatedAt(
                                    LocalDateTime.now()
                            );


                            item.setStatus(
                                    pending.get()
                            );


                            return item;

                        })
                        .toList();



        orderItemsRepository.saveAll(orderItems);


        BigDecimal subtotal = orderItems.stream()
                .map(OrderItems::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        order.setSubtotal(subtotal);

        order.setTotalAmount(subtotal);



        orderRepository.save(order);



        orderHistoryService.registerOrderHistory(
               order,
                HistoryOperation.ORDER,
                users,
                dto.getComments(),
                dto.getNotes(),
                dto.getDescription()
        );



        return new SystemResponse(
                "OK",
                "Order Created",
                "Cart checkout completed successfully",
                ResponseState.SUCCESS
        );



    }




}
