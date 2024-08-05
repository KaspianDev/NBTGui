package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;

import java.util.List;

public interface NBTParser<E> {

    List<NBTProperty<?>> parse(E entity);

}
