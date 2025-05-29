package twofive.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {
    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void preventOffhandArmorSwitch(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (hand == Hand.OFF_HAND
                && stack.getItem() instanceof ArmorItem armor
                && armor.getSlotType().getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
            cir.setReturnValue(ActionResult.FAIL);
            cir.cancel();
        }
    }
}
