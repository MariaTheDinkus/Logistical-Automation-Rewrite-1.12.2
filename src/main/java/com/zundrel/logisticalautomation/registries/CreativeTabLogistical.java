package com.zundrel.logisticalautomation.registries;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class CreativeTabLogistical extends CreativeTabs {
    public static CreativeTabLogistical INSTANCE = new CreativeTabLogistical();
    NonNullList<ItemStack> list;

    private CreativeTabLogistical() {
        super(LogisticalAutomation.MOD_ID);
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.conveyor_normal);
    }

    @Override
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        this.addBlock(ModBlocks.conveyor_normal);
        this.addBlock(ModBlocks.conveyor_fast);
        this.addBlock(ModBlocks.conveyor_express);

        this.addBlock(ModBlocks.conveyor_ramp_normal);
        this.addBlock(ModBlocks.conveyor_ramp_fast);
        this.addBlock(ModBlocks.conveyor_ramp_express);
    }

    private void addItem(Item item) {
        item.getSubItems(this, list);
    }

    private void addBlock(Block block) {
        block.getSubBlocks(this, list);
    }
}
