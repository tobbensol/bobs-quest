package view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import launcher.Boot;
import model.GameModel;

public class SelectLevelScreen extends AbstractScreen {

    public SelectLevelScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        stage.addActor(table);
        table.center().top().padTop(viewport.getScreenHeight() / 4f);

        table.setFillParent(true);
        Label title = new Label("Select Level", font);
        title.setFontScale(4f);
        table.add(title).colspan(2);

        int tableIndex = 0;
        for (String level : gameModel.getAvailableLevels()) {
            TextButton textButton = new TextButton(level, skin);
            if (tableIndex % 2 == 0) {
                table.row();
            }
            table.add(textButton).pad(10).minWidth(250).minHeight(50).colspan(1);
            textButton.addListener(Boot.INSTANCE.getGameController().selectLevel(textButton));
            tableIndex++;
        }


        TextButton back = new TextButton("Back", skin);
        table.row();
        table.add(back).padTop(20).minWidth(250).minHeight(50).colspan(2);

        back.addListener(Boot.INSTANCE.getGameController().goBackListener());

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.renderBackground();
    }

}
