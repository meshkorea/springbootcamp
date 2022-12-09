package com.vroong.product.adapter.in.rest;

import static com.vroong.product.adapter.in.rest.Fixtures.aProductDto;
import static com.vroong.product.config.Constants.DEFAULT_CHARSET;
import static com.vroong.product.config.Constants.V1_MEDIA_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vroong.product.rest.ProductApiController;
import com.vroong.product.rest.ProductApiDelegate;
import com.vroong.product.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@WithMockUser
class ProductApiDelegateImplTest {

  private MockMvc mvc;

  @Autowired
  private ProductApiDelegate apiDelegate;

  @Test
  public void createProduct() throws Exception {
    //given
    //when
    ResultActions res = mvc.perform(
        post("/api/products")
            .contentType(V1_MEDIA_TYPE)
            .content(TestUtils.convertObjectToString(aProductDto()))
            .characterEncoding(DEFAULT_CHARSET)
    ).andDo(print());

    //then
    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  public void getProduct() throws Exception {
    //given
    //when
    ResultActions res = mvc.perform(
        get("/api/products/{productId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(DEFAULT_CHARSET)
    ).andDo(print());

    //then
    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.productId").exists());
  }
  
  @Test
  public void getProductList() throws Exception {
    //given
    //when
    ResultActions res = mvc.perform(
        get("/api/products")
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(DEFAULT_CHARSET)
    ).andDo(print());

    //then
    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.data").isArray());
    res.andExpect(jsonPath("$.page").exists());
  }

  @Test
  public void updateProduct() throws Exception {
    //given
    //when
    ResultActions res = mvc.perform(
        patch("/api/products/{productId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(DEFAULT_CHARSET)
            .content(TestUtils.convertObjectToString(aProductDto()))
    ).andDo(print());

    //then
    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  public void deleteProduct() throws Exception {
    //given
    //when
    ResultActions res = mvc.perform(
        delete("/api/products/{productId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(DEFAULT_CHARSET)
    ).andDo(print());

    //then
    res.andExpect(status().is2xxSuccessful());
  }

  @BeforeEach
  void setUp() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new ProductApiController(apiDelegate))
        .addFilters(new CharacterEncodingFilter(DEFAULT_CHARSET.toString(), true))
        .build();
  }
}