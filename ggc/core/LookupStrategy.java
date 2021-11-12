package ggc.core;

import java.util.Collection;

import ggc.core.exception.UnknownObjectKeyException;

public interface LookupStrategy {
    public Collection<Object> execute(Warehouse store) throws UnknownObjectKeyException;
}
