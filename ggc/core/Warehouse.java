package ggc.core;

import java.io.Serializable;
import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.Product;
import ggc.core.product.Batch;
import ggc.core.partner.Partner;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.InvalidDateValueException;
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

  /**
   * Creates a new Warehouse
   */
  public Warehouse() {
    _date = new Date(0);
    _products = new HashSet<>();
    _partners = new HashSet<>();
  }

  /**
   * Get date object
   * @return Date
   */
  public Date getDate() {
    return _date;
  }

  /**
   * Advance date with the supplied value
   * @param value
   * @throws InvalidDateValueException
   */
  public void advanceDate(int value) throws InvalidDateValueException {
    _date = _date.add(value);
  }

  /**
   * Get Warehouse products list
   * @return products list
   */
  public List<Product> getProducts() {
    List<Product> products = new ArrayList<>(_products);
    Collections.sort(products);
    return products;
  }

  /**
   * Get Warehouse partners list
   * @return partners list
   */
  public List<Partner> getPartners() {
    List<Partner> partners = new ArrayList<>(_partners);
    Collections.sort(partners);
    return partners;
  }

  /**
   * Get a specific product by its id
   * @param id product id
   * @return Product
   * @throws UnknownObjectKeyException
   */
  public Product getProduct(String id) throws UnknownObjectKeyException {
    for (Product product : getProducts()) {
      if (product.getId().equalsIgnoreCase(id)) {
        return product;
      }
    }
    throw new UnknownObjectKeyException(id);
  }

  /**
   * Get a specific partner by its id
   * @param id partner id
   * @return Partner
   * @throws UnknownObjectKeyException
   */
  public Partner getPartner(String id) throws UnknownObjectKeyException {
    for (Partner partner : getPartners()) {
      if (partner.getId().equalsIgnoreCase(id)) {
        return partner;
      }
    }
    throw new UnknownObjectKeyException(id);
  }

  /**
   * Register a new Partner on the Warehouse
   * @param partner Partner object
   * @return if the operation was successful or not
   */
  public boolean addPartner(Partner partner){
    return _partners.add(partner);
  }

  /**
   * Register a new Product on the Warehouse
   * @param product Product object
   * @return if the operation was successful or not
   */
  public boolean addProduct(Product product) {
    return _products.add(product);
  }

  /**
   * Create a new Batch on Warehouse
   * @param idPartner
   * @param idProduct
   * @param stock
   * @param price
   * @throws UnknownObjectKeyException
   */
  public void addBatch(String idPartner, String idProduct, int stock, double price) throws UnknownObjectKeyException {
    Product product = getProduct(idProduct);
    Partner partner = getPartner(idPartner);
    Batch batch = new Batch(partner, product, stock, price);
    product.addBatch(batch);
    partner.addBatch(batch);  
  }

  /**
   * Get all available warehouse batches 
   * @return batches list
   */
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
  void importFile(String txtfile) throws IOException, BadEntryException, UnknownObjectKeyException {
    Parser parser = new Parser(this);
    parser.parseFile(txtfile);
  }

}
