package com.github.kaspiandev.nbtgui.property.value;

import com.github.kaspiandev.nbtgui.util.StringUtil;

import java.util.List;

public class PrettyStringListValue extends PrettyValue<String, List<String>> {

    public PrettyStringListValue(String input) {
        super(input);
    }

    @Override
    public List<String> getValue() {
        List<String> value = StringUtil.wrapString(input, 20);
        if (value.size() == 1) {
            value.set(0, "&7Value: " + value.get(0));
        } else {
            value.add(0, "&7Value:"); // Render lore starting at it's own line
        }
        return value;
    }

    @Override
    public void appendTo(List<String> lore) {
        lore.addAll(getValue());
    }

}
