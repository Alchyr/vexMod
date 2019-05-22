package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class SpireShuffle extends CustomRelic {


    public static final String ID = VexMod.makeID("SpireShuffle");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpireShuffle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public SpireShuffle() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.player.dialogX += 10.0F * Settings.scale;
            AbstractDungeon.player.drawX += 10.0F * Settings.scale;
        } else if (card.type == AbstractCard.CardType.SKILL) {
            AbstractDungeon.player.dialogX -= 10.0F * Settings.scale;
            AbstractDungeon.player.drawX -= 10.0F * Settings.scale;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
