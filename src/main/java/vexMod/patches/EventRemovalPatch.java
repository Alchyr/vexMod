package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.random.Random;
import vexMod.VexMod;
import vexMod.events.DeadDefectEvent;
import vexMod.events.DeadIroncladEvent;
import vexMod.events.DeadSilentEvent;
import vexMod.events.TweeterEvent;
import vexMod.relics.CursedCompass;
import vexMod.relics.MidasArmor;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="initializeCardPools"
)
public class EventRemovalPatch
{
    public static void Prefix(AbstractDungeon dungeon_instance)
    {
        if (AbstractDungeon.player instanceof Ironclad)
        {
            dungeon_instance.eventList.remove(DeadIroncladEvent.ID);
        }
        if (AbstractDungeon.player instanceof TheSilent)
        {
            dungeon_instance.eventList.remove(DeadSilentEvent.ID);
        }
        if (AbstractDungeon.player instanceof Defect)
        {
            dungeon_instance.eventList.remove(DeadDefectEvent.ID);
        }
    }
}