package ggc.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception for reporting file not found */
public class FileCouldNotBeFoundException extends CommandException {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109091821L;

  /** @param filename Problematic filename to report. */
  public FileCouldNotBeFoundException(String filename) {
    super(Message.fileNotFound(filename));
  }

}
