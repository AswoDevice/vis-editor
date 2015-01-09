/*******************************************************************************
 * Copyright 2014 Pawel Pastuszak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package pl.kotcrab.vis.ui.test;

import pl.kotcrab.vis.ui.VisTable;
import pl.kotcrab.vis.ui.widget.CollapsibleWidget;
import pl.kotcrab.vis.ui.widget.Separator;
import pl.kotcrab.vis.ui.widget.VisCheckBox;
import pl.kotcrab.vis.ui.widget.VisLabel;
import pl.kotcrab.vis.ui.widget.VisTextButton;
import pl.kotcrab.vis.ui.widget.VisTextField;
import pl.kotcrab.vis.ui.widget.VisWindow;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TestCollapsable extends VisWindow {

	public TestCollapsable (boolean useVisComponets) {
		super("collapsiblewidget");

		columnDefaults(0).left();

		addVisComponents();

		setPosition(1000, 360);
		pack();
	}

	private void addVisComponents () {
		VisCheckBox collapse = new VisCheckBox("show advanced settings");
		collapse.setChecked(true);

		VisTable table = new VisTable();
		final CollapsibleWidget collapsibleWidget = new CollapsibleWidget(table, false);

		VisTable numberTable = new VisTable(true);
		numberTable.add(new VisLabel("2 + 2 * 2 = "));
		numberTable.add(new VisTextField());

		table.defaults().left();
		table.add(new VisCheckBox("advanced option #1")).row();
		table.add(new VisCheckBox("advanced option #2")).row();
		table.add(numberTable).padTop(3).row();

		collapse.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				collapsibleWidget.setCollapsed(!collapsibleWidget.isCollapsed());
			}
		});

		VisTable notAdvancedTable = new VisTable(true);

		notAdvancedTable.defaults().left();
		notAdvancedTable.add(new VisLabel("less advanced settings")).expandX().fillX().row();
		notAdvancedTable.add(new VisCheckBox("option #1")).row();
		notAdvancedTable.add(new VisCheckBox("option #2")).row();
		notAdvancedTable.add(new VisTextButton("button"));

		add(collapse).row();
		add(collapsibleWidget).expandX().fillX().row();
		add(new Separator()).padTop(10).fillX().expandX().row();
		add(notAdvancedTable).expandX().fillX().padTop(5).row();
		add().expand().fill().padBottom(3);
	}
}
