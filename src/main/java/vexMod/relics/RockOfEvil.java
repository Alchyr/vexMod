package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.relics.JuzuBracelet;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockOfEvil extends CustomRelic {

    public static final String ID = VexMod.makeID("RockOfEvil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockOfEvil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public RockOfEvil() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(666);
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), BlightHelper.getRandomBlight());
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}