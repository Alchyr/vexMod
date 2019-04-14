package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;
import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RegenHeart extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("RegenHeart");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RegenVirus.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RegenVirus.png"));


    public RegenHeart() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.maxHealth = 30;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
    }


    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(AbstractDungeon.player.maxHealth);
        }

    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
