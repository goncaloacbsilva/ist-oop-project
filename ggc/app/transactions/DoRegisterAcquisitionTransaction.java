package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.RecipeTextComponent;
import pt.tecnico.uilib.forms.Form;


/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("amount", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.getPartner(stringField("partnerId"));

      try {
        _receiver.getProduct(stringField("productId"));
      } catch (UnknownObjectKeyException ignored) {
        


        if (Form.confirm(Message.requestAddRecipe())) {
          List<RecipeTextComponent> recipe = new ArrayList<>();

          int componentsCount = Form.requestInteger(Message.requestNumberOfComponents());
          double alpha = Form.requestReal(Message.requestAlpha());

          for(int i = 0; i < componentsCount; i++) {
            String productId = Form.requestString(Message.requestProductKey());
            int amount = Form.requestInteger(Message.requestAmount());
            recipe.add(new RecipeTextComponent(productId, amount));
          }

          _receiver.addDerivativeProduct(stringField("productId"), recipe, alpha);

        } else {
          _receiver.addSimpleProduct(stringField("productId"));
        }

      }

      _receiver.registerAcquisition(stringField("partnerId"), stringField("productId"), realField("price"), integerField("amount"));

    } catch (UnknownObjectKeyException exception) {
      switch(exception.getType()) {
        case PARTNER:
          throw new UnknownPartnerKeyException(exception.getObjectKey());
        case PRODUCT:
          throw new UnknownProductKeyException(exception.getObjectKey());
        default:
          
      }
    } catch (NotEnoughResourcesException exception) {
      throw new UnavailableProductException(exception.getObjectKey(), exception.getRequestedAmount(), exception.getAvailableAmount());
    }
  }

}
