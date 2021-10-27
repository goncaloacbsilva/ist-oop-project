package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import java.io.IOException;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  String filename;
  private Form _form;

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    _form = new Form();
  }

  @Override
  public final void execute() throws CommandException {
    try {
      try {
        _receiver.save();
      } catch (MissingFileAssociationException e) {
        _form.addStringField("filename", Message.newSaveAs());
        _form.parse();
        _receiver.saveAs(_form.stringField("filename"));
      }
    } catch (IOException ignored) {
      throw new FileOpenFailedException(_receiver.getFilename());
    }
  }

}
