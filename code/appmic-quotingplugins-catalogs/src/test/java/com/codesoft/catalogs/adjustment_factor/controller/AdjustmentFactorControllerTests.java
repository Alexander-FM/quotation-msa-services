package com.codesoft.catalogs.adjustment_factor.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdjustmentFactorController.class)
public class AdjustmentFactorControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AdjustmentFactorService adjustmentFactorService;

  private AdjustmentFactorResponseDto adjustmentFactorResponseDto;

  @BeforeEach
  public void setUp() throws IOException {
    String ADJUSTMENT_FACTOR_DTO = "adjustment_factor_dto.json";
    this.adjustmentFactorResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_DTO,
      AdjustmentFactorResponseDto.class, getClass());
  }

  @Test
  public void retrieveByIdAdjustmentFactorsSuccessfullyTest() throws Exception {
    when(adjustmentFactorService.findById(1)).thenReturn(adjustmentFactorResponseDto);
    mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/adjustment-factors/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.body.id")
        .value(adjustmentFactorResponseDto.getId())).andReturn();
  }

  @Test
  public void retrieveByIdAdjustmentFactorsErrorTest() throws Exception {
    when(adjustmentFactorService.findById(10)).thenThrow(new BaseException(BaseErrorMessage.NOT_FOUND));
    mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/adjustment-factors/10"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.body.errorCode")
        .value(HttpStatus.NOT_FOUND.value())).andReturn();
  }

}
