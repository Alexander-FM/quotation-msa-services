package com.codesoft.quotations.quotation.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.quotations.client.material.dto.MaterialResponseDto;
import com.codesoft.quotations.client.material.service.MaterialClient;
import com.codesoft.quotations.modules.module.entity.ModuleEntity;
import com.codesoft.quotations.modules.module.repository.ModuleRepository;
import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationEntity;
import com.codesoft.quotations.quotation.exception.QuotationException;
import com.codesoft.quotations.quotation.exception.QuotationMessage;
import com.codesoft.quotations.quotation.mapper.QuotationDetailFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationDetailSubItemFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationFieldsMapper;
import com.codesoft.quotations.quotation.repository.QuotationRepository;
import com.codesoft.utils.GenericResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

  private final QuotationRepository quotationRepository;

  private final ModuleRepository moduleRepository;

  private final QuotationFieldsMapper quotationFieldsMapper;

  private final QuotationDetailFieldsMapper quotationDetailFieldsMapper;

  private final QuotationDetailSubItemFieldsMapper quotationDetailSubItemFieldsMapper;

  private final MaterialClient materialClient;

  @Override
  public List<QuotationResponseDto> findAll() {
    final List<QuotationEntity> entities = this.quotationRepository.findAll();
    return quotationFieldsMapper.toDtoList(entities);
  }

  @Override
  public QuotationResponseDto findById(Integer id) {
    final Optional<QuotationEntity> entityOptional = this.quotationRepository.findById(id);
    return entityOptional.map(this.quotationFieldsMapper::toDto)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
  }

  @Override
  public QuotationResponseDto create(final QuotationRequestDto requestDto) {
    try {
      log.info("Creating quotation with data: {}", requestDto);
      final QuotationEntity entityMapped = this.quotationFieldsMapper.toEntity(requestDto);
      entityMapped.setState("BORRADOR");
      log.info("Iniciando llamada al micro servicio de materiales para validar datos relacionados.");
      for (var detail : entityMapped.getDetails()) {
        log.info("Consultando conceptos del modulo con ID: {}", detail.getModule().getId());
        ModuleEntity moduleEntity = this.moduleRepository.findById(detail.getModule().getId())
          .orElseThrow(() -> new QuotationException(QuotationMessage.MODULE_NOT_FOUND));
        log.info("Módulo encontrado: {}", moduleEntity);
        detail.setModule(moduleEntity);
        detail.setOverheadsPercentage(moduleEntity.getOverheadsCostPercentage());
        detail.setOverheadsAmount(moduleEntity.getOverheadsCostPercentage());
        detail.setFeePercentage(moduleEntity.getFeePercentage());
        detail.setFeeAmount(moduleEntity.getFeePercentage());
        detail.setRebatePercentage(moduleEntity.getRebatePercentage());
        detail.setRebateAmount(moduleEntity.getRebatePercentage());
        detail.setRebatePercentage(moduleEntity.getRebatePercentage());
        detail.setRebateAmount(moduleEntity.getRebatePercentage());
        log.info("Validando materiales para los subítems del detalle del módulo con ID: {}", detail.getId());
        for (var subItem : detail.getSubItems()) {
          ResponseEntity<GenericResponse<MaterialResponseDto>> materialResponse =
            this.materialClient.searchMaterialById(subItem.getMaterialId());
          if (materialResponse.getBody() == null || materialResponse.getBody().getCode() == -1) {
            log.warn("Material with ID {} not found in Material microservice.", subItem.getMaterialId());
            throw new QuotationException(QuotationMessage.MATERIAL_NOT_FOUND);
          }
          log.info("Material encontrado: {}", materialResponse.getBody().getBody());
          subItem.setMaterialId(materialResponse.getBody().getBody().getId());
          subItem.calculateTotalPrice();
          subItem.calculateUnitPrice();
        }
      }
      log.info("Mapped quotation entity: {}", entityMapped);
      final QuotationEntity entity = this.quotationRepository.save(entityMapped);
      return this.quotationFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      log.warn("Data integrity violation when creating quotation: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_ALREADY_EXISTS);
    } catch (final FeignException ex) {
      log.error("Error calling Material microservice: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.MATERIAL_SERVICE_UNAVAILABLE);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.quotationRepository.findById(id)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    this.quotationRepository.deleteById(id);
  }
}
