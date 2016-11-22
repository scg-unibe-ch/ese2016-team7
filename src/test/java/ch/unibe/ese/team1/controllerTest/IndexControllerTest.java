package ch.unibe.ese.team1.controllerTest;

import ch.unibe.ese.team1.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;

import javax.management.remote.JMXPrincipal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
public class IndexControllerTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getIndex() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getAbout() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    public void getDisclaimer() throws Exception {
        this.mockMvc.perform(get("/disclaimer"))
                .andExpect(status().isOk())
                .andExpect(view().name("disclaimer"));
    }

    /**
     * TODO: have to figure out how to pass in a Principal instance
     * (Therefore fails)
     * @throws Exception
     */
    @Test
    public void getProfileBalance() throws Exception {

        User user = new User();
        user.setEmail("ese@unibe.ch");
        JMXPrincipal principal = new JMXPrincipal("ese@unibe.ch");


        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .defaultRequest(get("/profile/balance"))
                .addFilters(springSecurityFilterChain)
                .build();

    }

}