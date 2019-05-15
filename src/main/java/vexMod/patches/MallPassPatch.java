package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import vexMod.relics.HatredEngine;
import vexMod.relics.MallPass;
import vexMod.relics.TreasureMap;

@SpirePatch(
        clz = EventHelper.class,
        method = "roll",
        paramtypez = {
                Random.class
        }
)

public class MallPassPatch {
    public static EventHelper.RoomResult Postfix(EventHelper.RoomResult choice, Random eventRng) {
        if (AbstractDungeon.player.hasRelic(MallPass.ID)) {
            AbstractDungeon.player.getRelic(MallPass.ID).flash();
            return EventHelper.RoomResult.SHOP;
        } else if (AbstractDungeon.player.hasRelic(HatredEngine.ID) && AbstractDungeon.eventRng.randomBoolean()) {
            AbstractDungeon.player.getRelic(HatredEngine.ID).flash();
            return EventHelper.RoomResult.MONSTER;
        } else if (AbstractDungeon.player.hasRelic(TreasureMap.ID)) {
            AbstractDungeon.player.getRelic(TreasureMap.ID).flash();
            return EventHelper.RoomResult.EVENT;
        } else {
            return choice;
        }
    }
}