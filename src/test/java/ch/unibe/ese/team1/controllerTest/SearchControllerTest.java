package ch.unibe.ese.team1.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })

@Transactional
public class SearchControllerTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpServletRequest request;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void searchAdTest() throws Exception{
        mockMvc.perform(get("/searchAd"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchAd"));
    }


    @Test
    public void resultsSuccessTest() throws Exception{
        mockMvc.perform(post("/results")
                .param("city", "3000 - Bern")
                .param("radius", "50")
                .param("price", "50000"))
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andExpect(model().attributeExists("results", "results", "premium"));
    }

    @Test
    public void resultsFailedTest() throws Exception{
        mockMvc.perform(post("/results")
                .param("city", "300 - Bern")
                .param("radius", "50")
                .param("price", "sdafdsjk"))
                .andExpect(view().name("searchAd"))
                .andExpect(model().attributeHasFieldErrors("searchForm", "price"))
                .andExpect(status().isOk());
    }



    @Test
    public void resultsQuicksearchSuccessTest() throws Exception{
        mockMvc.perform(post("/quicksearch")
                .param("city", "3000 - Bern"))
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andExpect(model().attributeExists("results", "results", "premium"));

    }



    /*
    @Test
    public void resultsQuicksearchFailed() throws Exception{
        mockMvc.perform(post("/quicksearch")
                .param("city", null))
                //.andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("searchForm","city"))
                .andExpect(status().isOk());
    }
    */


}