package vexMod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RelicWave extends AbstractGameEffect {

    private float defaultY;
    private float offset = 0;

    public RelicWave(float defaultY) {
        this.defaultY = defaultY;
    }

    public void update() {
        offset += 0.01F;
        double multiplyer = Math.PI * 2 / AbstractDungeon.player.relics.size();
        for(int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
            AbstractRelic ar = AbstractDungeon.player.relics.get(i);
            ar.currentY = defaultY + ((float)Math.sin(i * multiplyer + offset) * Settings.scale * 20);
        }
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {
        for(final AbstractRelic relic : AbstractDungeon.player.relics) {
            relic.currentY = defaultY;
        }
    }
}