package com.projetspringboot.controller.api;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursRestController {

    private final CoursService coursService;

    @Autowired
    public CoursRestController(CoursService coursService) {
        this.coursService = coursService;
    }

    @GetMapping
    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }
}
