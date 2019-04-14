package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.EntropicBrew;

@SpirePatch(
        clz = EntropicBrew.class,
        method = "use"
)

public class EntropicBrewPatch {
    public static void Prefix(EntropicBrew __obj_instance, AbstractCreature target) {
        CooldronPatch.isActive = false;
    }

    public static void Postfix(EntropicBrew __obj_instance, AbstractCreature target) {
        CooldronPatch.isActive = true;
    }
}