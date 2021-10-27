package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import ggc.core.Date;
import ggc.core.product.Product;
import ggc.core.product.SimpleProduct;
import ggc.core.product.Batch;
import ggc.core.partner.Partner;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.UnknownObjectKeyException;


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
  }

  public Date getDate() {
    return _date;
  }

  public void advanceDate(int value) {
    _date = _date.add(value);
  }

  public List<Product> getProducts() {
    List<Product> products = new ArrayList<>(_products);
    Collections.sort(products);
    return products;
  }

  public Product getProduct(String id) throws UnknownObjectKeyException {
    for (Product product : getProducts()) {
      if (product.getId().equalsIgnoreCase(id)) {
        return product;
      }
    }
    throw new UnknownObjectKeyException();
  }

  public Partner getPartner(String id) throws UnknownObjectKeyException {
    for (Partner partner : getPartners()) {
      if (partner.getId().equalsIgnoreCase(id)) {
        return partner;
      }
    }
    throw new UnknownObjectKeyException();
  }

  public boolean addPartner(Partner partner){
    return _partners.add(partner);
  }

  public List<Partner> getPartners() {
    List<Partner> partners = new ArrayList<>(_partners);
    Collections.sort(partners);
    return partners;
  }

  public List<Batch> getAvailableBatches() {
    List<Batch> batchList = new ArrayList<>();
    for(Product product : _products) {
      for(Batch batch : product.getBatches()) {
        batchList.add(batch);
      }
    }
    Collections.sort(batchList);
    return batchList;
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {
    try (
			BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    ) {
			String line = reader.readLine();
			while (line != null) {
        String[] objectString = line.split("\\|");
        if (objectString[0].equals("PARTNER")) {
          addPartner(new Partner(objectString));
        } 
        else if (objectString[0].equals("BATCH_S")) {
          Product product = null;
          Partner partner = null;

          try {
            product = getProduct(objectString[1]);
          } catch (UnknownObjectKeyException e) {
            product = new SimpleProduct(objectString[1]);
            _products.add(product);
          }

          try {
            partner = getPartner(objectString[2]);
          } catch (UnknownObjectKeyException e) {
            // DEU MERDA
          }

          Batch batch = new Batch(partner, product, Integer.valueOf(objectString[4]), Integer.valueOf(objectString[3]));

          product.addBatch(batch);
          partner.addBatch(batch);
          
        }
				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
  }

}
