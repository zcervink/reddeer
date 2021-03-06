/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

/**
 * POJO representing row in "Installed JREs" table (Preferences &gt; Java &gt; Installed
 * JREs).
 * 
 * @author rhopp
 *
 */

public class JREItem {

	private String name;
	private String location;
	private String type;
	private boolean isDefault;

	/**
	 * Instantiates a new JRE item.
	 *
	 * @param name the name
	 * @param location the location
	 * @param type the type
	 */
	public JREItem(String name, String location, String type) {
		this.name = name;
		this.location = location;
		this.type = type;
		this.isDefault = false;
	}
	
	/**
	 * Instantiates a new JRE item.
	 *
	 * @param name the name
	 * @param location the location
	 * @param type the type
	 * @param isDefault the default
	 */
	public JREItem(String name, String location, String type, boolean checked) {
		this.name = name;
		this.location = location;
		this.type = type;
		this.isDefault = checked;
	}

	/**
	 * Gets if is checked/default
	 * @return the checked
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Sets the default.
	 * 
	 * @param isDefault if it is default setup
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
