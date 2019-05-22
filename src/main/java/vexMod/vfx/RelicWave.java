package vexMod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RelicWave extends AbstractGameEffect {

    private final int amplitudeframes = 200;
    private final float wavespeed = 0.01F * (float) Math.PI * 2;
    private final float frequency = 1F;
    private final float targetAmplitude = Settings.scale * 20;
    private final float defaultY = (float) Settings.HEIGHT - 102.0F * Settings.scale;

    private float phase;

    private float currentAmplitude;

    private int curAframes;

    public RelicWave(Mode mode, float phase) {
        this.phase = phase;
        if (mode == Mode.AMPLITUDESLOW) {
            currentAmplitude = 0;
            curAframes = -amplitudeframes;
        } else {
            currentAmplitude = targetAmplitude;
        }
    }

    public RelicWave(Mode mode) {
        this(mode, 0);
    }

    public RelicWave(float phase) {
        this(Mode.NONE, phase);
    }

    public RelicWave() {
        this(Mode.NONE, 0);
    }

    public void update() {
        int size = AbstractDungeon.player.relics.size();
        phase += wavespeed;
        if (currentAmplitude < targetAmplitude) {
            currentAmplitude = targetAmplitude / (1 + (float) Math.pow(Math.E, (-6F / amplitudeframes) * curAframes++));
        }
        double multiplyer = Math.PI * 2 / size;
        for (int i = 0; i < size; i++) {
            AbstractRelic ar = AbstractDungeon.player.relics.get(i);
            ar.currentY = defaultY + ((float) Math.sin((i * multiplyer + phase) * frequency) * currentAmplitude);
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {

    }

    public enum Mode {
        NONE,
        AMPLITUDESLOW
    }
}