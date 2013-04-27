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

package com.boxysystems.libraryfinder.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Class for storing the result in clipboard
 * @author: Siddique Hameed
 * @since : Jan 6, 2006
 */
public class CopiedLibrary implements Transferable{
  private String libraryName;

  public CopiedLibrary(String libraryName) {
    this.libraryName = libraryName;
  }

  public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{DataFlavor.stringFlavor};
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
      return flavor.equals(DataFlavor.stringFlavor);
  }

  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    if (flavor.equals(DataFlavor.stringFlavor)){
      return this.libraryName;
    }
    return null;
  }
}
