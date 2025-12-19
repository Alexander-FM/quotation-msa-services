package com.codesoft.quotations.quotation.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.service.QuotationService;
import com.codesoft.quotations.quotation.utils.QuotationConstants;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.endpoints.quotation}")
@RequiredArgsConstructor
@Slf4j
public class QuotationController {

  private final QuotationService quotationService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<QuotationResponseDto>>> retrieve() {
    final List<QuotationResponseDto> responseDtoList = this.quotationService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(QuotationConstants.FOUND_MESSAGE, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<QuotationResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final QuotationResponseDto responseDto = this.quotationService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(QuotationConstants.FOUND_MESSAGE, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<QuotationResponseDto>> create(@Valid @RequestBody final QuotationRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final QuotationResponseDto responseDto = this.quotationService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(GenericResponseUtils.buildGenericResponseSuccess(QuotationConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<QuotationResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final QuotationRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final QuotationResponseDto existing = this.quotationService.findById(id);
    requestDto.setId(existing.getId());
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(QuotationConstants.UPDATED_MESSAGE, this.quotationService.create(requestDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.quotationService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(QuotationConstants.REMOVED_MESSAGE, null));
  }
}
