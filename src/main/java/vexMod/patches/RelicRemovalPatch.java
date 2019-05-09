package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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
import vexMod.relics.*;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="initializeRelicList"
)
public class RelicRemovalPatch
{
    public static void Postfix(AbstractDungeon dungeon_instance)
    {
        if (!VexMod.enablePlaceholder) {
            dungeon_instance.shopRelicPool.remove(NewsTicker.ID);
        }
        if (!VexMod.enableMemes)
        {
            dungeon_instance.shopRelicPool.remove(NotEnergy.ID);
            dungeon_instance.shopRelicPool.remove(TheWave.ID);
            dungeon_instance.shopRelicPool.remove(NewsTicker.ID);
            dungeon_instance.shopRelicPool.remove(TimeMachine.ID);
            dungeon_instance.shopRelicPool.remove(StoryBook.ID);
        }
        if (language == Settings.GameLanguage.ZHS)
        {
            dungeon_instance.shopRelicPool.remove(StoryBook.ID);
        }
    }
}