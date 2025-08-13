package github.qbic.darkflame.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

import github.qbic.darkflame.procedures.KillPlayerProcedure;

public class ErrNullItem extends Item {
	public ErrNullItem(Item.Properties properties) {
		super(properties.stacksTo(1).food((new FoodProperties.Builder()).nutrition(-20).saturationModifier(-20f).alwaysEdible().build()));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		KillPlayerProcedure.execute(entity);
		return retval;
	}
}