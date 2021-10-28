package ggc.core;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import ggc.core.Warehouse;
import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.Product;
import ggc.core.product.Batch;
import ggc.core.partner.Partner;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.InvalidDateValueException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  /**
   * Get the current date object
   * @return Date
   */
  public Date getDate() {
    return Date.now();
  }

  /**
   * Advance Warehouse date with the supplied value
   * @param value
   * @throws InvalidDateValueException
   * @see Warehouse#advanceDate(int value)
   */
  public void advanceDate(int value) throws InvalidDateValueException {
    Date.add(value);
  }

  /**
   * Get the name of the current program associated file
   * @return filename
   */
  public String getFilename() {
    return _filename;
  }

  /**
   * Get Warehouse products list
   * @return products list
   * @see Warehouse#getProducts()
   */
  public List<Product> getProducts() {
    return _warehouse.getProducts();
  }
  
  /**
   * Get Warehouse partners list
   * @return partners list
   * @see Warehouse#getPartners()
   */
  public List<Partner> getPartners() {
    return _warehouse.getPartners();
  }

  /**
   * Get all available warehouse batches 
   * @return batches list
   * @see Warehouse#getAvailableBatches()
   */
  public List<Batch> getAvailableBatches() {
    return _warehouse.getAvailableBatches();
  }


  /**
   * Get a specific partner by its id
   * @param id partner id
   * @return Partner
   * @throws UnknownObjectKeyException
   * @see Warehouse#getPartner(String id)
   */
  public Partner getPartner(String id) throws UnknownObjectKeyException {
    return _warehouse.getPartner(id);
  }

  /**
   * Get a specific product by its id
   * @param id product id
   * @return Product
   * @throws UnknownObjectKeyException
   * @see Warehouse#getProduct(String id)
   */
  public Product getProduct(String id) throws UnknownObjectKeyException {
    return _warehouse.getProduct(id);
  }

  /**
   * Register a new Partner on the Warehouse
   * @param partner Partner object
   * @return if the operation was successful or not
   * @see Warehouse#addPartner(Partner partner)
   */
  public boolean addPartner(String id, String name, String address){
    return _warehouse.addPartner(new Partner(id, name, address));
  }


  /**
   * Save current state on the associated file
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename.isEmpty()) {
      throw new MissingFileAssociationException();
    }
    try (
      FileOutputStream outFile = new FileOutputStream(_filename);
      ObjectOutputStream outStream = new ObjectOutputStream(outFile)
    ) {
      outStream.writeObject(_warehouse);
      outStream.writeObject(Date.now().getValue());
    }
  }

  /**
   * Associate a file and save current state
   * @param filename
   * @throws IOException
   * @throws FileNotFoundException
   * @see WarehouseManager#save()
   */
  public void saveAs(String filename) throws FileNotFoundException, IOException {
    // Avoid MissingFileAssociationException when the supplied filename is empty
    if (filename.isEmpty())
      throw new FileNotFoundException();
    
    _filename = filename;
    try {
      save();
    } catch (MissingFileAssociationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load state from file
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    try (
      FileInputStream inFile = new FileInputStream(filename);
      ObjectInputStream inStream = new ObjectInputStream(inFile)
    ) {
      _warehouse = (Warehouse) inStream.readObject();
      try {
        Date.add((int) inStream.readObject());
      } catch (InvalidDateValueException ignored) {
        // This is not possible to happen
      }
      _filename = filename;
    } catch (IOException ignored) {
      throw new UnavailableFileException(filename);
    } catch (ClassNotFoundException ignored) {
      throw new ClassNotFoundException();
    }
  }

  /**
   * Loads entities from text file
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException, BadEntryException {
    Parser parser = new Parser(_warehouse);
    try {
      parser.parseFile(textfile);
    } catch (IOException | BadEntryException | UnknownObjectKeyException e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
