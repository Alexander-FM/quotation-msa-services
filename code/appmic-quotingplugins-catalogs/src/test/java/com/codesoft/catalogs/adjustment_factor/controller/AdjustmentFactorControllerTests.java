package com.codesoft.catalogs.adjustment_factor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorException;
import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorMessageEnum;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import com.codesoft.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AdjustmentFactorController.class)
class AdjustmentFactorControllerTests {

  private static final String ADJUSTMENT_FACTOR_DTO = "adjustment_factor_dto.json";

  private static final String ADJUSTMENT_FACTOR_REQUEST_DTO = "adjustment_factor_request_dto.json";

  private static final String ADJUSTMENT_FACTOR_RESPONSE_DTO = "adjustment_factor_response_dto.json";

  private static final String ADJUSTMENT_FACTOR_LIST_DTO = "adjustment_factor_list_dto.json";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AdjustmentFactorService adjustmentFactorService;

  private AdjustmentFactorResponseDto adjustmentFactorResponseDto;

  private AdjustmentFactorRequestDto adjustmentFactorRequestDto;

  @BeforeEach
  void setUp() throws IOException {
    this.adjustmentFactorResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_DTO,
      AdjustmentFactorResponseDto.class, getClass());
    this.adjustmentFactorRequestDto = TestUtils.getResource(ADJUSTMENT_FACTOR_REQUEST_DTO,
      AdjustmentFactorRequestDto.class, getClass());
  }

  @Test
  void retrieveAllAdjustmentFactorsSuccessfullyTest() throws Exception {
    final List<AdjustmentFactorResponseDto> responseDtoList =
      TestUtils.getListResource(ADJUSTMENT_FACTOR_LIST_DTO, AdjustmentFactorResponseDto.class, getClass());

    when(this.adjustmentFactorService.findAll()).thenReturn(responseDtoList);

    mockMvc.perform(get("/api/catalogs/adjustment-factor").with(jwt()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.body").isArray())
      .andExpect(jsonPath("$.body[0].id").value(responseDtoList.getFirst().getId()))
      .andExpect(jsonPath("$.body[0].name").value(responseDtoList.getFirst().getName()))
      .andExpect(jsonPath("$.body[0].value").value(responseDtoList.getFirst().getValue()))
      .andReturn();
  }

  @Test
  void retrieveByIdAdjustmentFactorsSuccessfullyTest() throws Exception {
    when(this.adjustmentFactorService.findById(1)).thenReturn(this.adjustmentFactorResponseDto);
    mockMvc.perform(get("/api/catalogs/adjustment-factor/1").with(jwt()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.body.id")
        .value(adjustmentFactorResponseDto.getId())).andReturn();
  }

  @Test
  void retrieveByIdAdjustmentFactorsErrorTest() throws Exception {
    when(this.adjustmentFactorService.findById(10)).thenThrow(
      new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND));
    mockMvc.perform(get("/api/catalogs/adjustment-factor/10").with(jwt()))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.body.code")
        .value(31404001)).andReturn();
  }

  @Test
  void createAdjustmentFactorSuccessfullyTest() throws Exception {
    final AdjustmentFactorResponseDto createResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_RESPONSE_DTO,
      AdjustmentFactorResponseDto.class, getClass());
    when(this.adjustmentFactorService.create(any(AdjustmentFactorRequestDto.class)))
      .thenReturn(createResponseDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/catalogs/adjustment-factor").with(jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(adjustmentFactorRequestDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.body.id").value(createResponseDto.getId()))
      .andExpect(jsonPath("$.body.name").value(createResponseDto.getName()))
      .andExpect(jsonPath("$.body.value").value(createResponseDto.getValue()))
      .andReturn();
  }

  @Test
  void createAdjustmentFactorWhenIdIsPresenteInTheRequestErrorTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/catalogs/adjustment-factor").with(jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.adjustmentFactorResponseDto)))
      .andExpect(status().isBadRequest())
      .andReturn();
  }

  @Test
  void updateAdjustmentFactorSuccessfullyTest() throws Exception {
    final AdjustmentFactorResponseDto createResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_RESPONSE_DTO,
      AdjustmentFactorResponseDto.class, getClass());
    when(adjustmentFactorService.findById(2)).thenReturn(createResponseDto);
    when(adjustmentFactorService.create(any(AdjustmentFactorRequestDto.class))).thenReturn(createResponseDto);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/catalogs/adjustment-factor/2").with(jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(adjustmentFactorRequestDto)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.body.id").value(createResponseDto.getId()))
      .andExpect(jsonPath("$.body.name").value(createResponseDto.getName()))
      .andExpect(jsonPath("$.body.value").value(createResponseDto.getValue()))
      .andReturn();
  }

  @Test
  void updateAdjustmentFactorWhenIdNotExistErrorTest() throws Exception {
    when(this.adjustmentFactorService.findById(999)).thenThrow(
      new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND));

    mockMvc.perform(MockMvcRequestBuilders.put("/api/catalogs/adjustment-factor/999").with(jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(this.adjustmentFactorRequestDto)))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.body.code").value(31404001))
      .andReturn();
  }

  @Test
  void updateAdjustmentFactorWhenIdIsZeroErrorTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/catalogs/adjustment-factor/0").with(jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.adjustmentFactorRequestDto)))
      .andExpect(status().isBadRequest())
      .andReturn();
  }

  @Test
  void deleteAdjustmentFactorSuccessfullyTest() throws Exception {
    doNothing().when(adjustmentFactorService).deleteById(1);

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/catalogs/adjustment-factor/1").with(jwt()))
      .andExpect(status().isNoContent())
      .andReturn();
  }

  @Test
  void deleteAdjustmentFactorNotFoundErrorTest() throws Exception {
    doThrow(new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND)).when(adjustmentFactorService)
      .deleteById(10);

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/catalogs/adjustment-factor/10").with(jwt()))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.body.code").value(31404001))
      .andReturn();
  }

}
