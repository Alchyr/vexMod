package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import de.robojumper.ststwitch.TwitchConfig;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FluxCapacitor extends CustomRelic {


    public static final String ID = VexMod.makeID("FluxCapacitor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FluxCapacitor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FluxCapacitor.png"));

    public FluxCapacitor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        this.counter = -1;
    }

    public static void relicBullshit() {
        if (CardCrawlGame.playtime >= 0.0F && !AbstractDungeon.player.isDead) {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        CardCrawlGame.playtime = -600.0F;
        this.counter = -2;
    }

    @Override
    public void onVictory() {
        this.flash();
        if (TwitchConfig.readConfig().get().isEnabled()) {
            CardCrawlGame.playtime -= (30.0F + TwitchConfig.readConfig().get().getTimer());
        } else {
            CardCrawlGame.playtime -= 30.0F;
        }
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