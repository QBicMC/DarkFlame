package github.qbic.darkflame.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ErrNullItem extends Item {
	public ErrNullItem(Properties properties) {
		super(properties.stacksTo(1)
				.food(new FoodProperties.Builder()
						.nutrition(-20)
						.saturationModifier(-20f)
						.alwaysEdible()
						.build()
				));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {

		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.setHealth(0);
		}

		return super.finishUsingItem(itemstack, world, entity);
	}
}