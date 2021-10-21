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

  private int _value;

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);

    addIntegerField("advanceValue", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    int date = integerField("advanceValue");
    if (!_receiver.advanceCurrentDate(date)) {
      throw new InvalidDateException(date);
    }
  }

}
