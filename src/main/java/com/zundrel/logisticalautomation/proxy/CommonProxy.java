package com.zundrel.logisticalautomation.proxy;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyor;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyorRamp;
import com.zundrel.logisticalautomation.config.Config;
import com.zundrel.logisticalautomation.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), LogisticalAutomation.MOD_ID + ".cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockConveyor("conveyor_normal", EnumConveyorType.NORMAL, EnumConveyorTier.NORMAL));
        event.getRegistry().register(new BlockConveyor("conveyor_fast", EnumConveyorType.NORMAL, EnumConveyorTier.FAST));
        event.getRegistry().register(new BlockConveyor("conveyor_express", EnumConveyorType.NORMAL, EnumConveyorTier.EXPRESS));

        event.getRegistry().register(new BlockConveyorRamp("conveyor_ramp_normal", EnumConveyorType.RAMP, EnumConveyorTier.NORMAL));
        event.getRegistry().register(new BlockConveyorRamp("conveyor_ramp_fast", EnumConveyorType.RAMP, EnumConveyorTier.FAST));
        event.getRegistry().register(new BlockConveyorRamp("conveyor_ramp_express", EnumConveyorType.RAMP, EnumConveyorTier.EXPRESS));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_normal).setRegistryName(ModBlocks.conveyor_normal.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_fast).setRegistryName(ModBlocks.conveyor_fast.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_express).setRegistryName(ModBlocks.conveyor_express.getRegistryName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_ramp_normal).setRegistryName(ModBlocks.conveyor_ramp_normal.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_ramp_fast).setRegistryName(ModBlocks.conveyor_ramp_fast.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.conveyor_ramp_express).setRegistryName(ModBlocks.conveyor_ramp_express.getRegistryName()));
    }
}