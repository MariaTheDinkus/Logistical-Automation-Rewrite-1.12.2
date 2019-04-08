package com.zundrel.logisticalautomation.utilities;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class MovementUtilities {
	public static void pushEntity(Entity entity, BlockPos pos, float speed, EnumFacing facing) {
		pushEntity(entity, pos, speed, facing, true);
	}

	public static void pushEntity(Entity entity, BlockPos pos, float speed, EnumFacing facing, boolean shouldCenter) {
		entity.motionX += speed * facing.getFrontOffsetX();
		entity.motionZ += speed * facing.getFrontOffsetZ();

		if (speed * facing.getFrontOffsetX() > 0) {
			if (entity.motionX > speed) {
				entity.motionX = speed;
			}
		} else if (speed * facing.getFrontOffsetX() < 0) {
			if (entity.motionX < -speed) {
				entity.motionX = -speed;
			}
		}

		if (speed * facing.getFrontOffsetZ() > 0) {
			if (entity.motionZ > speed) {
				entity.motionZ = speed;
			}
		} else if (speed * facing.getFrontOffsetZ() < 0) {
			if (entity.motionZ < -speed) {
				entity.motionZ = -speed;
			}
		}

		if (shouldCenter) {
			centerEntity(entity, pos, speed, facing);
		}
	}

	private static void centerEntity(Entity entity, BlockPos pos, float speed, EnumFacing facing) {
		if (speed * facing.getFrontOffsetX() > 0 || speed * facing.getFrontOffsetX() < 0) {
		    centerZ(entity, pos);
		}

		if (speed * facing.getFrontOffsetZ() > 0 || speed * facing.getFrontOffsetZ() < 0) {
			centerX(entity, pos);
		}
	}

	private static void centerZ(Entity entity, BlockPos pos) {
        if (entity.posZ > pos.getZ() + .55) {
            entity.motionZ += -0.1F;
        } else if (entity.posZ < pos.getZ() + .45) {
            entity.motionZ += 0.1F;
        } else {
            entity.motionZ = 0;
        }
    }

    private static void centerX(Entity entity, BlockPos pos) {
        if (entity.posX > pos.getX() + .55) {
            entity.motionX += -0.1F;
        } else if (entity.posX < pos.getX() + .45) {
            entity.motionX += 0.1F;
        } else {
            entity.motionX = 0;
        }
    }
}