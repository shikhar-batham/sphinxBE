package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alumni")
public class AlumniController {

    @Autowired
    private AlumniService alumniService;
}
