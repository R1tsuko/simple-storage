package com.example.SimpleStorageMicroservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(name = "file_data", nullable = false)
    private String fileData;

    @NotNull
    @Column(name = "title", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name="description", nullable = false)
    private String fileDescription;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof File))
        {
            return false;
        }
        else {
            File file = (File) obj;
            return file.getFileName().equals(this.getFileName())
                    && file.getCreationDate().equals(this.getCreationDate())
                    && file.getFileDescription().equals(this.getFileDescription())
                    && file.getFileData().equals(this.getFileData());
        }
    }
}
