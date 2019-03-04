package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MindDevourer extends CustomRelic {


    public static final String ID = VexMod.makeID("MindDevourer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public MindDevourer() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void onVictory()
    {
        ArrayList<AbstractCard> removablecards = new ArrayList();
        Iterator plop = AbstractDungeon.player.masterDeck.group.iterator();
        while(plop.hasNext()) {
            AbstractCard c = (AbstractCard)plop.next();
            removablecards.add(c);
        }
        Collections.shuffle(removablecards, new Random(AbstractDungeon.miscRng.randomLong()));
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)removablecards.get(0), (float) Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractCard card = (AbstractCard)removablecards.get(0);
        AbstractDungeon.player.masterDeck.removeCard(card);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}