package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class NewClearReactor extends CustomRelic {

    public static final String ID = VexMod.makeID("NewClearReactor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NewClearReactor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public NewClearReactor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        this.counter = 100;
    }

    @Override
    public void atBattleStart() {
        EnergyPanel.totalCount = this.counter;
    }

    @Override
    public void onVictory() {
        this.counter = EnergyPanel.totalCount;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster = 0;
    }

    @Override
    public boolean canSpawn() {
        boolean hasBossRelic = false;
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.tier == RelicTier.BOSS) {
                hasBossRelic = true;
            }
        }

        return (AbstractDungeon.floorNum <= 18 && !AbstractDungeon.player.hasRelic(IceCream.ID) && !hasBossRelic && !(AbstractDungeon.player instanceof Defect));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        AbstractDungeon.player.energy.energyMaster = 0;
        if (room instanceof TreasureRoomBoss) {
            this.flash();
            this.counter += 100;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
