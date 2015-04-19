package com.github.johanbrorson.uimapper.exceptions;

public class LocatorNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 4573949218851376573L;

  public LocatorNotFoundException() {
    super();
  }

  public LocatorNotFoundException(final String message) {
    super(message);
  }

}
