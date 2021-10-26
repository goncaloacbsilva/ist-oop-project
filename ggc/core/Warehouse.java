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

  /** Warehouse associated partners */
  private Set<Partner> _partners;

  public Warehouse() {
    _date = new Date(0);
    _products = new HashSet<>();
    _partners = new HashSet<>();
    //DEBUG
    Partner pa = new Partner("P2", "Manuel", "SEI_LA");
    Product oxigenio = new SimpleProduct("OXIGENIO");
    Product hidrogenio = new SimpleProduct("HIDROGENIO");
    Batch oxigenioB1 = new Batch(pa, oxigenio, 10, 2.58);
    Batch oxigenioB2 = new Batch(pa, oxigenio, 200, 1.50);
    Batch hidrogenioB1 = new Batch(pa, hidrogenio, 82, 4.58);
    Batch hidrogenioB2 = new Batch(pa, hidrogenio, 5, 10.50);

    oxigenio.addBatch(oxigenioB1);
    hidrogenio.addBatch(hidrogenioB2);

    pa.addBatch(hidrogenioB1);
    pa.addBatch(oxigenioB2);
    
    _products.add(oxigenio);
    _products.add(hidrogenio);
    _partners.add(pa);

  }

  public void advanceDate(int value) {
    _date = _date.add(value);
  }

  public Set<Product> getProducts() {
    return new HashSet<>(_products);
  }

  public List<Partner> getPartners() {
    return new ArrayList<>(_partners);
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
