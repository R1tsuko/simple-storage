package com.example.SimpleStorageMicroservice.repository;

import com.example.SimpleStorageMicroservice.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
