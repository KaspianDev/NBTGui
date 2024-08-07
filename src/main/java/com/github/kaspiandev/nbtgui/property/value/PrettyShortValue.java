package com.github.kaspiandev.nbtgui.property.value;

import java.util.List;

public class PrettyShortValue extends PrettyValue<Short, String> {

    public PrettyShortValue(short input) {
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
