/*
 * Copyright 2007 BoxySystems Inc. <siddique@boxysystems.com>
 *                  http://www.BoxySystems.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boxysystems.libraryfinder.view.intellij;

import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.jgoogleanalytics.JGoogleAnalyticsTracker;
import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.view.intellij.intention.FindLibrariesIntentionAction;
import com.boxysystems.libraryfinder.view.intellij.intention.FindLibraryIntentionAction;
import com.intellij.codeInsight.intention.IntentionManager;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Dec 19, 2005
 * Time: 9:51:43 AM
 */
public class LibraryFinderPlugin implements ProjectComponent {

  private Project project;
  private IntelliJLibraryFinderView view;

  private JGoogleAnalyticsTracker jGoogleAnalyticsTracker = null;
  private static final String GOOGLE_ANALYTICS_TRACKING_CODE = "UA-2184000-1";
  private FocusPoint pluginLoadFocusPoint = new FocusPoint(Constants.PLUGIN_LOAD_ACTION);

  public LibraryFinderPlugin(Project project) {
    this.project = project;
    initJGoogleAnalyticsTracker();
  }

  private void initJGoogleAnalyticsTracker() {
    IdeaPluginDescriptor libraryFinderPluginDescriptor = PluginManager.getPlugin(PluginId.getId(Constants.PLUGIN_ID));
    String version = libraryFinderPluginDescriptor.getVersion();
    jGoogleAnalyticsTracker = new JGoogleAnalyticsTracker(Constants.PLUGIN_ID, version, GOOGLE_ANALYTICS_TRACKING_CODE);
    jGoogleAnalyticsTracker.setLoggingAdapter(new JGoogleAnalyticsLog4jAdapter());
  }

  public void track(FocusPoint focusPoint) {
    jGoogleAnalyticsTracker.trackAsynchronously(focusPoint);
  }

  public void projectOpened() {
    track(pluginLoadFocusPoint);
    final IntentionManager intentionManager = IntentionManager.getInstance(project);
    intentionManager.registerIntentionAndMetaData(new FindLibraryIntentionAction(), Constants.PLUGIN_ID);
    intentionManager.registerIntentionAndMetaData(new FindLibrariesIntentionAction(), Constants.PLUGIN_ID);
  }

  public void projectClosed() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    List toolWindowIds = Arrays.asList(toolWindowManager.getToolWindowIds());
    if (view != null && toolWindowIds.contains(Constants.PLUGIN_ID)) {
      toolWindowManager.unregisterToolWindow(Constants.PLUGIN_ID);
    }
  }

  public IntelliJLibraryFinderView getView(LibraryFinderQuery query) {
    if (view == null) {
      view = new IntelliJLibraryFinderView(this, query);
    }
    return view;
  }

  @org.jetbrains.annotations.NotNull
  public String getComponentName() {
    return Constants.PLUGIN_ID;
  }

  public void initComponent() {
  }

  public void disposeComponent() {
  }

  public static LibraryFinderPlugin instance(Project project) {
    return project.getComponent(LibraryFinderPlugin.class);
  }

  public Project getProject() {
    return project;
  }

  public ToolWindow getToolWindow() {
    return view.getToolWindow();
  }
}
