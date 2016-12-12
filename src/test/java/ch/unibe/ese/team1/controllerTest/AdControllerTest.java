package ch.unibe.ese.team1.controllerTest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdControllerTest {

    private MockMvc mockMvc;
   // private Principal principal;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void adNonExisting() throws Exception{
        this.mockMvc.perform(get("/ad").param("id","97699")).andExpect(view().name("pageNotFound"));
    }

    @Test
    public void messageSent() throws Exception{
        this.mockMvc.perform(get("/ad").principal(getPrincipal("jane@doe.ch")).param("id","1")
                .param("msgSubject","test").param("msgTextarea","jojo")).andExpect(view().name("adDescription"))
                .andExpect(status().isOk());
    }

    @Test
    public void inquirySent() throws Exception{
        this.mockMvc.perform(get("/ad").principal(getPrincipal("jane@doe.ch")).param("id","1")
                .param("confirmationDialog","")).andExpect(status().isOk())
                .andExpect(view().name("adDescription"));
    }

    @Test
    public void watchProfile() throws Exception{
        this.mockMvc.perform(get("/ad").principal(getPrincipal("jane@doe.ch")).param("id","1"));
    }

    @Test
    public void isBookmarked() throws Exception{
         MvcResult result = this.mockMvc.perform(post("/bookmark").principal(getPrincipal("jane@doe.com"))
                    .param("id","1").param("screening","true").param("bookmarked","true"))
                    .andExpect(status().isOk())
                    .andReturn();

        assertEquals("2",result.getResponse().getContentAsString());
    }

    @Test
    public void unbookmarkAd() throws Exception{
        MvcResult result  = this.mockMvc.perform(post("/bookmark").principal(getPrincipal("ese@unibe.ch"))
                .param("id","1").param("screening","true").param("bookmarked","true"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("3",result.getResponse().getContentAsString());
    }

    @Test
    public void openMyRoomsNotLoggedIn() throws Exception{
        this.mockMvc.perform(get("/profile/myRooms")).andExpect(view().name("home"));
    }

    @Test
    public void openMyRoomsLoggedIn() throws Exception{
        this.mockMvc.perform(get("/profile/myRooms").principal(getPrincipal("jane@doe.com")))
                .andExpect(view().name("myRooms")).andExpect(model().attributeExists("bookmarkedAdvertisements","ownAdvertisements"))
                .andExpect(status().isOk());
    }

    @Test
    public void bookmarkOwnAdandFail() throws Exception{
        MvcResult result  = this.mockMvc.perform(post("/bookmark").principal(getPrincipal("user@bern.com"))
                .param("id","1").param("screening","true").param("bookmarked","true"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("4",result.getResponse().getContentAsString());
    }

    @Test
    public void bookmarkNotLoggedIn() throws Exception{
        MvcResult result  = this.mockMvc.perform(post("/bookmark")
                .param("id","1").param("screening","true").param("bookmarked","true"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("0",result.getResponse().getContentAsString());
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




}
