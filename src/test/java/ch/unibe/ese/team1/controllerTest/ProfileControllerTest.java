package ch.unibe.ese.team1.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
        "file:src/main/webapp/WEB-INF/config/springData.xml",
        "file:src/main/webapp/WEB-INF/config/springSecurity.xml" })

@Transactional
public class ProfileControllerTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpServletRequest request;

    @Autowired
    UserDao userDao;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }



    @Test
    public void getLogicPageTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void getSignupPageTest() throws Exception {
        this.mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("signupForm"));
    }




    //TODO: This should go to login page, not signup page! Thus doesn't work yet!
    @Test
    public void signupSucessTest() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("email", "testTester@test.com")
                .param("password", "superPassword")
                .param("firstName", "superKid")
                .param("lastName", "superMan")
                .param("gender", "MALE")
                .param("hasCreditCard","false")
                .param("creditCardExpireMonth","11")
                .param("creditCardExpireYear","18")
                .param("securityCode","500")
                .param("creditCardNumber","1111000011110000"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("confirmationMessage"));
    }


    @Test
    public void signupFailTest() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("email", "email@email.com")
                .param("password", "shrt")
                .param("firstName", "superKid")
                .param("lastName", "superMan")
                .param("gender", "MALE")
                .param("hasCreditCard","false"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "password"));
    }

    @Test
    public void UserAlreadyExistsSignupTest() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("email", "ese@unibe.ch")
                .param("password", "securepassword")
                .param("firstName", "superman")
                .param("lastName", "batman")
                .param("gender", "MALE"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("signupForm"));
    }


    @Test
    public void googleSignupTest() throws Exception {
        this.mockMvc.perform(post("/googleSignup")
                .param("name","googleTestUser")
                .param("email","googleTest@googleTest.com"))
                .andExpect(status().isOk());
    }



    @Test
    public void getEditProfilePageTest() throws Exception {
        this.mockMvc.perform(get("/profile/editProfile")
                .principal(getPrincipal("ese@unibe.ch")))
                .andExpect(status().isOk())
                .andExpect(view().name("editProfile"))
                .andExpect(model().attributeExists("editProfileForm", "currentUser","hasCreditCard"));
    }


    @Test
    public void editProfileSuccessTest() throws Exception {
        User user = userDao.findByUsername(getPrincipal("ese@unibe.ch").getName());
        this.mockMvc.perform(post("/profile/editProfile")
                .principal(getPrincipal("ese@unibe.ch"))
                .param("username", "ese@unibe.ch")
                .param("password", "eseeseese")
                .param("firstName", "stupid")
                .param("lastName", "wayne")
                .param("hasCreditCard", "false"))
                .andExpect(view().name("redirect:../user?id=" + user.getId()))
                .andExpect(flash().attributeExists("confirmationMessage"));
    }



    @Test
    public void getProfileBalancePageTest() throws Exception {
        this.mockMvc.perform(get("/profile/balance")
                .principal(getPrincipal("jane@doe.com")))
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
