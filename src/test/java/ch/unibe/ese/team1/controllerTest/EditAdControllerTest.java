package ch.unibe.ese.team1.controllerTest;

import ch.unibe.ese.team1.model.Property;
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
import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })

@Transactional
public class EditAdControllerTest {

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
    public void editAdPageTest() throws Exception {
        this.mockMvc.perform(get("/profile/editAd")
                .param("id", "4")
                .principal(getPrincipal("ese@unibe.ch")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ad", "visits","placeAdForm"));
    }

    @Test
    public void userEditsOtherUsersAdTest() throws Exception {
        this.mockMvc.perform(get("/profile/editAd")
                .param("id", "5")
                .principal(getPrincipal("ese@unibe.ch")))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    public void editAdWithValidParametersTest() throws Exception {
        this.mockMvc.perform(post("/profile/editAd")
                .param("adId", "4")
                .param("price","500")
                .param("title", "super amazing house")
                .param("street", "HouseStrasse 42")
                .param("city", "3000 - Bern")
                .param("moveInDate", "20-06-2017")
                .param("price", "100")
                .param("roomDescription", "description")
                .param("property", Property.HOUSE.toString())
                .param("squareFootage", "100")
                .param("numberRooms", "3")
                .param("instantBuyPrice","1000")
                .principal(getPrincipal("ese@unibe.ch")))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeExists("confirmationMessage"));
    }


    @Test
    public void editAdWithInvalidParametersTest() throws Exception {
        this.mockMvc.perform(post("/profile/editAd")
                .param("adId", "4")
                .param("price","abc")
                .principal(getPrincipal("ese@unibe.ch")))
                .andExpect(model().attributeHasFieldErrors("placeAdForm", "price"));
    }


    @Test
    public void deletePictureFromAdTest() throws Exception {
        this.mockMvc.perform(post("/profile/editAd/deletePictureFromAd")
                .principal(getPrincipal("ese@unibe.ch"))
                .param("adId", "4").param("pictureId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVisitFromAdTest() throws Exception {
        this.mockMvc.perform(post("/profile/editAd/deleteVisistFromAd")
                .principal(getPrincipal("ese@unibe.ch"))
                .param("adId", "4").param("pictureId", "1"))
                .andExpect(status().isOk());
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