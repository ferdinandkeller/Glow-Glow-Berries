package dev.ferdinandkeller.glowglowberries.mixin;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(net.minecraft.item.Items.class)
public class Items {
	@Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;")
	private static void register(String id, Function<Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        if (!id.equals("glow_berries")) return;

        // create a custom Glow Berries consumable component
        ConsumableComponent GLOW_BERRIES = ConsumableComponents.food()
            .consumeEffect(
                new ApplyEffectsConsumeEffect(
                    new StatusEffectInstance(StatusEffects.GLOWING, 40)
                )
            )
            .build();

        // update the settings
        settings.food(FoodComponents.GLOW_BERRIES, GLOW_BERRIES);
	}
}
