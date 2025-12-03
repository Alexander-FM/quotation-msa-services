package com.codesoft.catalogs.adjustment_factor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorMessageEnum;
import com.codesoft.catalogs.adjustment_factor.mapper.AdjustmentFactorFieldsMapper;
import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import com.codesoft.catalogs.adjustment_factor.repository.AdjustmentFactorRepository;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdjustmentFactorServiceImplTests {

  private static final String ADJUSTMENT_FACTOR_LIST = "adjustment_factor_list.json";

  private static final String ADJUSTMENT_FACTOR = "adjustment_factor.json";

  private static final String ADJUSTMENT_FACTOR_REQUEST = "adjustment_factor_request.json";

  private static final String ADJUSTMENT_FACTOR_RESPONSE = "adjustment_factor_response.json";

  private static final String ADJUSTMENT_FACTOR_ENTITY = "adjustment_factor_entity.json";

  private static final Integer EXISTING_ID = 1;

  private static final Integer NO_EXISTING_ID = 120;

  @Mock
  private AdjustmentFactorRepository repository;

  @Mock
  private AdjustmentFactorFieldsMapper mapper;

  @InjectMocks
  private AdjustmentFactorServiceImpl service;

  private List<AdjustmentFactorResponseDto> adjustmentFactorResponseDtoList;

  private List<AdjustmentFactorEntity> adjustmentFactorEntityList;

  private AdjustmentFactorResponseDto adjustmentFactorResponseDto;

  private AdjustmentFactorEntity adjustmentFactorEntity;

  @Test
  void retrieveAllAdjustmentFactorsSuccessfullyTest() throws IOException {
    this.adjustmentFactorResponseDtoList = TestUtils.getListResource(ADJUSTMENT_FACTOR_LIST,
      AdjustmentFactorResponseDto.class, getClass());

    this.adjustmentFactorEntityList = TestUtils.getListResource(ADJUSTMENT_FACTOR_LIST,
      AdjustmentFactorEntity.class, getClass());

    when(repository.findAll()).thenReturn(this.adjustmentFactorEntityList);
    when(mapper.toDtoList(this.adjustmentFactorEntityList)).thenReturn(this.adjustmentFactorResponseDtoList);

    final List<AdjustmentFactorResponseDto> result = service.findAll();

    assertEquals(this.adjustmentFactorResponseDtoList, result);
    verify(repository).findAll();
    verify(mapper).toDtoList(adjustmentFactorEntityList);
  }

  @Test
  void retrieveByIdAdjustmentFactorsSuccessfullyTest() throws IOException {
    this.adjustmentFactorResponseDto = TestUtils.getResource(ADJUSTMENT_FACTOR,
      AdjustmentFactorResponseDto.class, getClass());
    this.adjustmentFactorEntity = TestUtils.getResource(ADJUSTMENT_FACTOR,
      AdjustmentFactorEntity.class, getClass());

    when(repository.findById(EXISTING_ID)).thenReturn(Optional.ofNullable(this.adjustmentFactorEntity));
    when(mapper.toDto(this.adjustmentFactorEntity)).thenReturn(this.adjustmentFactorResponseDto);

    final AdjustmentFactorResponseDto result = service.findById(EXISTING_ID);

    assertEquals(this.adjustmentFactorResponseDto, result);
    verify(repository).findById(EXISTING_ID);
    verify(mapper).toDto(adjustmentFactorEntity);
  }

  @Test
  void retrieveByIdAdjustmentFactorsWhenNoExistIdErrorTest() {
    when(repository.findById(NO_EXISTING_ID)).thenReturn(Optional.empty());

    final BaseException baseExceptionExpected = assertThrows(BaseException.class, () -> service.findById(NO_EXISTING_ID));

    assertEquals(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND, baseExceptionExpected.getErrorCodeInterface());
    verify(repository).findById(NO_EXISTING_ID);
  }

  @Test
  void createSuccessfullyTest() throws IOException {
    final AdjustmentFactorRequestDto requestDto = TestUtils.getResource(ADJUSTMENT_FACTOR_REQUEST,
      AdjustmentFactorRequestDto.class, getClass());
    final AdjustmentFactorEntity entity = TestUtils.getResource(ADJUSTMENT_FACTOR_ENTITY,
      AdjustmentFactorEntity.class, getClass());
    final AdjustmentFactorResponseDto responseDto = TestUtils.getResource(ADJUSTMENT_FACTOR_RESPONSE,
      AdjustmentFactorResponseDto.class, getClass());

    when(mapper.toEntity(requestDto)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(responseDto);

    final AdjustmentFactorResponseDto result = service.create(requestDto);

    assertNotNull(result);
    assertEquals(responseDto, result);
    verify(mapper).toEntity(requestDto);
    verify(repository).save(entity);
    verify(mapper).toDto(entity);
  }

}
