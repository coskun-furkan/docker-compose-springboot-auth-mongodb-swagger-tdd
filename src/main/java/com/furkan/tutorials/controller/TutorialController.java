package com.furkan.tutorials.controller;

import com.furkan.tutorials.dto.ApiResponse;
import com.furkan.tutorials.model.Tutorial;
import com.furkan.tutorials.service.TutorialService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutorials")
@SecurityRequirement(name = "bearerAuth")
public class TutorialController {

    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tutorial>>> getAll() {
        List<Tutorial> tutorials = tutorialService.getAll();
        return ResponseEntity.ok(ApiResponse.success("Tüm veriler başarıyla getirildi", tutorials));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getById(@PathVariable String id) {
        Optional<Tutorial> tutorial = tutorialService.getById(id);
        return tutorial.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tutorial>> create(@RequestBody Tutorial tutorial) {
        Tutorial saved = tutorialService.create(tutorial);
        return ResponseEntity.ok(ApiResponse.success("Tutorial başarıyla oluşturuldu", saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Tutorial>> update(@PathVariable String id, @RequestBody Tutorial tutorial) {
        Tutorial updated = tutorialService.update(id, tutorial);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Tutorial başarıyla güncellendi", updated));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.error("Güncellenecek tutorial bulunamadı"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        tutorialService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Tutorial başarıyla silindi", null));
    }

    @GetMapping("/published")
    public ResponseEntity<ApiResponse<List<Tutorial>>> getByPublished() {
        List<Tutorial> list = tutorialService.getByPublished();

        if (list.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("Henüz yayınlanmış tutorial yok", list));
        }

        return ResponseEntity.ok(ApiResponse.success("Yayınlanmış tutorial'lar başarıyla getirildi", list));
    }



}
