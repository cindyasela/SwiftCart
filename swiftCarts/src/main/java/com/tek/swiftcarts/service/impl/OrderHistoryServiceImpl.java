package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.OrderHistoryDTO;
import com.tek.swiftcarts.model.HistoryOperation;
import com.tek.swiftcarts.model.OrderHistory;
import com.tek.swiftcarts.model.Orders;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.OrderHistoryRepository;
import com.tek.swiftcarts.service.OrderHistoryService;
import lombok.AllArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository historyRepository;


    public OrderHistory registerOrderHistory(Orders order, HistoryOperation operation, Users entryBy, String comment, String notes, String description){

        OrderHistory history=new OrderHistory();

        history.setNotes(notes);
        history.setCreatedAt(LocalDateTime.now());
        history.setOrders(order);
        history.setDescription(description);
        history.setComment(comment);
        history.setOperation(operation);
        history.setEntryBy(entryBy);

        history=historyRepository.save(history);

        return history;

    }


}
