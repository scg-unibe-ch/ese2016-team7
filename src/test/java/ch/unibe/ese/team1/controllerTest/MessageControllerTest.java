package ch.unibe.ese.team1.controllerTest;


import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
@Transactional
public class MessageControllerTest {

    private MockMvc mockMvc;
    // private Principal principal;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }



    private Principal getPrincipal(String name){
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return name;
            }
        };
        return principal;
    }

    @Test
    public void messagesTest() throws Exception {

        mockMvc.perform(get("/profile/messages").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getInboxTest() throws  Exception {

        mockMvc.perform(post("/profile/message/inbox").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getSentTest() throws  Exception {

        mockMvc.perform(post("/profile/message/sent").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getMessageTest() throws Exception {

        mockMvc.perform(get("/profile/messages/getMessage").principal(getPrincipal("jane@doe.com")).param("id", "1"))
                .andExpect(status().isOk());
    }








}
