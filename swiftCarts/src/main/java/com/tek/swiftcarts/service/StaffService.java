package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.StaffDto;

import java.util.List;

public interface StaffService {

    List<StaffDto> fetchAllStaffMembers();
}
