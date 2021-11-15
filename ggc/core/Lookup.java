package ggc.core;

import java.util.Collection;

import ggc.core.exception.UnknownObjectKeyException;

class Lookup {
    private LookupStrategy _strategy;
    private Warehouse _store;

    Lookup(Warehouse store) {
        _store = store;
    }
    
    void setStrategy(LookupStrategy strategy) {
        _strategy = strategy;
    }

    Collection<Object> execute() throws UnknownObjectKeyException {
        return _strategy.execute(_store);
    }
}
