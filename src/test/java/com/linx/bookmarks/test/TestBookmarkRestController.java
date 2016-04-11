package com.linx.bookmarks.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.linx.bookmarks.Application;
import com.linx.bookmarks.domain.Account;
import com.linx.bookmarks.domain.Bookmark;
import com.linx.bookmarks.repository.AccountRepository;
import com.linx.bookmarks.repository.BookmarkRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestBookmarkRestController {

	private final static Logger log = LoggerFactory
			.getLogger(TestBookmarkRestController.class);

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private String userName = "bdussault";

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private Account account;

	private List<Bookmark> bookmarkList = new ArrayList<>();

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;	

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays
				.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() {
		Assert.assertNotNull(webApplicationContext);
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();

		this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

		this.account = accountRepository
				.save(new Account(userName, "password"));
		this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account,
				"http://bookmark.com/1/" + userName, "A description")));
		this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account,
				"http://bookmark.com/2/" + userName, "A description")));
		log.info(bookmarkList.toString());
	}

	@Test
	public void userNotFound() throws Exception {
		mockMvc.perform(
				post("/george/bookmarks/").content(
						this.json(new Bookmark(
								new Account(userName, "password"),
								"http://bookmark.com/1/" + userName,
								"A description"))).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void readSingleBookmark() throws Exception {
		log.info("URL : " + "/" + userName + "/bookmarks/"
				+ this.bookmarkList.get(0).getId());
		mockMvc.perform(get("/" + userName + "/bookmarks/"
                + this.bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.bookmark.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$.bookmark.description", is("A description")))
                .andExpect(jsonPath("$._links.self.href", containsString("/" + userName + "/bookmarks/"
                        + this.bookmarkList.get(0).getId())));		
	}

	@Test
	public void readBookmarks() throws Exception {
		mockMvc.perform(get("/" + userName + "/bookmarks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList", hasSize(2)))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.uri", is("http://bookmark.com/1/" + userName)))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.description", is("A description")))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.id", is(this.bookmarkList.get(1).getId().intValue())))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.uri", is("http://bookmark.com/2/" + userName)))
        .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.description", is("A description")));
	}

	@Test
	public void createBookmark() throws Exception {
		String bookmarkJson = json(new Bookmark(this.account,
				"http://spring.io",
				"a bookmark to the best resource for Spring news and information"));
		this.mockMvc.perform(
				post("/" + userName + "/bookmarks").contentType(contentType)
						.content(bookmarkJson)).andExpect(status().isCreated());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o,
				MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		log.info(mockHttpOutputMessage.getBodyAsString());
		return mockHttpOutputMessage.getBodyAsString();
	}

}
