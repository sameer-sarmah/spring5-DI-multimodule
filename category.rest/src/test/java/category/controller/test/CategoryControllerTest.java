package category.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import category.controller.CategoryController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers= {CategoryController.class})
public class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void getCategoriesTest() {
    	try {
			mockMvc.perform(get("/category"))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$[0].description").isString())
			.andExpect(jsonPath("$[0].categoryID").isString())
			.andExpect(jsonPath("$[0].categoryName").isString());
		} catch (Exception e) {
			e.printStackTrace();
			 Assert.fail(e.getMessage());
		}
    }
    
    public static ResultHandler print() {
        return new ResultHandler() {
            @Override
            public void handle(MvcResult res) throws Exception {
                System.out.println(res.getResponse().getContentAsString());
            }
        };
    }
    
    @Test
    public void getProductsByCategoryTest(){
    	try {
			mockMvc.perform(get("/products/Beverages"))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
			.andExpect(jsonPath("$[0].productID").isString())
			.andExpect(jsonPath("$[0].productName").isString())
			.andExpect(jsonPath("$[0].quantityPerUnit").isString());
		} catch (Exception e) {
			e.printStackTrace();
			 Assert.fail(e.getMessage());
		}
    }
}
