package vexMod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;
import vexMod.relics.NewClearReactor;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "render",
        paramtypez = {
                SpriteBatch.class
        }
)
public class NewClearReactorPatch2 {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"energyMsg"}
    )
    public static void Insert(EnergyPanel __instance, SpriteBatch sb, @ByRef String[] energyMsg) {
        if (AbstractDungeon.player.hasRelic(NewClearReactor.ID)) {
            energyMsg[0] = String.valueOf(EnergyPanel.totalCount);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getEnergyNumFont");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}