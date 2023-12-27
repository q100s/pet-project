package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;
    @GetMapping("/{id}")
    public Image getById(@PathVariable("id") Integer id) {
        return imageService.getById(id);
    }
}