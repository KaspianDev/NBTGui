package com.github.kaspiandev.nbtgui.property.value;

import java.util.List;

public abstract class PrettyValue<I, O> {

    protected final I input;

    public PrettyValue(I input) {
        this.input = input;
    }

    protected abstract O getValue();

    public abstract void appendTo(List<String> lore);

}
