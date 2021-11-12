package ggc.core;

import java.io.Serializable;
import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.Product;
import ggc.core.RecipeComponent;
import ggc.core.Transaction;
import ggc.core.Transaction.TransactionType;
import ggc.core.Batch;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.NotEnoughResourcesException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.exception.UnknownObjectKeyException.ObjectType;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Warehouse products */
  private Map<String, Product> _products;

  /** Warehouse associated partners */
  private Map<String, Partner> _partners;
  

  private int _nextTransactionId;

  private double _acquisitionsBalance;

  private double _salesBalance;

  private Map<Integer, Transaction> _transactions;

  /**
   * Creates a new Warehouse
   */
  public Warehouse() {
    _products = new HashMap<>();
    _partners = new HashMap<>();
    _transactions = new HashMap<>();
  }

  /**
   * Get Warehouse products list
   * @return products list
   */
  public List<Product> getProducts() {
    List<Product> products = new ArrayList<>(_products.values());
    Collections.sort(products);
    return products;
  }

  /**
   * Get Warehouse partners list
   * @return partners list
   */
  public List<Partner> getPartners() {
    List<Partner> partners = new ArrayList<>(_partners.values());
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
    if (_products.containsKey(id.toLowerCase())) {
      return _products.get(id.toLowerCase());
    } else {
      throw new UnknownObjectKeyException(id, ObjectType.PRODUCT);
    }
  }

  /**
   * Get a specific partner by its id
   * @param id partner id
   * @return Partner
   * @throws UnknownObjectKeyException
   */
  public Partner getPartner(String id) throws UnknownObjectKeyException {
    if (_partners.containsKey(id.toLowerCase())) {
      return _partners.get(id.toLowerCase());
    } else {
      throw new UnknownObjectKeyException(id, ObjectType.PARTNER);
    }
  }

  /**
   * Register a new Partner on the Warehouse
   * @param partner Partner object
   * @return if the operation was successful or not
   */
  public boolean addPartner(Partner partner){
    if (_partners.containsKey(partner.getId().toLowerCase())) {
      return false;
    } else {
      _partners.put(partner.getId().toLowerCase(), partner);
      return true;
    }
  }

  /**
   * Register a new Product on the Warehouse
   * @param product Product object
   * @return if the operation was successful or not
   */
  public boolean addProduct(Product product) {
    for (Partner partner : _partners.values()) {
      product.subscribe(partner);
    }

    if (_products.containsKey(product.getId().toLowerCase())) {
      return false;
    } else {
      _products.put(product.getId().toLowerCase(), product);
      return true;
    }
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
  }

  /**
   * Get all available warehouse batches 
   * @return batches list
   */
  public List<Batch> getAvailableBatches() {
    List<Batch> batchList = new ArrayList<>();
    for(Product product : _products.values()) {
      for(Batch batch : product.getBatches()) {
        batchList.add(batch);
      }
    }
    Collections.sort(batchList);
    return batchList;
  }

  public List<Batch> getBatchesByPartner(String partnerId) throws UnknownObjectKeyException {
    List<Batch> batchList = new ArrayList<>();

    getPartner(partnerId);

    for(Product product : _products.values()) {
      for(Batch batch : product.getBatchesByPartner(partnerId)) {
        batchList.add(batch);
      }
    }
    Collections.sort(batchList);
    return batchList;
  }

  public void checkDerivativeStock(Product product, int amount) throws NotEnoughResourcesException {
    for (RecipeComponent component : product.getRecipe()) {
      Product componentProduct = component.getProduct();
      int amountNeeded = component.getAmount() * amount;
      if (!componentProduct.hasAvailableStock(amountNeeded)) {
        throw new NotEnoughResourcesException(componentProduct.getId(), amountNeeded, componentProduct.getTotalStock());
      }
    }
  }

  public double createProduct(Product product) throws NotEnoughResourcesException {
    double balance = 0.0;
    for (RecipeComponent component : product.getRecipe()) {
      balance += component.getProduct().sellAmount(component.getAmount());
    }
    return balance;
  }

  public void registerPartnerPayment(int transactionId) throws UnknownObjectKeyException {
    Transaction transaction = getTransaction(transactionId);
    _salesBalance += transaction.pay();
  }

  public List<Transaction> getTransactionsByPartner(String partnerId, TransactionType type) throws UnknownObjectKeyException {
    List<Transaction> transactions = new ArrayList<>();
    for (Transaction transaction : getPartner(partnerId).getTransactions()) {
      if (transaction.getType() == type) {
        transactions.add(transaction);
      }
    }
    return transactions;
  }

  public List<Transaction> getTransactionsByType(TransactionType type) {
    List<Transaction> transactions = new ArrayList<>();
    for (Transaction transaction : _transactions.values()) {
      if (transaction.getType() == type) {
        transactions.add(transaction);
      }
    }
    return transactions;
  }

  public double getTransactionsHighestPrice(String productId) {
    double price = 0.0;
    for (Transaction transaction : _transactions.values()) {
      Product product = transaction.getProduct();
      if (product.getId().equals(productId)) {
        double transactionUnitPrice = transaction.getBasePrice() / transaction.getQuantity();
        if (transactionUnitPrice > price) {
          price = transactionUnitPrice;
        }
      }
    }
    return price;
  }

  public Transaction getTransaction(int transactionId) throws UnknownObjectKeyException {
    if (_transactions.containsKey(transactionId)) {
      return _transactions.get(transactionId);
    } else {
      throw new UnknownObjectKeyException(Integer.toString(transactionId), ObjectType.TRANSACTION);
    }
  }

  public int generateTransactionId() {
    return _nextTransactionId++;
  }

  public void addTransaction(Transaction transaction) {
    _transactions.put(transaction.getId(), transaction);
  }

  public double getAccountingBalance() {
    double balance = 0.0;
    for (Transaction transaction : _transactions.values()) {
      balance += transaction.calculatePriceToPay();
    }
    return balance - _acquisitionsBalance;
  }

  public double getAvailableBalance() {
    return _salesBalance - _acquisitionsBalance;
  }

  public void increaseAcquisitionsBalance(double price) {
    _acquisitionsBalance += price;
  }

  public void increaseSalesBalance(double price) {
    _salesBalance += price;
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

  public void toggleNotificationStatus(String partnerId, String productId) throws UnknownObjectKeyException {
    Partner partner = getPartner(partnerId);
    Product product = getProduct(productId);
    if (product.isSubscribed(partner)) {
      product.unsubscribe(partner);
    } else {
      product.subscribe(partner);
    }
  }

}
