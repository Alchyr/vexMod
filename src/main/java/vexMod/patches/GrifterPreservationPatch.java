package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import javassist.CtBehavior;
import vexMod.vfx.BouncingRelic;
import vexMod.vfx.JuggleRelic;
import vexMod.vfx.OrbitalRelics;
import vexMod.vfx.RelicWave;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "nextRoomTransition",
        paramtypez = {
                SaveFile.class
        }
)
public class GrifterPreservationPatch {
    private static ArrayList<AbstractGameEffect> carryOver = new ArrayList<>();

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractDungeon __instance, SaveFile saveFile) {
        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof OrbitalRelics || e instanceof BouncingRelic || e instanceof RelicWave || e instanceof JuggleRelic) {
                carryOver.add(e);
            }
        }
    }

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, SaveFile saveFile) {
        AbstractDungeon.effectList.addAll(carryOver);
        carryOver.clear();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "resetControllerValues");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}