package models;

import java.util.HashSet;
import java.util.List;

public class VennSet extends HashSet<String> {

    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;

    public VennSet() {
        super();
    }

    public VennSet(List<String> list) {
        super(list);
    }
}