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


public class PatternUtil {

    private static String escapeDotsWithSlashes(String pFileNamePattern) {
        String fileNamePattern = replaceBackwardSlashWithForwardSlash(pFileNamePattern);
        if (fileNamePattern.indexOf(".") != -1) {
            try {
                fileNamePattern = fileNamePattern.replaceAll("[.]", "/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileNamePattern;
    }

    private static String replaceBackwardSlashWithForwardSlash(String fileNamePattern) {
        if (fileNamePattern != null) {
            fileNamePattern = fileNamePattern.replace('\\', '/');
        }
        return fileNamePattern;
    }

    public static String generateFileNamePattern(String pattern) {
        String fileExt = "class";

        if (!pattern.endsWith(".class") && pattern.indexOf("/") == -1) {
            pattern += ".class";
        }

        int dotidx = pattern.lastIndexOf(".");

        if (dotidx > 0) {
            fileExt = pattern.substring(dotidx + 1);
            pattern = pattern.substring(0, dotidx);
        }
        pattern = escapeDotsWithSlashes(pattern);
        pattern = wildcardToRegex(pattern);
        pattern += "." + fileExt;
        return pattern;
    }

    protected static String wildcardToRegex(String wildcardPattern) {

        if (wildcardPattern != null && (wildcardPattern.indexOf("*") != -1 || wildcardPattern.indexOf("?") != -1)) {
            StringBuffer buffer = new StringBuffer();
            char[] chars = wildcardPattern.toCharArray();

            for (char aChar : chars) {
                if (aChar == '*')
                    buffer.append(".*");
                else if (aChar == '?')
                    buffer.append(".");
                else if ("+()^$.{}[]|\\".indexOf(aChar) != -1)
                    buffer.append('\\').append(chars); // prefix all metacharacters with backslash
                else
                    buffer.append(aChar);
            }

            return buffer.toString();
        }
        return wildcardPattern;
    }
}

