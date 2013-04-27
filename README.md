libraryfinder
=============

**Library Finder** helps finding library files (.jar/.zip) for any class/file name pattern. 
It's a troubleshooting tool for *ClassNotFoundException* or *NoClassDefFoundError*. 
It also helps setting up IntelliJ project/workspace by identifying libraries used by java source.

Features
--------

* Ability to add library files to IntelliJ classpath(module/project/global)
* Supports wildcard (*,?) file name pattern and regular expressions, hence, allowing to search for any file/resource types (.properties,.dtd,.tld,.xml, etc.)
* Intention actions to resolve single or all unresolved imports
* Open up file explorer in all IntelliJ supported platforms (Windows, Linux & Mac OS) navigating to the containing folder
* Copy library files classpath to the clipboard with its platform dependent classpath separator
