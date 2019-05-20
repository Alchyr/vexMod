package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.relics.MiniSolarSystem;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "spawnRelicAndObtain",
        paramtypez = {
                float.class,
                float.class,
                AbstractRelic.class
        }
)
public class RelicOrbitPatch {
    public static SpireReturn Prefix(AbstractRoom __instance, float x, float y, AbstractRelic relic) {
        if (AbstractDungeon.player.hasRelic(MiniSolarSystem.ID)) {
            relic.instantObtain();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}