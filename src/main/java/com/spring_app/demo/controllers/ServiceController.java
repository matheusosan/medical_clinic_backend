package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(value = "*")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping()
    List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @PostMapping()
    Service createService(@RequestBody ServiceRequestDTO dto) {
        return serviceService.createService(dto);
    }
}
