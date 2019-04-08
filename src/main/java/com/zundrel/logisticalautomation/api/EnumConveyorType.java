package com.zundrel.logisticalautomation.api;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorType implements IStringSerializable {
    NORMAL("normal"),
    RAMP("ramp");

    private final String name;

    EnumConveyorType(String name)
    {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
