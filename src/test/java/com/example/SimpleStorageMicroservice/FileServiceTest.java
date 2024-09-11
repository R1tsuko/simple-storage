package com.example.SimpleStorageMicroservice;

import com.example.SimpleStorageMicroservice.domain.File;
import com.example.SimpleStorageMicroservice.repository.FileRepository;
import com.example.SimpleStorageMicroservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    private File file;

    @BeforeEach
    public void addTestDataToRepository() {
        MockitoAnnotations.openMocks(this);
        file = new File();
        file.setFileData("asd");
        file.setCreationDate(Instant.now());
        file.setFileName("text.txt");
        file.setFileDescription("description_example");
        file.setId(1L);
    }

    @Test
    public void testGetAllFiles() {
        Page<File> pageImp = new PageImpl<>(Arrays.asList(file));
        when(fileRepository.findAll(isA(Pageable.class))).thenReturn(pageImp);

        List<File> files = fileService.getAllFiles(0, 10, "asc").getContent();

        verify(fileRepository, times(1)).findAll(isA(Pageable.class));
        assertEquals(files.get(0), file);
    }

    @Test
    public void testGetFileById() {
        long fileId = file.getId();
        when(fileRepository.findById(fileId)).thenReturn(java.util.Optional.of(file));

        File returnedFile = fileService.getFileById(fileId);

        verify(fileRepository, times(1)).findById(fileId);
        assertEquals(file, returnedFile);
    }

    @Test
    public void testSaveFile() {
        when(fileRepository.save(file)).thenReturn(file);

        File savedFile = fileService.saveFile(file);

        verify(fileRepository, times(1)).save(file);
        assertEquals(file, savedFile);
    }
}
