package com.github.kaspiandev.nbtgui.property.value;

import java.util.List;

public class PrettyIntegerValue extends PrettyValue<Integer, String> {

    public PrettyIntegerValue(Integer input) {
        super(input);
    }

    @Override
    public String getValue() {
        return "&7Value: " + input;
    }

    @Override
    public void appendTo(List<String> lore) {
        lore.add(getValue());
    }

}
