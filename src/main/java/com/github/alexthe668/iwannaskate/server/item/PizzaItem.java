package com.github.alexthe668.iwannaskate.server.item;

import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class PizzaItem extends IWSBlockItem{

    public PizzaItem(Supplier<Block> blockSupplier, Properties props) {
        super(blockSupplier, props);
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
