package com.github.kaspiandev.nbtgui.property.value;

import java.util.HexFormat;
import java.util.List;

public class PrettyByteValue extends PrettyValue<Byte, String> {

    public PrettyByteValue(byte input) {
        super(input);
    }

    @Override
    public String getValue() {
        return "&7Value: " + input + " (" + HexFormat.of().toHexDigits(input) + ")";
    }

    @Override
    public void appendTo(List<String> lore) {
        lore.add(getValue());
    }

}
