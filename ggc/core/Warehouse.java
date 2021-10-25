package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

import ggc.core.Date;
import ggc.core.product.Product;
import ggc.core.exception.BadEntryException;


/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Warehouse date object */
  private Date _date;

  /** Warehouse products */
  private Set<Product> _products;

  public Warehouse() {
    _date = new Date(0);
    _products = new HashSet<>();
  }

  public void advanceDate(int value) {
    _date = _date.add(value);
  }

  public Set<Product> getProducts() {
    return new HashSet<>(_products);
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method
  }

}
