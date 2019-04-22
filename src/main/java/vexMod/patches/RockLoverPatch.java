package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.relics.RandomRelic;
import vexMod.relics.RockLover;

public class RockLoverPatch
{
    @SpirePatch(
            clz=AbstractDungeon.class,
            method="returnEndRandomRelicKey"
    )
    public static class ReturnEndRandomRelicKey
    {
        public static String Postfix(String __result, AbstractRelic.RelicTier tier)
        {
            if (AbstractDungeon.player.hasRelic(RockLover.ID)) {
                return RandomRelic.ID;
            } else {
                return __result;
            }
        }
    }
    @SpirePatch(
            clz=AbstractDungeon.class,
            method="returnRandomRelicKey"
    )
    public static class ReturnRandomRelicKey
    {
        public static String Postfix(String __result, AbstractRelic.RelicTier tier)
        {
            if (AbstractDungeon.player.hasRelic(RockLover.ID)) {
                return RandomRelic.ID;
            } else {
                return __result;
            }
        }
    }
}