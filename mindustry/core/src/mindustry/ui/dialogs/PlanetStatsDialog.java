package mindustry.ui.dialogs;

import arc.*;
import arc.flabel.*;
import arc.input.*;
import arc.math.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.Strings;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;

import static mindustry.Vars.*;

public class PlanetStatsDialog extends BaseDialog{
    
    public PlanetStatsDialog(){
        super("@planet.stats");
        
        // Add keyboard listener for 'S' key to close (same as back button)
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, KeyCode key){
                if(key == KeyCode.s || key == KeyCode.escape || key == KeyCode.back){
                    hide();
                    return true;
                }
                return false;
            }
        });
    }
    
    public void show(Planet planet){
        if(planet == null) return;
        
        PlanetStats stats = planet.getStats();
        
        cont.clear();
        buttons.clear();
        
        cont.table(t -> {
            t.add(planet.localizedName).center().pad(6).style(Styles.outlineLabel).color(Pal.accent);
            t.row();
            
            t.pane(p -> {
                p.margin(13f);
                p.left().defaults().left();
                p.setBackground(Styles.black3);
                
                p.table(statsTable -> {
                    // Time played
                    addStatString(statsTable, Core.bundle.get("stats.playtime"), formatTime(stats.timePlayed), 0f);
                    // Distance walked
                    addStatString(statsTable, Core.bundle.get("stats.distanceWalked", "Distance Walked"), formatDistance(stats.distanceWalked), 0.05f);
                    addStat(statsTable, Core.bundle.get("stats.playerDeaths", "Player Deaths"), stats.playerDeaths, 0.1f);
                    addStat(statsTable, Core.bundle.get("stats.enemiesDestroyed"), stats.enemyUnitsDestroyed, 0.15f);
                    if(stats.wavesLasted > 0){
                        addStat(statsTable, Core.bundle.get("stats.wave"), stats.wavesLasted, 0.2f);
                    }
                    addStat(statsTable, Core.bundle.get("stats.unitsCreated"), stats.unitsCreated, 0.25f);
                    addStat(statsTable, Core.bundle.get("stats.built"), stats.buildingsBuilt, 0.3f);
                    addStat(statsTable, Core.bundle.get("stats.destroyed"), stats.buildingsDestroyed, 0.35f);
                    addStat(statsTable, Core.bundle.get("stats.deconstructed"), stats.buildingsDeconstructed, 0.4f);
                }).top().grow().row();
            }).grow().pad(12).top();
        }).center().minWidth(370).maxSize(600, 550).grow();
        
        buttons.button("@back", Icon.left, this::hide).size(170f, 60f);
        
        show();
    }
    
    private void addStat(Table parent, String stat, int value, float delay){
        parent.add(new StatLabel(stat, value, delay)).top().pad(5).growX().height(50).row();
    }
    
    private void addStatString(Table parent, String stat, String value, float delay){
        parent.add(new StatLabelString(stat, value, delay)).top().pad(5).growX().height(50).row();
    }
    
    /** Formats time in milliseconds to a readable string. */
    private String formatTime(long milliseconds){
        return Strings.formatMillis(milliseconds);
    }
    
    /** Formats distance to a readable string (meters/kilometers). */
    private String formatDistance(float distance){
        if(distance >= 1000f){
            return String.format("%.2f km", distance / 1000f);
        }else{
            return String.format("%.1f m", distance);
        }
    }
    
    private static class StatLabel extends Table {
        private float progress = 0;

        public StatLabel(String stat, int value, float delay){
            setTransform(true);
            setClip(true);
            setBackground(Tex.whiteui);
            setColor(Pal.accent);
            margin(2f);

            FLabel statLabel = new FLabel(stat);
            statLabel.setStyle(Styles.outlineLabel);
            statLabel.setWrap(true);
            statLabel.pause();

            Label valueLabel = new Label("", Styles.outlineLabel);
            valueLabel.setAlignment(Align.right);

            add(statLabel).left().growX().padLeft(5);
            add(valueLabel).right().growX().padRight(5);

            actions(
                Actions.scaleTo(0, 1),
                Actions.delay(delay),
                Actions.parallel(
                    Actions.scaleTo(1, 1, 0.3f, Interp.pow3Out),
                    Actions.color(Pal.darkestGray, 0.3f, Interp.pow3Out),
                    Actions.sequence(
                        Actions.delay(0.3f),
                        Actions.run(() -> {
                            valueLabel.update(() -> {
                                progress = Math.min(1, progress + (Time.delta / 60));
                                valueLabel.setText("" + (int)Mathf.lerp(0, value, value < 10 ? progress : Interp.slowFast.apply(progress)));
                            });
                            statLabel.resume();
                        })
                    )
                )
            );
        }
    }
    
    private static class StatLabelString extends Table {
        private float progress = 0;
        private final String value;

        public StatLabelString(String stat, String value, float delay){
            this.value = value;
            setTransform(true);
            setClip(true);
            setBackground(Tex.whiteui);
            setColor(Pal.accent);
            margin(2f);

            FLabel statLabel = new FLabel(stat);
            statLabel.setStyle(Styles.outlineLabel);
            statLabel.setWrap(true);
            statLabel.pause();

            Label valueLabel = new Label("", Styles.outlineLabel);
            valueLabel.setAlignment(Align.right);

            add(statLabel).left().growX().padLeft(5);
            add(valueLabel).right().growX().padRight(5);

            actions(
                Actions.scaleTo(0, 1),
                Actions.delay(delay),
                Actions.parallel(
                    Actions.scaleTo(1, 1, 0.3f, Interp.pow3Out),
                    Actions.color(Pal.darkestGray, 0.3f, Interp.pow3Out),
                    Actions.sequence(
                        Actions.delay(0.3f),
                        Actions.run(() -> {
                            valueLabel.setText(value);
                            statLabel.resume();
                        })
                    )
                )
            );
        }
    }
}

