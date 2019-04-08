package com.zundrel.logisticalautomation.api;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorTier implements IStringSerializable {
    NORMAL("normal", 0.125F),
    FAST("fast", 0.25F),
    EXPRESS("express", 0.375F);

    private final String name;
    private final float speed;

    EnumConveyorTier(String name, float speed)
    {
        this.name = name;
        this.speed = speed;
    }

    @Override
    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }
}
