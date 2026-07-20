package com.tek.swiftcarts.service;

import com.tek.swiftcarts.model.HistoryOperation;
import com.tek.swiftcarts.model.OrderHistory;
import com.tek.swiftcarts.model.Orders;
import com.tek.swiftcarts.model.Users;

public interface OrderHistoryService {

    OrderHistory registerOrderHistory(Orders order, HistoryOperation operation, Users entryBy, String comment, String notes, String description);
}
