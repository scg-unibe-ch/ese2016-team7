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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
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
                .andExpect(view().name("messages"))
                .andExpect(model().attributeExists("messageForm", "messages"))
                .andExpect(status().isOk());
    }

    @Test
    public void getInboxTest() throws  Exception {

        mockMvc.perform(post("/profile/message/inbox").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getSentTest() throws  Exception {

        MvcResult result = mockMvc.perform(post("/profile/message/sent").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void getMessageTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/profile/messages/getMessage").principal(getPrincipal("jane@doe.com"))
                .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();


        String message = "{\"id\":1,\"state\":\"UNREAD\",\"subject\":\"Cool ad\",\"text\":\"Hello Mr. Wayne\\n\\nI'm very interested in your advertisement, it looks just fabulous. The pictures give a goodimpression of the room and the whole flat. Your roommates seem to be very nice. The priceseems fair. I've been living in many flats, I had a great time with various roommates.My cooking skills are seriously impressive :). I like to chill out and drink some beer,have a nice meal together. My hoobies are hiking, traveling, music, sports and books. Ireally like to visit your flat, I sent you an enquiry for a visit.\\n\\nBest wishes,\\nJane Doe\",\"dateSent\":\"12:02, 24.02.2014\",\"sender\":{\"id\":5,\"username\":\"jane@doe.com\",\"password\":\"password\",\"email\":\"jane@doe.com\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"gender\":\"FEMALE\",\"enabled\":true,\"hasCreditCard\":true,\"premiumAdMoney\":0,\"aboutMe\":\"I am a Master student from switzerland. I'm 25 years old, my hobbies are summer-sports, hiking, traveling and cooking. I enjoy spending time with friends, watching movies, going for drinks and organizing dinners. I have lived in Fräkmündegg, London and Zurich, always in flatshares and i have never had problems with my flatmates because I am a nice person.\",\"creditCardNumber\":\"4040404040404040\",\"creditCardExpireMonth\":12,\"creditCardExpireYear\":12,\"securityCode\":\"257\",\"moneySpent\":0,\"moneyEarned\":0},\"recipient\":{\"id\":4,\"username\":\"ese@unibe.ch\",\"password\":\"ese\",\"email\":\"ese@unibe.ch\",\"firstName\":\"John\",\"lastName\":\"Wayne\",\"gender\":\"MALE\",\"enabled\":true,\"hasCreditCard\":true,\"premiumAdMoney\":0,\"aboutMe\":\"I am a Master student from switzerland. I'm 25 years old, my hobbies are summer-sports, hiking, traveling and cooking. I enjoy spending time with friends, watching movies, going for drinks and organizing dinners. I have lived in Fräkmündegg, London and Zurich, always in flatshares and i have never had problems with my flatmates because I am a nice person.\",\"creditCardNumber\":\"4040404040404040\",\"creditCardExpireMonth\":12,\"creditCardExpireYear\":12,\"securityCode\":\"257\",\"moneySpent\":0,\"moneyEarned\":0}}";
        assertEquals(message, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "jane@doe.com")
    public void messageSentSuccessTest() throws Exception {
        mockMvc.perform(post("/profile/messages").principal(getPrincipal("jane@doe.com"))
                .param("recipient", "ese@unibe.ch")
                .param("subject","testSubject")
                .param("text", "testText"))
                .andExpect(view().name("messages"))
                .andExpect(model().attributeExists("messageForm", "messages"))
                .andExpect(status().isOk());
    }

    @Test
    public void messageSentFailedTest() throws Exception {
        mockMvc.perform(post("/profile/messages").principal(getPrincipal("jane@doe.com"))
                .param("recipient", "")
                .param("subject","testSubject")
                .param("text", "testText"))
                .andExpect(view().name("messages"))
                .andExpect(model().attributeExists("messageForm"))
                .andExpect(model().attributeHasFieldErrors("messageForm", "recipient"))
                .andExpect(status().isOk());
    }

    @Test
    public void readMessageTest() throws Exception {

        mockMvc.perform(get("/profile/readMessage").principal(getPrincipal("jane@doe.com"))
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void unreadTest() throws Exception {

       MvcResult result = mockMvc.perform(get("/profile/unread").principal(getPrincipal("jane@doe.com")))
                .andExpect(status().isOk())
                .andReturn();

       assertEquals(0, result.getResponse().getContentLengthLong());
    }

    @Test
    public void validateEmailTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/profile/messages/validateEmail")
                .param("email", "jane@doe.com"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("\"jane@doe.com\"",result.getResponse().getContentAsString());
    }

    @Test
    public void validateEmailTestWithoutUser() throws Exception {

        MvcResult result = mockMvc.perform(post("/profile/messages/validateEmail")
                .param("email", ""))
                .andExpect(status().isOk())
                .andReturn();

        String expected = new String("\"This user does not exist.\"");

        assertEquals(expected, result.getResponse().getContentAsString());
    }


    @Test
    public void sendMessageTest() throws Exception {

        mockMvc.perform(post("/profile/messages/sendMessage").principal(getPrincipal("jane@doe.com"))
                .param("subject", "subject")
                .param("text", "text")
                .param("recipientEmail", "recipientEmail"))
                .andExpect(status().isOk());
    }
}
