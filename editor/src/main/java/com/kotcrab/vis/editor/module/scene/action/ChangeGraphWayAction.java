/*
 * Copyright 2014-2016 See AUTHORS file.
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
 */

package com.kotcrab.vis.editor.module.scene.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.module.scene.entitymanipulator.EntityManipulatorModule;
import com.kotcrab.vis.editor.proxy.EntityProxy;
import com.kotcrab.vis.editor.util.undo.UndoableAction;
import com.kotcrab.vis.runtime.component.GraphWay;

/** @author Kotcrab */
public class ChangeGraphWayAction implements UndoableAction {
	private EntityManipulatorModule entityManipulator;

	private final EntityProxy proxy;
	private Array<Vector2> vertices;
	private Vector2[][] faces;

	private Array<Vector2> oldVertices;
	private Vector2[][] oldFaces;

	public ChangeGraphWayAction(EntityManipulatorModule entityManipulator, EntityProxy proxy) {
		this.entityManipulator = entityManipulator;
		this.proxy = proxy;

		GraphWay component = proxy.getComponent(GraphWay.class);
		oldVertices = copyArray(component.vertices);
		oldFaces = component.faces;
	}

	public void takeSnapshot () {
		GraphWay component = proxy.getComponent(GraphWay.class);
		vertices = copyArray(component.vertices);
		faces = component.faces;
	}

	private Array<Vector2> copyArray (Array<Vector2> orig) {
		Array<Vector2> copy = new Array<>(orig.size);

		for (Vector2 v : orig) {
			copy.add(new Vector2(v));
		}

		return copy;
	}

	@Override
	public void undo () {
		swap(oldVertices, oldFaces);
	}

	@Override
	public void execute () {
		swap(vertices, faces);
	}

	private void swap (Array<Vector2> newPoints, Vector2[][] newVertices) {
		if (vertices == null) throw new IllegalStateException("You must call #takeSnapshot first!");

		proxy.reload();

		GraphWay component = proxy.getComponent(GraphWay.class);
		component.vertices = newPoints;
		component.faces = newVertices;

		entityManipulator.selectedEntitiesValuesChanged();
	}

	@Override
	public String getActionName () {
		return "Modify Polygon";
	}
}
