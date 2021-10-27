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
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.UnknownObjectKeyException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  public Date getDate() {
    return _warehouse.getDate().now();
  }

  public void advanceDate(int value) {
    _warehouse.advanceDate(value);
  }

  public String getFilename() {
    return _filename;
  }

  public List<Product> getProducts() {
    return _warehouse.getProducts();
  }

  public List<Batch> getAvailableBatches() {
    return _warehouse.getAvailableBatches();
  }

  public List<Partner> getPartners() {
    return _warehouse.getPartners();
  }

  public Partner getPartner(String id) throws UnknownObjectKeyException {
    return _warehouse.getPartner(id);
  }

  public Product getProduct(String id) throws UnknownObjectKeyException {
    return _warehouse.getProduct(id);
  }


  public boolean addPartner(String id, String name, String address){
    return _warehouse.addPartner(new Partner(id, name, address));
  }


  /**
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
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws FileNotFoundException, IOException {
    _filename = filename;
    try {
      save();
    } catch (MissingFileAssociationException e) {
      e.printStackTrace();
    }
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    try (
      FileInputStream inFile = new FileInputStream(filename);
      ObjectInputStream inStream = new ObjectInputStream(inFile)
    ) {
      _warehouse = (Warehouse) inStream.readObject();
      _filename = filename;
    } catch (IOException ignored) {
      throw new UnavailableFileException(filename);
    } catch (ClassNotFoundException ignored) {
      throw new ClassNotFoundException();
    }
  }

  /**
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
