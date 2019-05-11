package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.random.Random;
import vexMod.relics.CursedCompass;

@SpirePatch(
        clz = MapGenerator.class,
        method = "generateDungeon"
)
public class CursedCompassPatch {
    @SpirePrefixPatch
    public static void Prefix(int height, int width, @ByRef int[] pathDensity, Random rng) {
        if (AbstractDungeon.player.hasRelic(CursedCompass.ID)) {
            pathDensity[0] = 1;
        }
    }
}