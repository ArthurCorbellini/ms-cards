package com.artcorb.cards.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Error Response", description = "Schema to hold error response information")
@Data
@AllArgsConstructor
public class ResponseErrorDto {

  @Schema(description = "API path called by client", example = "/path/example")
  private String apiPath;

  @Schema(description = "Error code representing the error happened", example = "500")
  private HttpStatus errorCode;

  @Schema(description = "Error message representing the error happened",
      example = "An error occurred. Please try again or contact Dev team")
  private String errorMessage;

  @Schema(description = "Time when the error happened")
  private LocalDateTime errorTime;
}
