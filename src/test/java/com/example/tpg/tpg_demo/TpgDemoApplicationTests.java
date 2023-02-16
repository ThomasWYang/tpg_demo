package com.example.tpg.tpg_demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TpgDemoApplicationTests {

	@Autowired
	private CandidateController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		assertNotNull(controller);
	}

	@Test
	void getAllShouldReturnAllCandidates() throws Exception {
		this.mockMvc.perform(get("/candidates"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.candidates").exists());
	}

	@Test
	void getOneShouldReturnFirstCandidate() throws Exception {
		this.mockMvc.perform(get("/candidates/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("Thomas"))
				.andExpect(jsonPath("$.lname").value("Yang"))
				.andExpect(jsonPath("$.email").value("thomas.y2022@gmail.com"));
	}

	@Test
	void getOneShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/candidates/99"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldDelete() throws Exception {
		this.mockMvc.perform(get("/candidates/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("Thomas"))
				.andExpect(jsonPath("$.lname").value("Yang"))
				.andExpect(jsonPath("$.email").value("thomas.y2022@gmail.com"));
	}

	@Test
	void getAllShouldReturnInitialData() throws Exception {
		this.mockMvc.perform(get("/candidates"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.candidates").exists());
	}

}
