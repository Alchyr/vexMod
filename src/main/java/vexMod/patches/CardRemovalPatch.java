package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.random.Random;
import vexMod.VexMod;
import vexMod.cards.*;
import vexMod.events.*;
import vexMod.potions.CameraPotion;
import vexMod.relics.CursedCompass;
import vexMod.relics.MidasArmor;
import vexMod.relics.NotEnergy;
import vexMod.relics.TheWave;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="addColorlessCards"
)
public class CardRemovalPatch
{
    public static void Postfix(AbstractDungeon dungeon_instance)
    {
        if (!VexMod.enablePlaceholder) {
            dungeon_instance.colorlessCardPool.removeCard(EmailVexCard.ID);
        }
        if (!VexMod.enableMemes)
        {
            dungeon_instance.colorlessCardPool.removeCard(EmailVexCard.ID);
            dungeon_instance.colorlessCardPool.removeCard(FullService.ID);
            dungeon_instance.colorlessCardPool.removeCard(CalendarSmash.ID);
            dungeon_instance.colorlessCardPool.removeCard(WeekCard.ID);
            dungeon_instance.colorlessCardPool.removeCard(NamedBlast.ID);
            dungeon_instance.colorlessCardPool.removeCard(WellTimedStrike.ID);
            dungeon_instance.colorlessCardPool.removeCard(VolumeVengeance.ID);
            dungeon_instance.colorlessCardPool.removeCard(ChimeraCard.ID);
        }
    }
}