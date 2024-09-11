package com.example.SimpleStorageMicroservice.service;

import com.example.SimpleStorageMicroservice.domain.File;
import com.example.SimpleStorageMicroservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Page<File> getAllFiles(int page, int pageSize, String sortDirection) {

        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                sortDirection.equals("desc") ? Sort.by("creationDate").descending() : Sort.by("creationDate").ascending());
        return fileRepository.findAll(pageable);
    }

    public File saveFile(File file) {
        file.setCreationDate(Instant.now());
        File savedFile = fileRepository.save(file);

        return savedFile;
    }

    public File getFileById(long id) {
        return fileRepository.findById(id).orElse(null);
    }

}
