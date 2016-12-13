package ch.unibe.ese.team1.controllerTest;

import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
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

import java.io.IOError;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class PlaceAdControllerTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void loadPage() throws Exception{
        this.mockMvc.perform(get("/profile/placeAd").param("id","1").principal(getPrincipal("jane@doe.com"))
        ).andExpect(view().name("placeAd")).andExpect(status().isOk());
    }

    @Test
    public void uploadPictures() throws Exception{
        MockMultipartFile pictureFile = new MockMultipartFile("pic", "pic.png", "image/png", "picture".getBytes());

        MvcResult result= this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/profile/placeAd/uploadPictures")
                .file(pictureFile))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().length()>0);

        }

    @Test
    public void createAdWithoutParameter() throws Exception{
        this.mockMvc.perform(post("/profile/placeAd").principal(getPrincipal("jane@doe.com"))
        ).andExpect(view().name("placeAd"));
    }

    @Test
    public void createAdWithParameter() throws  Exception{
        this.mockMvc.perform(post("/profile/placeAd").principal(getPrincipal("jane@doe.com"))
                .param("title","title")
                .param("property","HOUSE")
                .param("street","street")
                .param("city","3000 - Bern")
                .param("moveInDate","12-12-2017")
                .param("price","100")
                .param("squareFootage","100")
                .param("NumberRooms","2.5")
                .param("roomDescription","description")
                .param("instantBuyPrice","0")
        ).andExpect(status().is3xxRedirection()).andExpect(model().hasNoErrors());
    }

    @Test
    public void createAdWithParameterandId() throws  Exception{
        this.mockMvc.perform(post("/profile/placeAd").principal(getPrincipal("jane@doe.com"))
                .param("title","title")
                .param("property","HOUSE")
                .param("street","street")
                .param("city","3000 - Bern")
                .param("moveInDate","12-12-2017")
                .param("price","100")
                .param("squareFootage","100")
                .param("NumberRooms","2.5")
                .param("roomDescription","description")
                .param("instantBuyPrice","0")
                .param("id","0")
                .param("premium","true")
        ).andExpect(status().is3xxRedirection()).andExpect(model().hasNoErrors());
    }

    @Test
    public void getNoUploadedPictures() throws Exception{
        MvcResult result = this.mockMvc.perform(post("/profile/placeAd/getUploadedPictures").principal(getPrincipal("jane@doe.com"))
        ).andExpect(status().isOk()).andReturn();

        assertEquals("",result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().length()==0);
    }

    @Test
    public void getUploadedPictures() throws Exception{

        MockMultipartFile pictureFile = new MockMultipartFile("pic", "pic.png", "image/png", "picture".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/profile/placeAd/uploadPictures")
                .file(pictureFile))
                .andExpect(status().isOk()).andReturn();

        MvcResult result  = this.mockMvc.perform(post("/profile/placeAd/getUploadedPictures").principal(getPrincipal("jane@doe.com"))
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().length()>0);
    }

    @Test
    public void deleteUploadedPictures() throws Exception{

        MockMultipartFile pictureFile = new MockMultipartFile("pic", "pic.png", "image/png", "picture".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/profile/placeAd/uploadPictures")
                .file(pictureFile))
                .andExpect(status().isOk()).andReturn();

      MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/profile/placeAd/deletePicture").principal(getPrincipal("jane@doe.com"))
        .param("url","pic.png")).andReturn();

        assertTrue(result.getResponse().getContentAsString().length()==0);
    }
    @Test
    public void deleteFileNotExisting() throws NoSuchFileException,Exception{

      MockMultipartFile pictureFile = new MockMultipartFile("pic", "pic.png", "image/png", "picture".getBytes());

      this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/profile/placeAd/uploadPictures")
              .file(pictureFile))
              .andExpect(status().isOk()).andReturn();

      this.mockMvc.perform(post("/profile/placeAd/deletePicture").principal(getPrincipal("jane@doe.com"))
              .param("url","notexisting")).andExpect(status().isOk());

  }

    @Test
    public void validateInvalidEmail() throws Exception{
        MvcResult result  =this.mockMvc.perform(post("/profile/placeAd/validateEmail").principal(getPrincipal("jane@doe.com"))
                .param("email","noMail").param("alreadyIn","")).andExpect(status().isOk()).andReturn();

        assertEquals("\"This user does not exist, did your roommate register?\"",result.getResponse().getContentAsString());
    }

    @Test
    public void validateValidEmailIn() throws Exception{
        MvcResult result  =this.mockMvc.perform(post("/profile/placeAd/validateEmail").principal(getPrincipal("jane@doe.com"))
                .param("email","ese@unibe.ch").param("alreadyIn","test@gmx.ch;ese@unibe.ch")).andExpect(status().isOk()).andReturn();

        assertEquals("\"You already added this person.\"",result.getResponse().getContentAsString());

    }

    @Test
    public void validateValidEmailNotIn() throws Exception{
        MvcResult result  =this.mockMvc.perform(post("/profile/placeAd/validateEmail").principal(getPrincipal("jane@doe.com"))
                .param("email","ese@unibe.ch").param("alreadyIn","test@gmx.ch;test@gmail.com")).andExpect(status().isOk()).andReturn();

        assertEquals("\"ese@unibe.ch\"",result.getResponse().getContentAsString());

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
