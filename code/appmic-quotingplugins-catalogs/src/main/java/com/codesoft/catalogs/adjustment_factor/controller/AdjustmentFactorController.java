package com.codesoft.catalogs.adjustment_factor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import com.codesoft.catalogs.adjustment_factor.utils.AdjustmentFactorConstants;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
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
@RequestMapping("/api/catalogs/adjustment-factor")
@RequiredArgsConstructor
public class AdjustmentFactorController {

  private final AdjustmentFactorService adjustmentFactorService;

  private final ApplicationContext context;

  private final Environment environment;

  @GetMapping
  public ResponseEntity<GenericResponse<List<AdjustmentFactorResponseDto>>> retrieve() {
    final List<AdjustmentFactorResponseDto> adjustmentFactors = adjustmentFactorService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(AdjustmentFactorConstants.FOUND_MESSAGE, adjustmentFactors));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final AdjustmentFactorResponseDto adjustmentFactors = adjustmentFactorService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(AdjustmentFactorConstants.FOUND_MESSAGE, adjustmentFactors));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> create(
    @Valid @RequestBody final AdjustmentFactorRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final AdjustmentFactorResponseDto responseDto = this.adjustmentFactorService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(GenericResponseUtils.buildGenericResponseSuccess(AdjustmentFactorConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final AdjustmentFactorRequestDto requestDto) {
    if (id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final AdjustmentFactorResponseDto existing = adjustmentFactorService.findById(id);
    requestDto.setId(existing.getId());
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(AdjustmentFactorConstants.UPDATED_MESSAGE,
        this.adjustmentFactorService.create(requestDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.adjustmentFactorService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(AdjustmentFactorConstants.REMOVED_MESSAGE, null));
  }

  /**
   * Este endpoint sirve para simular el cierre del microservice (crash). Spring cierra todos los beans, desconecta la base de datos y apaga
   * el servidor (Tomcat, Jetty, etc.). Como ya no hay nada ejecutándose el proceso de java finaliza y la aplicación se cierra por completo,
   * kubernetes detecta que el proceso murió y levanta una nueva instancia del pod. Ya que por definición los pods tiene una política de
   * reinicio Always. (restartPolicy: Always), Kubernetes dice: "¡Oye! El contenedor se apagó. Debo crear uno nuevo de inmediato".
   * <p>Resultado: El pod se reinicia automáticamente.</p>
   */
  @GetMapping("/crash")
  public void crash() {
    ((ConfigurableApplicationContext) context).close();
  }

  @GetMapping("/testLoadBalancer")
  public ResponseEntity<Object> testLoadBalancer() {
    Map<String, Object> body = new HashMap<>();
    body.put("body", this.adjustmentFactorService.findAll());
    body.put("podInfo", environment.getProperty("MY_POD_NAME") + ": " + environment.getProperty("MY_POD_IP"));
    body.put("texto", environment.getProperty("config.texto"));
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
