package com.github.kaspiandev.nbtgui.property.value;

import com.github.kaspiandev.nbtgui.util.StringUtil;

import java.util.List;

public class PrettyLongValue extends PrettyValue<Long, List<String>> {

    public PrettyLongValue(long input) {
        super(input);
    }

    @Override
    public List<String> getValue() {
        List<String> value = StringUtil.wrapString(String.valueOf(input), 14);
        if (value.size() == 1) {
            value.set(0, "Value: " + value.get(0));
        } else {
            value.add(0, "Value:"); // Render lore starting at it's own line
        }
        return value.stream()
                    .map("&7"::concat)
                    .toList();
    }

    @Override
    public void appendTo(List<String> lore) {
        lore.addAll(getValue());
    }

}
