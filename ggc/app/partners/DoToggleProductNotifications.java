package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;



//FIXME import classes

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");

    try {
      if (_receiver.toggleNotificationStatus(partnerId, productId)) {
      }
    } catch (UnknownPartnerKeyException e) {
      throw new UnknownPartnerKeyException(partnerId);
    } catch (UnknownProductKeyException e) {
      throw new UnknownProductKeyException(productId);
    }
  }
  
}
