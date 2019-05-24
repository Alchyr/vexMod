package vexMod.patches;

import com.evacipated.cardcrawl.mod.bard.cards.Doot;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.relics.Dootinator;

public class DoTheBardStuff {
    public static void Wahoo() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doot(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new Dootinator());
    }

    public static boolean AREYOUTHEDAMNBARD() {
        return (AbstractDungeon.player instanceof Bard);
    }
}
