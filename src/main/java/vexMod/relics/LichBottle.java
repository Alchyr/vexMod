package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class LichBottle extends CustomRelic implements BetterOnLoseHpRelic {


    public static final String ID = VexMod.makeID("LichBottle");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LichPhylactery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LichPhylactery.png"));


    public LichBottle() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
        this.counter = 0;
    }

    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.player.masterDeck.size() > 0) {
            for (int i = 0; i < damageAmount; i++) {
                counter += 1;
                if (this.counter == 8) {
                    this.counter = 0;
                    ArrayList<AbstractCard> removablecards = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
                    Collections.shuffle(removablecards, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(removablecards.get(0), (float) Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    AbstractCard card = removablecards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(card);
                }
            }
        }
        if (AbstractDungeon.player.masterDeck.size() > 0) {
            return 0;
        } else {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
            return 0;
        }
    }

    public boolean canSpawn() {
        return (!Settings.isEndless && !AbstractDungeon.player.hasRelic(MidasArmor.ID) && !AbstractDungeon.player.hasRelic(RegenHeart.ID));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
