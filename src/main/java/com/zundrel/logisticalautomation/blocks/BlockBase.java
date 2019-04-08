package com.zundrel.logisticalautomation.blocks;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.registries.CreativeTabLogistical;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockBase extends BlockHorizontal {
    public BlockBase(String unlocalizedName, Material material) {
        super(material);

        setCreativeTab(CreativeTabLogistical.INSTANCE);
        setUnlocalizedName(LogisticalAutomation.MOD_ID + "." + unlocalizedName);
        setRegistryName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public void addCollisionBox(AxisAlignedBB box, BlockPos pos, List collidingBoxes, AxisAlignedBB entityBox) {
        if (box != null && entityBox.intersects(box.offset(pos))) {
            collidingBoxes.add(box.offset(pos));
        }
    }
}
