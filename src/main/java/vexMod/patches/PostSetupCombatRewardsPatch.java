package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CtBehavior;
import vexMod.relics.PostSetupCombatRewardsRelic;

@SpirePatch(
        clz = CombatRewardScreen.class,
        method = "setupItemReward"
)

public class PostSetupCombatRewardsPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void PostRewards(CombatRewardScreen __instance) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof PostSetupCombatRewardsRelic) {
                ((PostSetupCombatRewardsRelic) r).onPostSetupCombatRewards(__instance);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "dynamicButton");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
