package dev.honokai.data_integrator_backend.application.controllers;

import dev.honokai.data_integrator_backend.domain.enums.FileFilterType;
import dev.honokai.data_integrator_backend.domain.enums.SourceType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/enums")
public class EnumController {
    @GetMapping("/file-filter-types")
    public ResponseEntity<List<String>> fileFilterTypes() {
        List<String> availableValues = Stream.of(FileFilterType.values()).map(Enum::name).toList();

        return ResponseEntity.ok(availableValues);
    }

    @GetMapping("/file-source-types")
    public ResponseEntity<List<String>> fileSourceTypes() {
        List<String> availableValues = Stream.of(SourceType.values()).map(Enum::name).toList();

        return ResponseEntity.ok(availableValues);
    }
}
