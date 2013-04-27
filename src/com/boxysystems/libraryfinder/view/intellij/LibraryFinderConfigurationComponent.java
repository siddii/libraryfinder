package com.boxysystems.libraryfinder.view.intellij;

import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.view.intellij.ui.LibraryFinderConfigurationForm;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 11, 2007
 * Time: 1:21:19 AM
 */
public class LibraryFinderConfigurationComponent implements ApplicationComponent, Configurable, JDOMExternalizable {
  public String excludedFolders; //need to public inorder to be saved by DefaultJDOMExternalizer
  public String openContainingFolderScript; //need to public inorder to be saved by DefaultJDOMExternalizer
  private LibraryFinderConfigurationForm form;


  public static synchronized LibraryFinderConfigurationComponent getInstance() {
    return ApplicationManager.getApplication().getComponent(LibraryFinderConfigurationComponent.class);
  }

  public void initComponent() {
    initExcludedFolders();
    initOpenContainingFolderScript();
  }

  private void initOpenContainingFolderScript() {
    if (openContainingFolderScript == null) {
      if (Constants.OS.startsWith("Windows")) {
        openContainingFolderScript = "explorer";
      } else if (Constants.OS.startsWith("Mac OS")) {
        openContainingFolderScript = "";
      } else {
        String[] fileBrowsers = {"nautilus", "konqueror", "firefox", "opera", "epiphany", "mozilla", "netscape"};
        try {
          for (int count = 0; count < fileBrowsers.length && openContainingFolderScript == null; count++) {
            if (Runtime.getRuntime().exec(new String[]{"which", fileBrowsers[count]}).waitFor() == 0) {
              openContainingFolderScript = fileBrowsers[count];
            }
          }

        } catch (InterruptedException e) {
          //ignore
        } catch (IOException e) {
          //ignore
        }
      }
    }
  }

  private void initExcludedFolders() {
    if (excludedFolders == null) {
      excludedFolders = Constants.DEFAULT_EXCLUDED_FOLDERS;
    }
  }

  public void disposeComponent() {
    // TODO: insert component disposal logic here
  }

  @NotNull
  public String getComponentName() {
    return "LibraryFinderConfigurationComponent";
  }

  public String getExcludedFolders() {
    return excludedFolders;
  }

  public void setExcludedFolders(String excludedFolders) {
    this.excludedFolders = excludedFolders;
  }

  public String getOpenContainingFolderScript() {
    return openContainingFolderScript != null ? openContainingFolderScript.trim() : "";
  }

  public void setOpenContainingFolderScript(String openContainingFolderScript) {
    this.openContainingFolderScript = openContainingFolderScript;
  }

  @Nls
  public String getDisplayName() {
    return Constants.PLUGIN_ID;
  }

  public Icon getIcon() {
    return null;
  }

  @Nullable
  @NonNls
  public String getHelpTopic() {
    return null;
  }

  public JComponent createComponent() {
    if (form == null) {
      form = new LibraryFinderConfigurationForm();
    }
    return form.getRootComponent();
  }

  public boolean isModified() {
    return form != null && form.isModified(this);
  }

  public void apply() throws ConfigurationException {
    if (form != null) {
      form.getData(this);
    }
  }

  public void reset() {
    if (form != null) {
      form.setData(this);
    }
  }

  public void disposeUIResources() {
    form = null;
  }

  public void readExternal(Element element) throws InvalidDataException {
    DefaultJDOMExternalizer.readExternal(this, element);
  }

  public void writeExternal(Element element) throws WriteExternalException {
    DefaultJDOMExternalizer.writeExternal(this, element);
  }
}
