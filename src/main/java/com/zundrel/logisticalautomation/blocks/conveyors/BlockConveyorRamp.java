package com.zundrel.logisticalautomation.blocks.conveyors;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.BlockConveyorBase;
import com.zundrel.logisticalautomation.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.utilities.RotationUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockConveyorRamp extends BlockConveyorBase {
    public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockConveyorRamp(String unlocalizedName, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(unlocalizedName, conveyorType, conveyorTier);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).add(DOWN).add(FRONT).add(RIGHT).add(BACK).add(BOTTOM).add(LEFT).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int down = state.getValue(DOWN) ? 1 : 0;

        return Integer.parseInt(down + "" + state.getValue(FACING).getIndex());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int metaLength = String.valueOf(meta).length();

        boolean down = metaLength == 2;

        int metaNew = metaLength == 1 ? Integer.parseInt(String.valueOf(("" + meta).charAt(0))) : Integer.parseInt(String.valueOf(("" + meta).charAt(1)));

        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(DOWN, down);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState newState = state;
        EnumFacing facing = state.getValue(FACING);

        BlockPos bottomPos = pos.down();

        if (!worldIn.getBlockState(bottomPos).isFullCube()) {
            newState.withProperty(BOTTOM, true);
        } else {
            newState.withProperty(BOTTOM, false);
        }

        return newState;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(DOWN, placer.isSneaking());
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for (int i = 0; i < 16; i++) {
            AxisAlignedBB bounds = new AxisAlignedBB(0, (1F / 16F) * i, (1F / 16F) * i, 1, ((1F / 16F) * i) + (1F / 16F), ((1F / 16F) * i) + (1F / 16F));
            bounds = RotationUtilities.getRotatedAABB(bounds, state.getValue(FACING).getOpposite());
            addCollisionBox(bounds, pos, collidingBoxes, entityBox);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        if (entityIn instanceof EntityLivingBase && !entityIn.onGround)
            return;

        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;

            if (!player.getHeldItemMainhand().isEmpty() && Block.getBlockFromItem(player.getHeldItemMainhand().getItem()) instanceof BlockConveyorBase) {
                return;
            } else if (!player.getHeldItemOffhand().isEmpty() && Block.getBlockFromItem(player.getHeldItemOffhand().getItem()) instanceof BlockConveyorBase) {
                return;
            }
        }

        MovementUtilities.pushEntity(entityIn, pos, getConveyorTier().getSpeed(), state.getValue(DOWN) ? state.getValue(FACING).getOpposite() : state.getValue(FACING));
    }
}