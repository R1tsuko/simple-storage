package com.example.SimpleStorageMicroservice.controller;

import com.example.SimpleStorageMicroservice.domain.File;
import com.example.SimpleStorageMicroservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileService fileService;

    @GetMapping("/file")
    public ResponseEntity<List<File>> getAllFiles(
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name="creationDateSort", defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok().body(fileService.getAllFiles(page, pageSize, sortDirection).getContent());
    }

    @PostMapping("/file")
    public ResponseEntity<File> saveFile(@RequestBody File file) {
        return ResponseEntity.ok().body(fileService.saveFile(file));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id) {
        return ResponseEntity.ok().body(fileService.getFileById(id));
    }
}
