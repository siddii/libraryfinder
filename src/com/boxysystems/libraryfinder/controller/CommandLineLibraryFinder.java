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


package com.boxysystems.libraryfinder.controller;

import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.view.CommandLineView;
import com.boxysystems.libraryfinder.view.LibraryFinderView;

import java.util.Arrays;
import java.util.List;

/**
 * Command line version of library finder plugin
 * <pre>
 * Usage: java -jar "LibraryFinder.jar" [-options] searchPath fileNamePattern
 * where options include:
 * -caseSensitive to perform case-sensitive matching (by default, its case-insensitive)
 * -regex         to perform regular expression matching on fileNamePattern
 * -verbose       to run in verbose mode
 * -exclude       to override default excluded folders(CVS;SCCS;RCS;rcs;.DS_Store;.svn;vssver.scc;vssver2.scc)
 * For example: java -jar "LibraryFinder.jar" "C:/Program Files/Oracle"  oracle.jdbc.driver.OracleDriver
 * Or.    java -jar "LibraryFinder.jar" /usr/siddique/lib com.therandomhomepage.widgets.LightboxImageWidget
 * Or.    java -jar "LibraryFinder.jar" -verbose -exclude .tmp /usr/siddique/lib com.therandomhomepage.widgets.LightboxImageWidget
 * </pre>
 *
 * @author Siddique Hameed
 * @since Jan 30, 2007
 */
public class CommandLineLibraryFinder extends LibraryFinderController {

    private static final String CASE_SENSITIVE_ARG = "-caseSensitive";
    private static final String REGEX_ARG = "-regex";
    private static final String EXCLUDE_ARG = "-exclude";
    private static final String VERBOSE_ARG = "-verbose";

    public static final String USAGE_TEXT = "Invalid Arguments!" + Constants.NEW_LINE +
            "Usage: java -jar \"LibraryFinder.jar\" [-options] searchPath fileNamePattern" + Constants.NEW_LINE +
            "where options include:" + Constants.NEW_LINE +
            "    -caseSensitive to perform case-sensitive matching (by default, its case-insensitive)" + Constants.NEW_LINE +
            "    -regex         to perform regular expression matching on fileNamePattern" + Constants.NEW_LINE +
            "    -exclude       to override default excluded folders(" + Constants.DEFAULT_EXCLUDED_FOLDERS + ") " + Constants.NEW_LINE +
            "    -verbose       to run in verbose mode " + Constants.NEW_LINE +
            "For example: java -jar \"LibraryFinder.jar\" \"C:/Program Files/Oracle\"  oracle.jdbc.driver.OracleDriver" + Constants.NEW_LINE +
            "      Or.    java -jar \"LibraryFinder.jar\" /usr/siddique/lib com.therandomhomepage.widgets.LightboxImageWidget";

    public CommandLineLibraryFinder(LibraryFinderView view) {
        super(view);
    }

    private static void printUsageText() {
        System.out.println(USAGE_TEXT);
    }

    protected static LibraryFinderQuery extractQuery(List<String> argsList) {
        LibraryFinderQuery query = new LibraryFinderQuery(argsList.get(argsList.size() - 2), argsList.get(argsList.size() - 1));
        query.setExcludedFolders(Constants.DEFAULT_EXCLUDED_FOLDERS);
        if (argsList.contains(CASE_SENSITIVE_ARG)) {
            query.setCaseSensitive(true);
        }
        if (argsList.contains(REGEX_ARG)) {
            query.setRegex(true);
        }
        if (argsList.contains(EXCLUDE_ARG)) {
            query.setExcludedFolders(argsList.get(argsList.indexOf(EXCLUDE_ARG) + 1));
        }
        if (argsList.contains(VERBOSE_ARG)) {
            query.setVerboseMode(true);
        }
        return query;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsageText();
            return;
        }
        final LibraryFinderQuery query = extractQuery(Arrays.asList(args));

        final CommandLineView view = new CommandLineView(query);
        CommandLineLibraryFinder libraryFinder = new CommandLineLibraryFinder(view);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                if (!view.isRendered()) {
                    view.render();
                }
            }
        });
        libraryFinder.performAction();
    }
}
