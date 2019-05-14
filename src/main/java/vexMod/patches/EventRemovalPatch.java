package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.events.DeadDefectEvent;
import vexMod.events.DeadIroncladEvent;
import vexMod.events.DeadSilentEvent;
import vexMod.events.TweeterEvent;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "initializeCardPools"
)
public class EventRemovalPatch {
    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof Ironclad) {
            AbstractDungeon.eventList.remove(DeadIroncladEvent.ID);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            AbstractDungeon.eventList.remove(DeadSilentEvent.ID);
        }
        if (AbstractDungeon.player instanceof Defect) {
            AbstractDungeon.eventList.remove(DeadDefectEvent.ID);
        }
        if (!VexMod.enablePlaceholder) {
            AbstractDungeon.eventList.remove(TweeterEvent.ID);
        }
    }
}