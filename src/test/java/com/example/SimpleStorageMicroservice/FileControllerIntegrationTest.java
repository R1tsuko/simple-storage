package com.example.SimpleStorageMicroservice;

import com.example.SimpleStorageMicroservice.domain.File;
import com.example.SimpleStorageMicroservice.repository.FileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String FILE_JSON =
			"{\"fileData\":\"dGhpcyBpcyBub3JtYWwgdGV4dAo=\"," +
					"\"fileName\":\"title_example\"," +
					"\"fileDescription\":\"description_example\"}";

	@AfterEach
	void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "file");
	}

	@Test
	public void emptyDBFileSuccessfullyUploaded() throws Exception {
		ResultActions result = mockMvc.perform(post("/api/v1/file")
				.contentType(MediaType.APPLICATION_JSON)
				.content(FILE_JSON));

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.fileName").value("title_example"))
				.andExpect(jsonPath("$.fileData").value("dGhpcyBpcyBub3JtYWwgdGV4dAo="))
				.andExpect(jsonPath("$.fileDescription").value("description_example"));
	}

	@Test
	public void twoFilesTwoFilesGot() throws Exception {
		for(int i = 0; i < 2; ++i) mockMvc.perform(post("/api/v1/file")
						.contentType(MediaType.APPLICATION_JSON)
						.content(FILE_JSON));

		ResultActions result = mockMvc.perform(get("/api/v1/file"));

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void fiveFilesReturnedPageTwoSizeTwo() throws Exception {
		for(int i = 0; i < 5; ++i) {
			mockMvc.perform(post("/api/v1/file")
					.contentType(MediaType.APPLICATION_JSON)
					.content(FILE_JSON));
		}

		ResultActions result = mockMvc.perform(get("/api/v1/file?page=0&pageSize=2"));

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void oneFileGetFileByIdReturnFileWithThisId () throws Exception {
		File file = new File();
		file.setFileData("asd");
		file.setCreationDate(Instant.now());
		file.setFileName("text.txt");
		file.setFileDescription("description_example");
		file = fileRepository.save(file);

		ResultActions result = mockMvc.perform(get("/api/v1/file/{id}", file.getId()));

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(file.getId()));
	}
}
