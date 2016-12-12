package ch.unibe.ese.team1.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
@Transactional
public class AlertControllerTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void createAlertAndShowAlertView() throws Exception {
        this.mockMvc.perform(post("/profile/alerts").principal(getPrincipal("ese@unibe.ch"))
                .param("house", "true")
                .param("city", "3000 - Bern")
                .param("radius", "5")
                .param("price", "500"))
                .andExpect(status().isOk())
                .andExpect(view().name("alerts"))
                .andExpect(model().attributeExists("user", "alertForm", "alerts", "searchForm"))
                .andExpect(model().attributeHasNoErrors("alertForm"));
    }

    @Test (expected = NestedServletException.class)
    public void createUnfinishedFakeAlertAndShowAlertViewFails() throws Exception {
        this.mockMvc.perform(post("/profile/alerts").principal(getPrincipal("ese@unibe.ch")));
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
