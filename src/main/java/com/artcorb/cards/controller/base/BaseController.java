package com.artcorb.cards.controller.base;

public class BaseController {

  protected static final String STATUS_200 = "200";
  protected static final String MESSAGE_200 = "Request processed successfully";

  protected static final String STATUS_201 = "201";
  protected static final String MESSAGE_201 = "Register created successfully";

  protected static final String STATUS_417 = "417";
  protected static final String MESSAGE_417_UPDATE =
      "Update operation failed. Please try again or contact Dev team";
  protected static final String MESSAGE_417_DELETE =
      "Delete operation failed. Please try again or contact Dev team";

  protected static final String STATUS_500 = "500";
  protected static final String MESSAGE_500 =
      "An error occurred. Please try again or contact Dev team";
}
