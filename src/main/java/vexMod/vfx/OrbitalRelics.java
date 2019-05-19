package vexMod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.HashMap;
import java.util.Map;

public class OrbitalRelics extends AbstractGameEffect {

    Map<AbstractRelic, OrbitalData> relics = new HashMap<>();

    public OrbitalRelics() {
        for (final AbstractRelic ar : AbstractDungeon.player.relics) {
            relics.put(ar, new OrbitalData());
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void update() {
        for (final AbstractRelic ar : relics.keySet()) {
            relics.get(ar).update(ar);
        }
    }

    public void dispose() {

    }
}