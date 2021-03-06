package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import javassist.CtBehavior;
import vexMod.relics.MidasArmor;

import java.util.ArrayList;

@SpirePatch(
        clz = Hexaghost.class,
        method = "takeTurn"
)
public class HexaghostPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"d"}
    )
    public static void Insert(Hexaghost __instance, @ByRef int[] d) {
        if (AbstractDungeon.player.hasRelic(MidasArmor.ID)) {
            d[0] = 5;
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            ArrayList<Matcher> prevMatches = new ArrayList<>();
            prevMatches.add(
                    new Matcher.FieldAccessMatcher(AbstractPlayer.class,
                            "currentHealth"));

            Matcher finalMatcher = new Matcher.TypeCastMatcher(DamageInfo.class.getName());

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}