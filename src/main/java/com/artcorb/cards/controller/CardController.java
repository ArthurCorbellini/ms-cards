package com.artcorb.cards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.artcorb.cards.cfg.CardsEnvironments;
import com.artcorb.cards.controller.base.BaseController;
import com.artcorb.cards.dto.CardDto;
import com.artcorb.cards.dto.ResponseDto;
import com.artcorb.cards.dto.ResponseErrorDto;
import com.artcorb.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "CRUD REST API for Cards", description = "CREATE, READ, UPDATE and DELETE cards")
@RestController
@Validated
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardController extends BaseController {

  @Autowired
  private ICardService iCardService;
  @Autowired
  private CardsEnvironments environmentConfig;
  @Autowired
  private Environment environment;
  @Value("${build.version}")
  private String buildVersion;

  @Operation(summary = "Create Card REST API", description = "REST API to create new card")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_201),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(
      regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
    iCardService.createCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
  }

  @Operation(summary = "Fetch Card REST API",
      description = "REST API to fetch card based on a mobile number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/fetch")
  public ResponseEntity<CardDto> fetchCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber) {
    CardDto cardDto = iCardService.fetchCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(cardDto);
  }

  @Operation(summary = "Update Card REST API",
      description = "REST API to update loan based on a card number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_417, description = MESSAGE_417_UPDATE),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardDto) {
    if (iCardService.updateCard(cardDto)) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
    }
  }

  @Operation(summary = "Delete Card REST API",
      description = "REST API to delete Card based on a mobile number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_417, description = MESSAGE_417_DELETE),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(
      regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
    if (iCardService.deleteCard(mobileNumber)) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
    }
  }

  @Operation(summary = "Get build information",
      description = "Get build information that is deployed into accounts microservice")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @Operation(summary = "Get Java version", description = "Get Java version of enviroment")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(summary = "Get Contact Info",
      description = "Contact Info details that can be reached out in case of any issues")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/contact-info")
  public ResponseEntity<CardsEnvironments> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(environmentConfig);
  }
}
