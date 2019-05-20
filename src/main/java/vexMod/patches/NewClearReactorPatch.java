package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vexMod.relics.NewClearReactor;

@SpirePatch(
        clz = EnergyManager.class,
        method = "recharge"
)

public class NewClearReactorPatch {
    @SpirePrefixPatch
    public static SpireReturn Prefix(EnergyManager __instance) {
        if (AbstractDungeon.player.hasRelic(NewClearReactor.ID)) {// 31
            EnergyPanel.addEnergy(__instance.energy);// 37
            AbstractDungeon.actionManager.updateEnergyGain(__instance.energy);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}