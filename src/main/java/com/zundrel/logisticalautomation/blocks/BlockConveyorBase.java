package com.zundrel.logisticalautomation.blocks;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.api.IConveyor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockConveyorBase extends BlockBase implements IConveyor {
    public static final PropertyBool FRONT = PropertyBool.create("front");
    public static final PropertyBool RIGHT = PropertyBool.create("right");
    public static final PropertyBool BACK = PropertyBool.create("back");
    public static final PropertyBool LEFT = PropertyBool.create("left");
    public static final PropertyBool TOP = PropertyBool.create("top");

    private final EnumConveyorType conveyorType;
    private final EnumConveyorTier conveyorTier;

    public BlockConveyorBase(String unlocalizedName, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(unlocalizedName, Material.IRON);

        this.conveyorType = conveyorType;
        this.conveyorTier = conveyorTier;
    }

    @Override
    public void initModel() {
        super.initModel();
    }

    @Override
    public EnumConveyorType getConveyorType() {
        return conveyorType;
    }

    @Override
    public EnumConveyorTier getConveyorTier() {
        return conveyorTier;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).add(FRONT).add(RIGHT).add(BACK).add(LEFT).add(TOP).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.isSneaking() ? placer.getHorizontalFacing().getOpposite() : placer.getHorizontalFacing());
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityItem) {
            entityIn.stepHeight = 0.5F;
        }
    }
}
