# Student Course Management System

This is a console-based Java 17+ lab project using only the standard library.

## Current Stage

Stage 3 includes:

- `Course`, `InvalidCourseException`, `CourseManager`, and `Main` classes
- Add Course, View All Courses, Search Course by Code, Compute Total Units, Save, and Load operations
- A complete menu with options 1 through 7
- Centralized course validation in `CourseManager`
- Course code normalization, format validation, unit validation, and duplicate detection
- Recursive search and recursive total-unit calculation using private index-based helpers
- File persistence in `courses.txt` using the `CODE|TITLE|UNITS` format
- Safe loading that replaces the current list and counts malformed lines

The system uses only the Java standard library.

## Compile and Run

```text
javac *.java
java Main
```

The program creates or reads `courses.txt` when options 5 or 6 are selected.