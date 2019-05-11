package vexMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RelicWaveEffect extends AbstractGameEffect {

    private int toGet;

    private float baseduration;

    private boolean isFlashed = false;

    public RelicWaveEffect(int Index, float Timer) {
        this.toGet = Index;
        this.baseduration = Timer;
        this.duration = Timer;
        AbstractDungeon.player.relics.get(toGet).flash();
    }

    @Override
    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime(); //tickDuration();
        if (duration < 0.0F) {
            if (toGet < (AbstractDungeon.player.relics.size() - 1)) {
                AbstractDungeon.effectsQueue.add(new RelicWaveEffect(toGet + 1, baseduration));
            }
            isDone = true;
            duration = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {
    }
}