package com.codesoft.catalogs.unit_of_measurement.controller;

import java.util.List;

import com.codesoft.catalogs.unit_of_measurement.dto.request.UnitOfMeasurementRequestDto;
import com.codesoft.catalogs.unit_of_measurement.dto.response.UnitOfMeasurementResponseDto;
import com.codesoft.catalogs.unit_of_measurement.service.UnitOfMeasurementService;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogs/unit-of-measurement")
@RequiredArgsConstructor
public class UnitOfMeasurementController {

  private final UnitOfMeasurementService unitOfMeasurementService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<UnitOfMeasurementResponseDto>>> retrieve() {
    final List<UnitOfMeasurementResponseDto> responseDtoList = unitOfMeasurementService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<UnitOfMeasurementResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final UnitOfMeasurementResponseDto responseDto = unitOfMeasurementService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(responseDto));
  }

  @GetMapping("/searchByName")
  public ResponseEntity<GenericResponse<UnitOfMeasurementResponseDto>> retrieveByName(
    @RequestParam(name = "name") final String name) {
    final UnitOfMeasurementResponseDto responseDto = unitOfMeasurementService.findByName(name);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<UnitOfMeasurementResponseDto>> create(
    @Valid @RequestBody final UnitOfMeasurementRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final UnitOfMeasurementResponseDto responseDto = this.unitOfMeasurementService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<UnitOfMeasurementResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final UnitOfMeasurementRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    this.unitOfMeasurementService.findById(id);
    requestDto.setId(id);
    final UnitOfMeasurementResponseDto updated = this.unitOfMeasurementService.create(requestDto);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.unitOfMeasurementService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(null));
  }
}
