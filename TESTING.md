# Testing Plan

The following 15 test cases cover the complete requested system. Stage 1 currently supports test cases 1-7 and 15 where applicable; the remaining cases are for later stages.

| # | Test action | Expected result |
|---|---|---|
| 1 | Add ` cos201 `, title `Programming I`, units `3` | Course is added with code `COS201`. |
| 2 | Add code `AB12` | Course is rejected because the code format is invalid. |
| 3 | Add an empty title | Course is rejected because the title is empty. |
| 4 | Add units `0` | Course is rejected because units must be 1-6. |
| 5 | Add units `7` | Course is rejected because units must be 1-6. |
| 6 | Enter `three` for units | Input is rejected without crashing. |
| 7 | Add the same course code twice | The second course is rejected as a duplicate. |
| 8 | View courses when the list is empty | A friendly empty-list message is displayed. |
| 9 | Search for an existing course code | The matching course is displayed. |
| 10 | Search for a code that does not exist | A friendly not-found message is displayed. |
| 11 | Compute total units for three courses | The recursive total equals the sum of all units. |
| 12 | Save courses to `courses.txt` | Courses are saved one per line using `CODE|TITLE|UNITS`. |
| 13 | Save with an empty list | The program asks for confirmation before saving. |
| 14 | Load a file containing malformed lines | Valid lines load; malformed lines are skipped and counted. |
| 15 | Choose an invalid menu option | A friendly error is displayed and the menu appears again. |