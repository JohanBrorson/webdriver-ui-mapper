package com.github.johanbrorson.uimapper.exceptions;

public class LocatorFileIOException extends RuntimeException {
  private static final long serialVersionUID = -7450168303860568729L;

  public LocatorFileIOException(final Throwable throwable) {
    super(throwable);
  }

  public LocatorFileIOException(final String message) {
    super(message);
  }

}
