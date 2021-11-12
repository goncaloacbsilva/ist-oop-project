package ggc.core.lookups;

import java.util.Collection;

import ggc.core.Warehouse;
import ggc.core.exception.UnknownObjectKeyException;

public interface LookupStrategy {
    public Collection<Object> execute(Warehouse store) throws UnknownObjectKeyException;
}
