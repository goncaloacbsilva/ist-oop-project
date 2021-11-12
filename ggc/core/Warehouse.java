package ggc.core;

import java.io.Serializable;
import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.Product;
import ggc.core.product.comparators.OrderByLowerPriceFirst;
import ggc.core.transaction.Acquisition;
import ggc.core.transaction.SaleByCredit;
import ggc.core.transaction.Transaction;
import ggc.core.product.Batch;
import ggc.core.partner.Partner;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.exception.UnknownObjectKeyException.ObjectType;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Warehouse products */
  private Set<Product> _products;

  /** Warehouse associated partners */
  private Set<Partner> _partners;
  

  private int _nextTransactionId;

  private double _acquisitionsBalance;

  private double _salesAccountingBalance;

  private double _salesBalance;

  private Set<Transaction> _transactions;

  /**
   * Creates a new Warehouse
   */
  public Warehouse() {
    _products = new HashSet<>();
    _partners = new HashSet<>();
    _transactions = new HashSet<>();
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
    throw new UnknownObjectKeyException(id, ObjectType.PRODUCT);
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
    throw new UnknownObjectKeyException(id, ObjectType.PARTNER);
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

  public void registerAcquisition(String partnerId, String productId, double price, int amount) throws NotEnoughResourcesException, UnknownObjectKeyException {
    // Get Transaction Entities info
    Partner supplier = getPartner(partnerId);
    Product product = getProduct(productId);

    // Process operation
    double totalPrice = supplier.sellBatch(productId, amount);
    _acquisitionsBalance += totalPrice;
    
    // Update inventory
    product.addBatch(new Batch(supplier, product, amount, price));

    // Register transaction
    Transaction acquisitionTransaction = new Acquisition(_nextTransactionId++, product, amount, supplier, Date.now().getValue(), totalPrice);

    _transactions.add(acquisitionTransaction);
    supplier.addTransaction(acquisitionTransaction);
  }

  public void registerSaleByCredit(String partnerId, String productId, int paymentDeadline, int amount) throws NotEnoughResourcesException, UnknownObjectKeyException {
    // Get Transaction Entities info
    Partner partner = getPartner(partnerId);
    Product product = getProduct(productId);
    
    // Check stocks
    if (!product.hasAvailableStock(amount)) {
      throw new NotEnoughResourcesException(productId, amount, product.getTotalStock());
    }
    
    // Update inventory
    double basePrice = product.sellAmount(amount);

    Transaction saleTransaction = new SaleByCredit(_nextTransactionId++, product, amount, partner, basePrice, paymentDeadline);
    _transactions.add(saleTransaction);
    partner.addTransaction(saleTransaction);
  }

  public String viewTransaction(int transactionId) throws UnknownObjectKeyException {
    for (Transaction transaction : _transactions) {
      if (transaction.getId() == transactionId) {
        return transaction.toString();
      }
    }
    throw new UnknownObjectKeyException(Integer.toString(transactionId), ObjectType.TRANSACTION);
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

  public boolean toggleNotificationStatus(String partnerId, String productId) throws UnknownPartnerKeyException, UnknownProductKeyException {
    //return getPartner(partnerId).toggleNotificationStatus(productId);
  }

}
