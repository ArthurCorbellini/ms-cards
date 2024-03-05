package com.artcorb.cards.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.artcorb.cards.controller.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "CRUD REST API for Cards", description = "CREATE, READ, UPDATE and DELETE cards")
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardController extends BaseController {

}
