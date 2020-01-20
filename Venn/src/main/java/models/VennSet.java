package models;
//hi
import java.util.HashSet;
import java.util.List;

public class VennSet extends HashSet<String> {

    public VennSet() {
        super();
    }

    public VennSet(List<String> list) {
        super(list);
    }
}