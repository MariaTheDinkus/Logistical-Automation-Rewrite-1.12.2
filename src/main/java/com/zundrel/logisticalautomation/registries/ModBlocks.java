package com.zundrel.logisticalautomation.registries;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyor;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyorRamp;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(LogisticalAutomation.MOD_ID)
public class ModBlocks {
    public static final BlockConveyor conveyor_normal = null;
    public static final BlockConveyor conveyor_fast = null;
    public static final BlockConveyor conveyor_express = null;

    public static final BlockConveyorRamp conveyor_ramp_normal = null;
    public static final BlockConveyorRamp conveyor_ramp_fast = null;
    public static final BlockConveyorRamp conveyor_ramp_express = null;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        conveyor_normal.initModel();
        conveyor_fast.initModel();
        conveyor_express.initModel();

        conveyor_ramp_normal.initModel();
        conveyor_ramp_fast.initModel();
        conveyor_ramp_express.initModel();
    }
}