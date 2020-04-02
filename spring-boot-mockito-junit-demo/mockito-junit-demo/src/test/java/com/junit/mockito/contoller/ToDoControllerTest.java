package com.junit.mockito.contoller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.junit.mockito.MockitoJunitDemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockitoJunitDemoApplication.class) // ApplicationContext will be loaded from
																	// MockitoJunitDemoApplication class
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoControllerTest {

	/*
	 * WebApplicationContext (wac) provides the web application configuration. It
	 * loads all the application beans and controllers into the context.
	 */

	@Autowired
	private WebApplicationContext wac;

	/*
	 * MockMvc provides support for Spring MVC testing. It encapsulates all web
	 * application beans and make them available for testing.
	 * 
	 * We need to initialize the mockMvc object in the @Before annotated method, so
	 * that we don't need to initialize it inside every test.
	 */

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/*
	 * For our tutorial here, let's actually verify that we're loading the
	 * WebApplicationContext object (wac) properly. We'll also verify that the right
	 * servletContext is being attached:
	 */
	@Test
	public void givenWac_whenServletContext_thenItProvidesGreetController() {
		ServletContext servletContext = wac.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("toDoController"));
	}

	@Test
	public void verifyAllToDoList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/todo").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(4))).andDo(print());
	}

	@Test
	public void verifyToDoById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/todo/3").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.text").exists())
				.andExpect(jsonPath("$.completed").exists()).andExpect(jsonPath("$.id").value(3))
				.andExpect(jsonPath("$.text").value("Build the artifacts"))
				.andExpect(jsonPath("$.completed").value(false)).andDo(print());
	}

	@Test
	public void verifyInvalidToDoArgument() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/todo/f").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value("400"))
				.andExpect(jsonPath("$.message")
						.value("The request could not be understood by the server due to malformed syntax."))
				.andDo(print());
	}

	@Test
	public void verifyInvalidToDoId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/todo/6").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value("404"))
				.andExpect(jsonPath("$.message").value("ToDo doesn´t exist")).andDo(print());
	}

	@Test
	public void verifyDeleteToDoById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/todo/4").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("200"))
				.andExpect(jsonPath("$.message").value("ToDo has been deleted")).andDo(print());
	}

	@Test
	public void verifyInvalidDeleteToDoById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/todo/7").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value("404"))
				.andExpect(jsonPath("$.message").value("ToDo to delete doesn´t exist")).andDo(print());
	}

	@Test
	public void saveToDo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").contentType(MediaType.APPLICATION_JSON)
				.content("{\"text\": \"New ToDO\",\"completed\": \"false\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.text").value("New ToDo Sample")).andExpect(jsonPath("$.completed").value(false))
				.andDo(print());
	}

	@Test
	public void verifyMalformedSaveToDo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo/").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"id\": \"8\", \"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.errorCode").value(404))
				.andExpect(jsonPath("$.message").value("Payload malformed, id must not be defined")).andDo(print());
	}

	@Test
	public void verifyUpdateToDo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/todo/").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"id\": \"1\", \"text\" : \"New ToDo Text\", \"completed\" : \"false\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.text").exists()).andExpect(jsonPath("$.completed").exists())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.text").value("New ToDo Text"))
				.andExpect(jsonPath("$.completed").value(false)).andDo(print());
	}

	@Test
	public void verifyInvalidToDoUpdate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/todo/").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"idd\": \"8\", \"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.errorCode").value(404))
				.andExpect(jsonPath("$.message").value("ToDo to update doesn´t exist")).andDo(print());
	}

}
