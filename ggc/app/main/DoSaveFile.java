package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.io.IOException;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  String filename;

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    if (_receiver.getFilename().isEmpty()) {
      /* 
       * IMPORTANT NOTE:
       * This logic is useless
       * If the program is already associated to a file it still asks for filename but will not use it.
       * The Command class doesn't provide any method to deal with this issue.
       */
      addStringField("filename", Message.newSaveAs());
    }
  }

  @Override
  public final void execute() throws CommandException {
    try {
      try {
        _receiver.save();
      } catch (MissingFileAssociationException ignored) {
        _receiver.saveAs(stringField("filename"));
      }
    } catch (IOException ignored) {
      throw new FileOpenFailedException(_receiver.getFilename());
    }
  }
}
