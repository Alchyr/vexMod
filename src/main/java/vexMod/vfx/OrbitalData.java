package vexMod.vfx;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class OrbitalData {
    private static int minframes = 200;
    private static int maxframes = 600;

    private static float minvelocity = -0.04F;
    private static float maxvelocity = 0.04F;

    private float mindistance;
    private float maxdistance;

    private float phase;

    private int distanceframes;
    private int curdframes;
    private float prevdistance;
    private float curdistance;
    private float targetdistance;

    private int velocityframes;
    private int curvframes;
    private float prevvelocity;
    private float curvelocity;
    private float targetvelocity;

    public OrbitalData() {
        mindistance = AbstractDungeon.player.hb_h * 2 / 3;
        maxdistance = AbstractDungeon.player.hb_h * 1.5F;

        phase = MathUtils.random((float) Math.PI * 2);

        targetvelocity = 0;
        prevvelocity = 0;
        prevdistance = 0;
        targetdistance = 0;

        curdframes = 0;
        distanceframes = 0;
        velocityframes = 0;
        curvframes = 0;
    }

    public void update(AbstractRelic relic) {
        if (curdframes < distanceframes) {
            curdistance = prevdistance + sigmoid(targetdistance - prevdistance, 6F / distanceframes, curdframes++);
            curdframes++;
        } else {
            distanceframes = MathUtils.random(minframes, maxframes);
            curdframes = -distanceframes;

            prevdistance = targetdistance;
            targetdistance = MathUtils.random(mindistance, maxdistance);
        }

        if (curvframes < velocityframes) {
            curvelocity = prevvelocity + sigmoid(targetvelocity - prevvelocity, 6F / velocityframes, curvframes);
            curvframes++;
        } else {
            velocityframes = MathUtils.random(minframes, maxframes);
            curvframes = -velocityframes;

            prevvelocity = targetvelocity;
            targetvelocity = MathUtils.random(minvelocity, maxvelocity);
        }

        phase += curvelocity;
        relic.currentX = curdistance * (float) Math.cos(phase) + AbstractDungeon.player.drawX;
        relic.currentY = curdistance * (float) Math.sin(phase) + AbstractDungeon.player.drawY + 100 * Settings.scale;
        relic.hb.move(relic.currentX, relic.currentY);
    }

    private float sigmoid(float endvalue, float steepness, float curval) {
        return endvalue / (1 + (float) Math.pow(Math.E, -steepness * curval));
    }
}