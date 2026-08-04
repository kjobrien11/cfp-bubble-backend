package com.cfpbubble.cfpbubble.exception;

public class SeasonStartedException extends RuntimeException {
  public SeasonStartedException() {
    super("Cannot create bubble: season has already started");
  }

}
