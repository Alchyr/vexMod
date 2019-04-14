package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import vexMod.relics.Cooldron;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "returnRandomPotion",
        paramtypez = {boolean.class}
)

public class CooldronPatch {
    public static boolean isActive = true;
    public static SpireReturn<AbstractPotion> Prefix() {
        if (isActive && AbstractDungeon.player.hasRelic(Cooldron.ID)) {
            return SpireReturn.Return(new EntropicBrew());
        }
        return SpireReturn.Continue();
    }
}