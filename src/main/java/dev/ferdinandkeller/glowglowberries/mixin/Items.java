package dev.ferdinandkeller.glowglowberries.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(net.minecraft.item.Items.class)
public class Items {
    @ModifyArgs(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/AliasedBlockItem;<init>(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item$Settings;)V"
        )
    )
    private static void register(Args args) {
        // find which block we are applying the mixin to
        Block block = (Block) args.get(0);

        if (!block.equals(Blocks.CAVE_VINES)) return;

        Item.Settings settings = (Item.Settings) args.get(1);

        // create a custom Glow Berries food component
        FoodComponent GLOW_BERRIES = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40), 1.0F)
            .build();

        // update the settings
        settings.food(GLOW_BERRIES);
    }
}

