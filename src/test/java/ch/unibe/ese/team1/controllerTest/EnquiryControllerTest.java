package ch.unibe.ese.team1.controllerTest;

import ch.unibe.ese.team1.model.Rating;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;
import ch.unibe.ese.team1.model.dao.RatingDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import ch.unibe.ese.team1.model.dao.VisitEnquiryDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
@Transactional

public class EnquiryControllerTest {

    private MockMvc mockMvc;
    // private Principal principal;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VisitEnquiryDao enquiryDao;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    //NotLoggedIn not of interest since this path isn't accessible.
    @Test
    public void enterEnquiriesLoggedIn() throws Exception{
        this.mockMvc.perform(get("/profile/enquiries").principal(getPrincipal("jane@doe.com"))).andExpect(view().name("enquiries"))
                .andExpect(status().isOk());
    }

    @Test
    public void sendEnquiry() throws  Exception{
        this.mockMvc.perform(get("/profile/enquiries/sendEnquiryForVisit").principal(getPrincipal("jane@doe.com"))
                .param("id","1")).andExpect(status().isOk());
        VisitEnquiry enquiry = enquiryDao.findOne((long) 1);
        assertEquals(enquiry.getState(), VisitEnquiryState.OPEN);
    }

    @Test
    public void acceptEnquiry() throws Exception{
        this.mockMvc.perform(get("/profile/enquiries/acceptEnquiry").principal(getPrincipal("jane@doe.com"))
        .param("id","1")).andExpect(status().isOk());
        VisitEnquiry enquiry = enquiryDao.findOne((long) 1);
        assertEquals(enquiry.getState(), VisitEnquiryState.ACCEPTED);
    }

    @Test
    public void declineEnquiry() throws Exception{
        this.mockMvc.perform(get("/profile/enquiries/declineEnquiry").principal(getPrincipal("jane@doe.com"))
                .param("id","1")).andExpect(status().isOk());
        VisitEnquiry enquiry = enquiryDao.findOne((long) 1);
        assertEquals(enquiry.getState(), VisitEnquiryState.DECLINED);
    }

    @Test
    public void declineAndReopenEnquiry() throws Exception{
        this.mockMvc.perform(get("/profile/enquiries/declineEnquiry").principal(getPrincipal("jane@doe.com"))
                .param("id","1")).andExpect(status().isOk());
        VisitEnquiry enquiry = enquiryDao.findOne((long) 1);
        assertEquals(enquiry.getState(), VisitEnquiryState.DECLINED);
        this.mockMvc.perform(get("/profile/enquiries/reopenEnquiry").principal(getPrincipal("jane@doe.com"))
        .param("id","1")).andExpect(status().isOk());
        assertEquals(enquiry.getState(),VisitEnquiryState.OPEN);
    }

    @Test
    @WithMockUser(username = "jane@doe.com")
    public void rateUser() throws Exception{
        this.mockMvc.perform(get("/profile/rateUser").principal(getPrincipal("jane@doe.com"))
        .param("rate","2").param("stars","3")).andExpect(status().isOk());
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
