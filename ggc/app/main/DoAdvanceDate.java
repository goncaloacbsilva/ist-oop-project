package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);

    addIntegerField("advanceValue", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    int advanceValue = integerField("advanceValue");
    if (advanceValue < 0) {
      throw new InvalidDateException(advanceValue);
    }

    _receiver.advanceDate(advanceValue);
  }

}
