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

package com.boxysystems.libraryfinder.view.intellij.ui;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Apr 5, 2006
 * Time: 11:30:07 PM
 */

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.net.URL;

public class Icons {

  public static final Icon RERUN_ICON = IconLoader.getIcon("/actions/refreshUsages.png");
  public static final Icon CLOSE_ICON =
          IconLoader.getIcon("/actions/cancel.png");
  public static final Icon ERROR_ICON =
          IconLoader.getIcon("/nodes/errorIntroduction.png");
  public static final Icon LIBRARY_ICON =
          IconLoader.getIcon("/fileTypes/archive.png");
  public static final Icon HELP_ICON =
          IconLoader.getIcon("/actions/help.png");
}
