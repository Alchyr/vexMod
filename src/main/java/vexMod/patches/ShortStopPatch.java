package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import vexMod.relics.ShortStop;

import java.util.ArrayList;

@SpirePatch(
        clz = MapGenerator.class,
        method = "generateDungeon"
)
public class ShortStopPatch {
    @SpirePrefixPatch
    public static void Prefix(@ByRef int height[], int width, int pathDensity, Random rng) {
        if (AbstractDungeon.player.hasRelic(ShortStop.ID)) {
            height[0] /= 2;
            if (height[0] < 9) {
                height[0] = 9;
            }
        }
    }
}