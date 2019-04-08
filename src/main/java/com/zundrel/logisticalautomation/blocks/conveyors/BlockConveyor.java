package com.zundrel.logisticalautomation.blocks.conveyors;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.BlockConveyorBase;
import com.zundrel.logisticalautomation.utilities.MovementUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConveyor extends BlockConveyorBase {
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockConveyor(String unlocalizedName, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(unlocalizedName, conveyorType, conveyorTier);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState newState = state;
        EnumFacing facing = state.getValue(FACING);

        BlockPos rightPos = pos.offset(facing.rotateY());
        BlockPos backPos = pos.offset(facing.getOpposite());
        BlockPos leftPos = pos.offset(facing.rotateYCCW());
        BlockPos topPos = pos.up();

        if (worldIn.getBlockState(rightPos).getBlock() instanceof BlockConveyor && worldIn.getBlockState(rightPos).getValue(FACING) == facing.rotateYCCW()) {
            newState = newState.withProperty(RIGHT, true);
        } else if (worldIn.getBlockState(rightPos).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(rightPos).getValue(DOWN) && worldIn.getBlockState(rightPos).getValue(FACING) == facing.rotateY()) {
            newState = newState.withProperty(RIGHT, true);
        } else if (worldIn.getBlockState(rightPos.down()).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(rightPos.down()).getValue(FACING) == facing.rotateYCCW()) {
            newState = newState.withProperty(RIGHT, true);
        } else {
            newState = newState.withProperty(RIGHT, false);
        }

        if (worldIn.getBlockState(leftPos).getBlock() instanceof BlockConveyor && worldIn.getBlockState(leftPos).getValue(FACING) == facing.rotateY()) {
            newState = newState.withProperty(LEFT, true);
        } else if (worldIn.getBlockState(leftPos).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(leftPos).getValue(DOWN) && worldIn.getBlockState(leftPos).getValue(FACING) == facing.rotateYCCW()) {
            newState = newState.withProperty(LEFT, true);
        } else if (worldIn.getBlockState(leftPos.down()).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(leftPos.down()).getValue(FACING) == facing.rotateY()) {
            newState = newState.withProperty(LEFT, true);
        } else {
            newState = newState.withProperty(LEFT, false);
        }

        if (worldIn.getBlockState(backPos).getBlock() instanceof BlockConveyor && worldIn.getBlockState(backPos).getValue(FACING) != facing.getOpposite()) {
            newState = newState.withProperty(BACK, true);
        } else if (worldIn.getBlockState(backPos).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(backPos).getValue(DOWN) && worldIn.getBlockState(backPos).getValue(FACING) == facing.getOpposite()) {
            newState = newState.withProperty(BACK, true);
        } else if (worldIn.getBlockState(backPos.down()).getBlock() instanceof BlockConveyorRamp && worldIn.getBlockState(backPos.down()).getValue(FACING) == facing) {
            newState = newState.withProperty(BACK, true);
        } else {
            newState = newState.withProperty(BACK, false);
        }

        if (worldIn.getBlockState(topPos).getBlock() instanceof BlockConveyor) {
            newState = newState.withProperty(TOP, true);
        } else {
            newState = newState.withProperty(TOP, false);
        }

        return newState;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1);
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

        MovementUtilities.pushEntity(entityIn, pos, getConveyorTier().getSpeed(), state.getValue(FACING));
    }
}