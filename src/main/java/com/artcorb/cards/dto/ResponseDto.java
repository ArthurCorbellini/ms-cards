package com.artcorb.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response", description = "Schema to hold successful response information")
@Data
@AllArgsConstructor
public class ResponseDto {

  @Schema(description = "Response status code")
  private String statusCode;

  @Schema(description = "Response status message")
  private String statusMessage;
}
