/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.ui.test.run.launchConfigurations;

import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationTab;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Represent RedDeer launch configuration tab
 * @author odockal
 *
 */
public class RedDeerLaunchConfigurationTab extends LaunchConfigurationTab {

	public RedDeerLaunchConfigurationTab() {
		super("RedDeer");
	}
	
	public DefaultTable getTable() {
		activate();
		return new DefaultTable();
	}

}
