package com.boxysystems.libraryfinder.view.intellij.ui;

import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderConfigurationComponent;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderHistory;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 11, 2007
 * Time: 1:16:59 AM
 */
public class LibraryFinderConfigurationForm {
  private JPanel rootPanel;
  private JTextField txtExcludedFolders;
  private JTextField txtOpenContainingFolderScript;
  private JButton btnBrowseContainingFolderScript;
  private JButton btnClearClassFileName;
  private JButton btnClearDirHistory;

  private LibraryFinderHistory libraryFinderHistory = LibraryFinderHistory.getInstance();
  private final FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);

//  private FocusPoint focusPoint = new FocusPoint(Constants.CONFIG_FORM);

  public LibraryFinderConfigurationForm() {

//    Project defaultProject = ProjectManager.getInstance().getDefaultProject();
//    if (defaultProject != null) {
//      LibraryFinderPlugin.instance(defaultProject).track(focusPoint);
//    }
    enableOrDisableClearClassFileNameButton();
    enableOrDisableClearDirHistoryButton();

    btnClearClassFileName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        libraryFinderHistory.clearResourceMap();
        enableOrDisableClearClassFileNameButton();
      }
    });

    btnClearDirHistory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        libraryFinderHistory.clearDirMap();
        enableOrDisableClearDirHistoryButton();
      }
    });

    btnBrowseContainingFolderScript.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        final VirtualFile[] files = FileChooser.chooseFiles(getRootComponent(), descriptor);
        if (files != null && files.length == 1) {
          String script = files[0].getPresentableUrl();
          txtOpenContainingFolderScript.setText(script);
        }
      }
    });
  }

  private void enableOrDisableClearDirHistoryButton() {
    btnClearDirHistory.setEnabled(libraryFinderHistory.getDirs().size() > 0);
  }

  private void enableOrDisableClearClassFileNameButton() {
    btnClearClassFileName.setEnabled(libraryFinderHistory.getResources().size() > 0);
  }

  public void setData(LibraryFinderConfigurationComponent data) {
    txtExcludedFolders.setText(data.getExcludedFolders());
    txtOpenContainingFolderScript.setText(data.getOpenContainingFolderScript());
  }

  public void getData(LibraryFinderConfigurationComponent data) {
    data.setExcludedFolders(txtExcludedFolders.getText());
    data.setOpenContainingFolderScript(txtOpenContainingFolderScript.getText());
  }

  public boolean isModified(LibraryFinderConfigurationComponent data) {
    if ((txtExcludedFolders.getText() != null) && !txtExcludedFolders.getText().equals(data.getExcludedFolders())) {
      return true;
    } else
    if (txtOpenContainingFolderScript.getText() != null && !txtOpenContainingFolderScript.getText().equals(data.getOpenContainingFolderScript())) {
      return true;
    }
    return false;
  }

  public JComponent getRootComponent() {
    return rootPanel;
  }
}