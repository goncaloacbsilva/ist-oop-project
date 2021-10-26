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
    return Date.now();
  }

  public void advanceDate(int value) {
    _warehouse.advanceDate(value);
  }

  public String getFilename() {
    return _filename;
  }

  public Set<Product> getProducts() {
    return _warehouse.getProducts();
  }

  public List<Batch> getAvailableBatches() {
    return _warehouse.getAvailableBatches();
  }

  public Partner getPartner(String id) throws UnknownObjectKeyException {
    for (Partner partner : _warehouse.getPartners()) {
      if (partner.getId().equals(id)) {
        return partner;
      }
    }
    throw new UnknownObjectKeyException();
  }

  public Product getProduct(String id) throws UnknownObjectKeyException {
    for (Product product : _warehouse.getProducts()) {
      if (product.getId().equals(id)) {
        return product;
      }
    }
    throw new UnknownObjectKeyException();
  }



  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename.equals("")) {
      throw new MissingFileAssociationException();
    }
    try (
      FileOutputStream outFile = new FileOutputStream(_filename);
      ObjectOutputStream outStream = new ObjectOutputStream(outFile)
    ) {
      outStream.writeObject(_warehouse);
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      throw e;
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
    } catch (IOException e) {
      throw new UnavailableFileException(filename);
    } catch (ClassNotFoundException e) {
      throw new ClassNotFoundException();
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
