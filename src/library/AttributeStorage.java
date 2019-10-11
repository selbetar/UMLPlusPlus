package library;

import java.util.*;

public class AttributeStorage {

    public Set<String> declaredClasses = new HashSet<>();
    public Map<String, String> variableMap = new HashMap<>();

    private static AttributeStorage attributeStorage;

    private AttributeStorage() {
    }

    public static AttributeStorage getInstance() {
        if (attributeStorage == null) {
            attributeStorage = new AttributeStorage();
        }
        return attributeStorage;
    }
}
