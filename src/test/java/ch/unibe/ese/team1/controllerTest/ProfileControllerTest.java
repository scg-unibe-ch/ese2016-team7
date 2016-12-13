package ch.unibe.ese.team1.controllerTest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    @Autowired
    UserService userService;

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





    @Test
    public void signupSucessTest() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("email", "test@test.com")
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
    public void emailExists() throws Exception{
        MvcResult result = this.mockMvc.perform(post("/signup/doesEmailExist")
                .param("email", "ese@unibe.ch")
        ).andExpect(status().isOk()).andReturn();
        assertEquals("true",result.getResponse().getContentAsString());
    }

    @Test
    public void emailDoesNotExists() throws Exception{
        MvcResult result = this.mockMvc.perform(post("/signup/doesEmailExist")
                .param("email", "ese@unxldkfhnvibe.ch")
        ).andExpect(status().isOk()).andReturn();
        assertEquals("false",result.getResponse().getContentAsString());
    }

    @Test
    public void deleteExistingCreditCard() throws Exception{
        assertTrue(userDao.findUserById(3).getHasCreditCard());
        this.mockMvc.perform(post("/profile/editProfile/deleteCreditCardFromUser")
                .param("userId","3")).andExpect(status().isOk());
        assertFalse(userDao.findUserById(3).getHasCreditCard());
    }

    @Test
    public void deleteNonExistingCreditCard() throws Exception{
        long id = userDao.findByUsername("user@bern.com").getId();

        assertFalse(userDao.findUserById(id).getHasCreditCard());
        this.mockMvc.perform(post("/profile/editProfile/deleteCreditCardFromUser")
                .param("userId",Long.toString(id))).andExpect(status().isOk());
        assertFalse(userDao.findUserById(id).getHasCreditCard());
    }

    @Test
    public void addNonExistingCreditCard() throws  Exception{
        long id = userDao.findByUsername("user@bern.com").getId();
        assertFalse(userDao.findUserById(id).getHasCreditCard());
        this.mockMvc.perform(post("/profile/editProfile/addCreditCardToUser")
                .param("userId",Long.toString(id))).andExpect(status().isOk());
        assertTrue(userDao.findUserById(id).getHasCreditCard());
    }

    @Test
    public void addExistingCreditCard() throws  Exception{
        long id = userDao.findByUsername("ese@unibe.ch").getId();
        assertTrue(userDao.findUserById(id).getHasCreditCard());
        this.mockMvc.perform(post("/profile/editProfile/addCreditCardToUser")
                .param("userId",Long.toString(id))).andExpect(status().isOk());
        assertTrue(userDao.findUserById(id).getHasCreditCard());
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
        User user = userDao.findByUsername("googleTest@googleTest.com");
        assertNotEquals(user,null);
    }

    @Test
    public void editProfileWrong() throws Exception{
        this.mockMvc.perform(post("/profile/editProfile")
                .principal(getPrincipal("ese@unibe.ch"))
                .param("username", "ese@unibe.ch")
                .param("password", "eseeseese")
                .param("lastName", "wayne")
                .param("hasCreditCard", "false")
                .param("securityCode","asdf"))
                .andExpect(view().name("editProfile"));
    }

    @Test
    public void displayUserPage() throws Exception{
        this.mockMvc.perform(get("/user").principal(getPrincipal("jane@doe.com"))
        .param("id","1")
        ).andExpect(view().name("user")).andExpect(model().attributeExists("principalID"))
        .andExpect(status().isOk()).andExpect(model().attributeExists("user"))
        .andExpect(model().attributeExists("messageForm"));
    }

    @Test
    public void displayUserPageNoPrincipal() throws Exception{
        this.mockMvc.perform(get("/user")
                .param("id","1")
        ).andExpect(view().name("user"))
                .andExpect(status().isOk()).andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("messageForm"));
    }

    @Test
    public void showBalance() throws Exception{
        this.mockMvc.perform(get("/profile/schedule").principal(getPrincipal("ese@unibe.ch"))
        ).andExpect(view().name("schedule")).andExpect(model().attributeExists("visits"))
        .andExpect(model().attributeExists("presentations")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "jane@doe.com")
    public void showVisitors() throws Exception{
        this.mockMvc.perform(get("/profile/visitors").param("visit","2"))
                .andExpect(view().name("visitors")).andExpect(model().attributeExists("visitors"))
                .andExpect(model().attributeExists("ad")).andExpect(status().isOk());
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
                .param("hasCreditCard", "false")
                .param("securityCode","000"))
                .andExpect(view().name("updatedProfile"))
                .andExpect(model().attributeExists("message"));
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
