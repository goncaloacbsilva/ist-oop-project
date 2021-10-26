package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import ggc.core.Date;
import ggc.core.product.Product;
//
import ggc.core.product.SimpleProduct;
import ggc.core.product.Batch;
//
import ggc.core.product.Batch;
import ggc.core.exception.BadEntryException;
import ggc.core.partner.Partner;


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
    //DEBUG
    Partner pa = new Partner("P2", "Manuel", "SEI_LA");
    Product oxigenio = new SimpleProduct("OXIGENIO");
    Product hidrogenio = new SimpleProduct("HIDROGENIO");
    oxigenio.addBatch(pa, 10, 2.58);
    oxigenio.addBatch(pa, 200, 1.50);
    hidrogenio.addBatch(pa, 82, 4.58);
    hidrogenio.addBatch(pa, 5, 10.50);
    _products.add(oxigenio);
    _products.add(hidrogenio);

  }

  public void advanceDate(int value) {
    _date = _date.add(value);
  }

  public Set<Product> getProducts() {
    return new HashSet<>(_products);
  }

  public List<Batch> getAvailableBatches() {
    List<Batch> batchList = new ArrayList<>();
    for(Product product : _products) {
      for(Batch batch : product.getBatches()) {
        batchList.add(batch);
      }
    }

    return batchList;
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
