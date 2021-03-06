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
package org.eclipse.reddeer.eclipse.m2e.core.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Class representing "Maven" &gt; "Archetypes" preference page.
 * 
 * @author jkopriva
 *
 */

public class MavenArchetypesPreferencePage extends PreferencePage {

	private static final String ADD_LOCAL_CATALOG = "Add Local Catalog...";
	private static final String ADD_REMOTE_CATALOG = "Add Remote Catalog...";
	private static final String EDIT_CATALOG = "Edit...";
	private static final String REMOVE_CATALOG = "Remove";
	private static final String LOCAL_CATALOG_SHELL = "Local Archetype Catalog";
	private static final String REMOTE_CATALOG_SHELL = "Remote Archetype Catalog";
	private static final String CATALOG_DESCRIPTION = "Description:";
	private static final String VERIFY_BUTTON = "Verify...";

	private static final Logger log = Logger.getLogger(MavenArchetypesPreferencePage.class);

	/**
	 * Construct the preference page with "Maven" &gt; "Archetypes".
	 */
	public MavenArchetypesPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] { "Maven", "Archetypes" });
	}

	/**
	 * Add new archetypes local catalog to Eclipse.
	 * 
	 * @param catalogFile
	 *            path to the XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 */
	public MavenArchetypesPreferencePage addLocalCatalog(String catalogFile, String description) {
		new PushButton(this, ADD_LOCAL_CATALOG).click();
		Shell localCatalogShell = new DefaultShell(LOCAL_CATALOG_SHELL);
		new DefaultCombo(localCatalogShell).setText(catalogFile);
		new LabeledText(localCatalogShell, CATALOG_DESCRIPTION).setText(description);
		new OkButton(localCatalogShell).click();
		new WaitWhile(new ShellIsAvailable(localCatalogShell));
		return this;
	}

	/**
	 * Add new archetypes remote catalog to Eclipse. Catalog is verified.
	 * Returns status of verification.
	 * 
	 * @param catalogFileURL
	 *            URL to the remote XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 * @return verificationResult status of verification
	 */
	public String addRemoteCatalog(String catalogFileURL, String description) {
		return addRemoteCatalog(catalogFileURL, description, true);
	}

	/**
	 * Add new archetypes remote catalog to Eclipse. Catalog is verified depends
	 * on verify parameter. Returns status of verification(if parameter verify
	 * is true, otherwise it returns empty string).
	 * 
	 * @param catalogFileURL
	 *            URL to the remote XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 * @param verify whether a catalog URL should be verified
	 * @return verificationResult status of verification
	 */
	public String addRemoteCatalog(String catalogFileURL, String description, boolean verify) {
		String verificationResult = "";
		new PushButton(this, ADD_REMOTE_CATALOG).click();
		Shell remoteCatalogShell = new DefaultShell(REMOTE_CATALOG_SHELL);
		new DefaultCombo(remoteCatalogShell).setText(catalogFileURL);
		new LabeledText(remoteCatalogShell, CATALOG_DESCRIPTION).setText(description);
		if (verify) {
			verificationResult = verifyURL(remoteCatalogShell);
		}
		log.info(verificationResult);
		new OkButton(remoteCatalogShell).click();
		new WaitWhile(new ShellIsAvailable(remoteCatalogShell));
		return verificationResult;
	}

	/**
	 * Returns list of catalog TableItems available in Maven Archetypes
	 * Preference page.
	 * 
	 * @return list of TableItems with catalogs from table
	 */
	public List<TableItem> getCatalogs() {
		Table table = new DefaultTable(this);
		return table.getItems();
	}

	/**
	 * Returns list of all catalog names available in Maven Archetypes
	 * Preference page.
	 * 
	 * @return list of catalog names from table
	 */
	public List<String> getCatalogNames() {
		List<String> catalogNames = new ArrayList<String>();
		for (TableItem item : getCatalogs()) {
			catalogNames.add(item.getText());
		}
		return catalogNames;
	}

	/**
	 * Removes Archetypes catalog from Eclipse.
	 * 
	 * @param catalogName
	 *            name of the catalog to be deleted
	 */
	public MavenArchetypesPreferencePage removeCatalog(String catalogName) {
		selectCatalog(catalogName);
		new PushButton(this, REMOVE_CATALOG).click();
		return this;
	}

	/**
	 * Edit local catalog.
	 * 
	 * @param catalogName
	 *            name of the catalog
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 */
	public MavenArchetypesPreferencePage editLocalCatalog(String catalogName, String catalogFile, String description) {
		editCatalog(catalogName, catalogFile, description, false);
		return this;
	}

	/**
	 * Edit remote catalog and verify. Returns status of verification.
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @return verificationResult status of verification
	 */
	public String editRemoteCatalog(String catalogName, String catalogFile, String description) {
		return editCatalog(catalogName, catalogFile, description, true);
	}

	/**
	 * Edit remote catalog. Returns status of verification(if parameter verify
	 * is true, otherwise it returns empty string).
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @param verify
	 *            true - verify remote catalog, false - do not verify remote
	 *            catalog
	 * @return verificationResult status of verification
	 */
	public String editRemoteCatalog(String catalogName, String catalogFile, String description, boolean verify) {
		return editCatalog(catalogName, catalogFile, description, verify);
	}

	/**
	 * Private method for editing catalog by name. Returns status of
	 * verification(if parameter verify is true, otherwise it returns empty
	 * string).
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @param verify
	 *            true - verify remote catalog, false - do not verify remote
	 *            catalog (only for remote catalogs)
	 * @return verificationResult status of verification
	 */
	private String editCatalog(String catalogName, String catalogFile, String description, boolean verify) {
		String verificationResult = "";
		selectCatalog(catalogName);
		new PushButton(this, EDIT_CATALOG).click();
		Shell editShell = new DefaultShell(new WithTextMatcher(new RegexMatcher(".* Archetype Catalog")));
		new DefaultCombo(editShell).setText(catalogFile);
		new LabeledText(editShell, CATALOG_DESCRIPTION).setText(description);
		if (verify) {
			verificationResult = verifyURL(editShell);
		}
		log.info(verificationResult);
		new OkButton(editShell).click();
		new WaitWhile(new ShellIsAvailable(editShell));
		return verificationResult;
	}

	/**
	 * If catalog with given name is available, returns true, otherwise false.
	 * 
	 * @param catalogName catalog name
	 * @return true if catalog with given name exists, false otherwise
	 */
	public boolean containsCatalog(String catalogName) {
		for (String name : getCatalogNames()) {
			if (name.equals(catalogName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for selecting catalogs.
	 * 
	 * @param catalogName
	 *            name of the catalog file
	 */
	public MavenArchetypesPreferencePage selectCatalog(String catalogName) {
		for (TableItem item : getCatalogs()) {
			if (item.getText().equals(catalogName)) {
				item.select();
			}
		}
		return this;
	}

	/**
	 * Protected method for verifying remote catalog.
	 * 
	 * @return result of verification
	 */
	protected String verifyURL(ReferencedComposite composite) {
		new PushButton(composite, VERIFY_BUTTON).click();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		Text labeledText = new LabeledText(composite, REMOTE_CATALOG_SHELL);
		String text = labeledText.getText();
		return text;
	}

}
