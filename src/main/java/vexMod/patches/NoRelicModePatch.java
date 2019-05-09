package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.modifiers.NoRelicMode;

public class NoRelicModePatch {
    @SpirePatch(
            clz = AbstractRelic.class,
            method = "obtain"
    )
    public static class norm_obtain {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractRelic __instance) {
            if (CardCrawlGame.trial!=null)
            {
                if (AbstractDungeon.player != null && CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID) && AbstractDungeon.floorNum > 1) {
                    __instance.isDone = true;
                    __instance.isObtained = false;
                    __instance.discarded = true;
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractRelic.class,
            method = "instantObtain",
            paramtypez = {}
    )
    public static class norm_instantObtain {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractRelic __instance) {
            if (CardCrawlGame.trial!=null)
            {
                if (AbstractDungeon.player != null && CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID) && AbstractDungeon.floorNum > 1) {
                    __instance.isDone = true;
                    __instance.isObtained = false;
                    __instance.discarded = true;
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractRelic.class,
            method = "instantObtain",
            paramtypez = {
                    AbstractPlayer.class,
                    int.class,
                    boolean.class
            }
    )
    public static class param_instantObtain {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractRelic __instance, AbstractPlayer p, int slot, boolean callOnEquip) {
            if (CardCrawlGame.trial!=null)
            {
                if (AbstractDungeon.player != null && CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID) && AbstractDungeon.floorNum > 1) {
                    __instance.isDone = true;
                    __instance.isObtained = false;
                    __instance.discarded = true;
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }
}