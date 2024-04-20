package net.wilux.util;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;

public class PolyElementHolder {
    public static ElementHolder newFromElements(VirtualElement... elements) {
        var a = new ElementHolder();
        for (var elem : elements) {
            a.addElement(elem);
        }
        return a;
    }
}
