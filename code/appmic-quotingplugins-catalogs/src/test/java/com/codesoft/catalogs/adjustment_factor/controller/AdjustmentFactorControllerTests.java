package com.codesoft.catalogs.adjustment_factor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AdjustmentFactorController.class)
public class AdjustmentFactorControllerTests {

  private static final String ADJUSTMENT_FACTOR_DTO = "adjustment_factor_dto.json";

  private static final String ADJUSTMENT_FACTOR_REQUEST_DTO = "adjustment_factor_request_dto.json";

  private static final String ADJUSTMENT_FACTOR_RESPONSE_DTO = "adjustment_factor_response_dto.json";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AdjustmentFactorService adjustmentFactorService;

  private AdjustmentFactorResponseDto adjustmentFactorResponseDto;

  private AdjustmentFactorRequestDto adjustmentFactorRequestDto;

  private AdjustmentFactorResponseDto createResponseDto;

  @BeforeEach
  public void setUp() throws IOException {
    this.adjustmentFactorResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_DTO,
      AdjustmentFactorResponseDto.class, getClass());
    this.adjustmentFactorRequestDto = TestUtils.getResource(ADJUSTMENT_FACTOR_REQUEST_DTO,
      AdjustmentFactorRequestDto.class, getClass());
    this.createResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_RESPONSE_DTO,
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

  @Test
  public void createSuccessfullyTest() throws Exception {
    when(adjustmentFactorService.create(any(AdjustmentFactorRequestDto.class)))
      .thenReturn(createResponseDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/adjustment-factors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(adjustmentFactorRequestDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.body.id").value(createResponseDto.getId()))
      .andExpect(jsonPath("$.body.name").value(createResponseDto.getName()))
      .andExpect(jsonPath("$.body.value").value(createResponseDto.getValue()))
      .andReturn();
  }

//  @Test
//  public void updateSuccessfullyTest() throws Exception {
//    final AdjustmentFactorResponseDto updatedResponse = AdjustmentFactorResponseDto.builder()
//      .id(1)
//      .name("Factor Controller Test")
//      .value(new java.math.BigDecimal("1.85"))
//      .isActive(true)
//      .build();
//
//    when(adjustmentFactorService.update(eq(1), any(AdjustmentFactorRequestDto.class)))
//      .thenReturn(updatedResponse);
//
//    mockMvc.perform(MockMvcRequestBuilders.put("/api/adjustment-factors/1")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(adjustmentFactorRequestDto)))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.body.id").value(updatedResponse.getId()))
//      .andExpect(jsonPath("$.body.name").value(updatedResponse.getName()))
//      .andExpect(jsonPath("$.body.value").value(updatedResponse.getValue()))
//      .andReturn();
//  }
//
//  @Test
//  public void updateShouldReturnNotFoundWhenIdDoesNotExistTest() throws Exception {
//    when(adjustmentFactorService.update(eq(999), any(AdjustmentFactorRequestDto.class)))
//      .thenThrow(new BaseException(BaseErrorMessage.NOT_FOUND));
//
//    mockMvc.perform(MockMvcRequestBuilders.put("/api/adjustment-factors/999")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(adjustmentFactorRequestDto)))
//      .andExpect(status().isNotFound())
//      .andExpect(jsonPath("$.body.errorCode").value(HttpStatus.NOT_FOUND.value()))
//      .andReturn();
//  }

}
