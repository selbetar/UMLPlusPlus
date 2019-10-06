package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttributeStorage {

    public Map<String, ArrayList<String>> fieldMap = new HashMap<>();
    public Map<String, ArrayList<String>> methodMap = new HashMap<>();
    private static AttributeStorage attributeStorage;

    private AttributeStorage(){};

    public static AttributeStorage getInstance() {
        if (attributeStorage == null) {
            attributeStorage = new AttributeStorage();
        }
        return attributeStorage;
    }
}
