package com.vroong.product.adapter.in.rest;

import static com.vroong.product.adapter.in.rest.Fixtures.aProductDto;
import static com.vroong.product.config.Constants.DEFAULT_CHARSET;
import static com.vroong.product.config.Constants.V1_MEDIA_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vroong.product.application.ProductService;
import com.vroong.product.domain.Product;
import com.vroong.product.rest.ProductApiController;
import com.vroong.product.rest.ProductApiDelegate;
import com.vroong.product.support.TestUtils;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

  @MockBean
  private ProductService productService;

  @Test
  public void createProduct() throws Exception {
    //given
    Product aProduct = Fixtures.aProduct();
    aProduct.setProductId(1L);

    given(productService.createProduct(any()))
        .willReturn(aProduct);

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
    long productId = 1L;
    Product aProduct = Fixtures.aProduct();
    aProduct.setProductId(productId);
    aProduct.setCreatedAt(Instant.now());
    aProduct.setUpdatedAt(Instant.now());

    given(productService.getProduct(any()))
        .willReturn(aProduct);

    //when
    ResultActions res = mvc.perform(
        get("/api/products/{productId}", productId)
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
    int size = 10;
    int page = 0;
    Product aProduct = Fixtures.aProduct();
    aProduct.setCreatedAt(Instant.now());
    aProduct.setUpdatedAt(Instant.now());
    PageImpl<Product> productPage = new PageImpl<>(
        List.of(aProduct),
        PageRequest.of(page, size),
        1
    );

    given(productService.listProducts(any(), any(), any()))
        .willReturn(productPage);

    //when
    ResultActions res = mvc.perform(
        get("/api/products")
            .param("q", Fixtures.DEFAULT_PRODUCT_NAME)
            .param("size", String.valueOf(size))
            .param("page", String.valueOf(page))
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
    Product aProduct = Fixtures.aProduct();
    aProduct.setCreatedAt(Instant.now());
    aProduct.setUpdatedAt(Instant.now());

    given(productService.updateProduct(any(), any()))
        .willReturn(aProduct);

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