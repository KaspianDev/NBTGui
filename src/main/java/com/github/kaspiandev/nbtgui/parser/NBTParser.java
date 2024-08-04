package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.List;

public interface NBTParser<N extends ReadableNBT> {

    List<NBTProperty<?>> parse(N nbtEntity);

}
