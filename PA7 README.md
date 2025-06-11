# Programming Assignment 7: Integer Lists

This assignment focuses on implementing two collections and achieving code reuse through
inheritance.

## Overview

You will implement two classes: first `ArrayIntList`, and then `SortedIntList`.

You will create `ArrayIntList` from scratch, i.e. **you should NOT inherit from or implement any Java
`Collections` classes or interfaces**. Your ArrayIntList will use an array as an instance variable

`SortedIntList` will inherit from `ArrayIntList`, and will have two primary differences from the
base class:

- A `SortedIntList` must maintain its list of integers in **sorted (increasing) order**.
- A `SortedIntList` has an option to specify that its elements should be unique (**no** duplicates).

## ArrayIntList

First, implement the following methods and constructors for `ArrayIntList`:

- `public ArrayIntList()`: constructs an empty list of a default capacity (10)
- `public ArrayIntList(int capacity)`: constructs an empty list with given capacity.
  - Should throw an `IllegalArgumentException` if the capacity is negative.
- `public void add(int value)`: appends given value to end of list
  - If the internal array has insufficient capacity, capacity should be added
- `public void add(int index, int value)`: inserts given value at given index, shifting subsequent
  values right.
  - Should allow adding at the end of the list, i.e. calling `add(0, x)` on an empty list should work.
  - Should throw an `ArrayIndexOutOfBounds` exception if the index is greater than the current
    `size()` of the `ArrayIntList`.
  - If the internal array has insufficient capacity, capacity should be added
- `public int get(int index)`: returns the element at the given `index`.
  - Should throw an `ArrayIndexOutOfBounds` exception if the index is greater than the current
    `size()` of the `ArrayIntList`.
- `public int indexOf(int value)`: returns the index of first occurrence of given `value` (or -1 if not
  found)
- `public void remove(int index)`: removes value at the given `index`, shifting subsequent values left.
  - Should throw an `ArrayIndexOutOfBounds` exception if the index is greater than the current
    `size()` of the `ArrayIntList`.
- `public int size()`: returns current number of elements in the list
- `public String toString()`: returns a string version of the list.
  - The expected format is square brackets (`[]`), with comma separated values in between, e.g.
    `"[4, 5, 17]"`.
  - You must implement this yourself, you may not use `Arrays.toString()`.
- `public void clear()`: removes all elements from this list.
- `public boolean contains(int value)`: returns true if the given value is contained in this list, else
  false.
- `public void ensureCapacity(int capacity)`: makes sure that this list's internal array is large
  enough to store at least the given number of elements.
  - Postcondition: length of internal array >= capacity
  - To ensure this postcondition, you may have to resize your array.
- `public boolean isEmpty()`: returns true if this list does not contain any elements, else false.
- `private void checkIndex(int index, int min, int max)`: throws an `ArrayIndexOutOfBoundsException`
  if `index` is not between `min` and `max`, inclusive.

## Testing - ArrayIntList

You are required to write a JUnit test for each public method of `ArrayIntList` in a file named
`ArrayIntListTest.java`. Remember that you have to write @Test above each of your tests in order for
JUnit to run them!

You do not have to write a test for the constructors. We recommend that you write the unit tests
**before** you start using `ArrayIntList` in order to implement `SortedIntList`, to rule out bugs
related to the base class you implement the sub-class.

## SortedIntList

The class `SortedIntList` should extend `ArrayIntList`, adding a few new methods/constructors, and
overriding some existing methods to modify/improve their functionality.

Write the following constructors and methods on SortedIntList. Note that there are additional
requirements for SortedIntList under [Implementation Notes](#sortedintlist-implementation-notes) below.

- `public SortedIntList()`: constructs an empty list of a default capacity, allowing duplicates
- `public SortedIntList(boolean unique)`: constructs empty list of default capacity and given `unique`
  setting
- `public SortedIntList(int capacity)`: constructs an empty list with given capacity, allowing
  duplicates
  - Throws an `IllegalArgumentException` if capacity is negative
- `public SortedIntList(boolean unique, int capacity)`: constructs an empty list with given capacity
  and `unique` setting
  - Throws an `IllegalArgumentException` if capacity is negative
- `public void add(int value)`: possibly adds given value to list, maintaining sorted order
- `public void add(int index, int value)`: always throws an `UnsupportedOperationException`
- `public boolean getUnique()`: returns whether only unique values are allowed in the list
- `public int indexOf(int value)`: same behavior as before, but optimized; see
  [Implementation Notes](#sortedintlist-implementation-notes)
- `public int max()`: returns the maximum integer value stored in the list
  - Throws a `NoSuchElementException` if the list is empty
- `public int min()`: returns the minimum integer value stored in the list
  - Throws a `NoSuchElementException` if the list is empty
- `public void setUnique(boolean unique)`: sets whether only unique values are allowed in the list;
  - If set to true, immediately removes any existing duplicates from the list
- `public String toString()`: returns a string version of the list. The expected format is:
  - `S:<ArrayIntList representation>` if `getUnique()` is false
    - For example, a list of the numbers `3, 5, 7, 9` with `getUnique` false is: `S:[3, 5, 7, 9]`
  - `S:<ArrayIntList representation>U` if `getUnique()` is true
    - For example, list of the numbers `3, 5, 7, 9` with `getUnique` true is: `S:[3, 5, 7, 9]U`
  - `<ArrayIntList representation>` is the format specified for `ArrayIntList.toString()`

The `SortedIntList` class will have a field to keep track of whether or not it is limited to unique
values. The value can be set to your second constructor or by calling `setUnique`. Think of it as
an on/off switch that each list has.

You will override the `add` method to ensure that elements are added in sorted order, and you will
override the `indexOf` method to be more efficient by taking advantage of the list's sorted order.
You will also override `toString`. All other methods inherited from `ArrayIntList` should use
the default inherited behavior and should not be overridden.

### SortedIntList Implementation Notes:

- `public void add(int value)`

  Your single-argument `add` method should be overridden to ensure a sorted list. You should no
  longer add at the end of the list; instead you should add the value at an appropriate place to keep
  the list **in sorted order**. For example, if the list is `[-3, 7, 18, 42]` and the user adds 27,
  afterward the list should contain [-3, 7, 18, **27** , 42] in that order. (Also see `indexOf`)

  The `add` method has to pay attention to whether the client has requested **unique values only**. If
  so, your method must not allow any element to be added if that value is already in the list. If an
  existing value is added again, it does not throw an exception or print a message; it just doesn’t add
  the repeated value, i.e. the list does not change.

  **You should not re-sort the entire list every time an element is added**. Finding the correct index
  and inserting the element there is more efficient than re-sorting the whole list.

- `public void add(int index, int value)`

  For a sorted list, it does not make sense to allow the client to insert an element at any particular
  index. The elements should be ordered so that the list will remain sorted. Therefore, you do not
  want this method in your `SortedIntList` class; but you must inherit such a method because you
  extend `ArrayIntList`. The best we can do is to "disable" this method by overriding it to always
  throw an `UnsupportedOperationException`. The list should not be modified when this method is called.

- `public void setUnique(boolean value)`

  Allows client to set whether to allow duplicates in the list (`true` means no duplicates, `false`
  means duplicates allowed).

  If the unique switch is set to off (false), the list allows any integer to be added, even if that
  integer is already found in the list. If the unique switch is on (true), any call to `add` that
  passes a value already in the list has no effect. In other words, when the unique switch is true,
  the `add` method should not allow any duplicates to be added to the list.

  For example, if you start with an empty list that has the unique switch off, adding three `42`s will
  generate the list `[42, 42, 42]`. But if your list has the unique switch on, adding those same three
  42s would generate the single-element list `[42]`.

  If the client sets unique to `true` when the list has duplicates, `setUnique` should
  **remove all duplicates** and ensure that no future duplicates can be added unless the client sets
  unique back to false. If the client changes the unique setting to false, the list elements do not
  immediately change, but it will mean that duplicates could be added in the future.

- `public int max() / public int min()`

  These methods should rely on the fact that the list is sorted, and compute the max and min efficiently (i.e. they should not inspect every item in the list).

- `public int indexOf(int value)`

  This method should be overridden to take advantage of the fact that the list is sorted. It should use
  the faster **binary search** algorithm rather than the sequential search used in `ArrayIntList`. If
  the element is found in the list, your `indexOf` should return its position. The element might occur
  in the list multiple times; if so, you may return any index at which that element value appears. If
  the element is not found in the list, your method should return a negative number.

  We will discuss how binary search is implemented in class, but you should **not** try to implement
  binary search yourself. Use the built-in `Arrays.binarySearch` method for all index location
  searching ("Where is a value currently located?" "Where should I insert a new value?"). You can find
  its documentation in the
  [Arrays Java API docs](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#binarySearch-int:A-int-int-int-).

  For example, to search indexes 0-16 of an array called data for values `42` and `66`, you could write:

  ```
  //    index    0  1  2  3   4   5   6   7   8   9   10  11  12  13  14  15  16   17 18
  int[] data = {-4, 2, 7, 10, 15, 20, 22, 25, 30, 36, 42, 50, 56, 68, 85, 92, 103, 0, 0};
  int index1 = Arrays.binarySearch(data, 0, 17, 42); // index1 is 10
  int index2 = Arrays.binarySearch(data, 0, 17, 66); // index2 is -14
  ```

  **Note** that while there are other overloaded versions of `Arrays.binarySearch` that don’t
  require the start and end index, you must use this version of binarySearch that takes in
  the start and end index: your underlying array will have a number of empty slots containing
  0s that you don’t want to be searched.

  When `Arrays.binarySearch` is unable to find a value in the list, it returns a negative number
  one less than the index at which the value _would_ have been found if it had been in the array
  (sometimes called the **"insertion point"**). For example, in the example above the value `66` is not
  found, but if it had been in the list, it would have been at index 13; therefore the search returns -14.
  You should take advantage of this information when adding new elements to your list.

  To get access to the `Arrays` class, you should `import java.util.*;` at the beginning of your
  class.

## Testing - SortedIntList

You are required to write JUnit tests for `SortedIntList`, in a file named `SortedIntListTest.java`.
Write several test cases for your sorted int list by creating at least two lists, adding some
elements to them, calling various methods, and checking the expected results. You don’t have to
write a JUnit test for each method of SortedIntList, only a subset.

For full credit, your testing file must contain at least 2 test methods, and you must call at least
3 of the different methods on the list(s) you create.

## Development Strategy

We suggest that you develop the program in the following three stages:

- First, implement your basic `ArrayIntList`. Then, write unit tests for `ArrayIntList`.
  - Note that you may want to write the unit tests while you are implementing the class,
    practicing test-driven development. Writing the unit tests will get you to think about how
    the methods should behave precisely.
- Write a first version of SortedIntList that allows duplicates and doesn't worry at all about the
  issue of unique values. Just keep your list in sorted order and use binary search to speed up
  searching.
- Write your tests for `SortedIntList`. You may want to write tests for uniqueness that your current
  version would be failing, to help you think through the behavior of those functions.
- Modify your code to so that `SortedIntList` keeps track of whether the client wants only unique
  values. Add any state necessary and modify constructor(s) as needed. Next modify `add` so that it
  doesn't add duplicates if the unique setting is true.
- Write the `getUnique` and `setUnique` methods. Remember that if the client calls `setUnique` and
  sets the value to true, you must remove any duplicates currently in the list.

## Other requirements and grading notes

You may not use any features from Java's collection framework on this assignment, such as
`ArrayList` or other pre-existing collection classes. You also may not use the `Arrays.sort`
method to sort your list, or `Arrays.toString` to display your list.

A major focus of our style grading is **redundancy**. As much as possible you should avoid
redundancy and repeated logic within your code. Note that even constructors can be redundant; if
two or more constructors in your class (or the superclass) contain similar or identical code, you
should find a way to reduce this redundancy by making them call each other as appropriate. Any
additional methods you add to `SortedIntList` beyond those specified should be private so
that outside code cannot call them.

Another way you should avoid redundancy is by
**utilizing the behavior you inherit from the ArrayIntList superclass**. You should not re-implement
any `ArrayIntList` behavior from that is not modified in your class. Also, if part of your class's
new behavior can make use of the superclass's behavior, you should call constructors/methods from
the superclass. For example, the `ArrayIntList` already contains code for adding and removing
elements to the list at a specific index, so if your `SortedIntList` code needs to do those things,
you should not re-implement that functionality.

Properly **encapsulate** your objects by making any data fields in your class `private`
if you want them to be only accessible in that file, or `protected` if you want them accessible only
to other files in that package, including subclasses. Avoid unnecessary fields; use fields to store
important data of your objects but not to store temporary values only used within a single call to
one method. Fields should always be initialized inside a constructor or method, never at declaration.

## PA7 Style guide

For this PA, you are required to adhere to the style guidelines below.
Points will be deducted for violations of these style rules.

1. All classes must have a Javadoc header formatted like this:

```
/**
* first_name last_name
* email@brandeis.edu
* Month Date, Year
* PA#
* Explanation of the program/class
* Known Bugs: explain bugs/null pointers/etc.
*/
```

2. All methods must have meaningful Javadoc-style comments, and must use (where appropriate) the
   `@return` ([docs](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#@return)),
   `@params` ([docs](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#@param)),
   and `@throws` ([docs](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#@exception)) tags:

```
/**
  * <description of what the method does, what it returns, anything else important>
  * @param ...
  * @param ...
  * @return ...
  * @throws ...
  */
```

Method-level comments should be at an appropriate level of detail - i.e. enough for a reader to
understand what the function does, but not so much that the comment recreates the method
implementation in pseudocode.

3. Classes, methods, and variables should be named meaningfully and use consistent naming conventions
   (e.g. class constants are named with ALL_CAPS).

4. Unused code (variables, methods, classes) should not be present in your submission.

5. Classes should be properly encapsulated. Fields and methods should be as private as possible given
   the implementation (e.g. if a field is only accessed from within a class, it should be `private`;
   if it is accessed only from within a class hierarchy it should be `protected`; if it is accessed
   by another class it should either be exposed via getters & setters, or made `public`).

# Feeling stuck?

1. Review the assignment instructions and relevant lecture slides - there might be something you
   missed or forgot!
1. Post to the "Ask the Class" forum with general questions. Do not paste any code into the public
   forums.
1. If you are getting an error message, try searching online for the exact error message, there may
   be resources to help point you in the right direction.
1. Attend office hours

# Submission instructions

You can submit as many times as you want, up to the deadline. We will grade your most recent
submission prior to the assignment deadline.

You must submit first to GitHub, then to Gradescope. Both steps must be followed for each submission,
and the GitHub step must be performed first.

1. Submit to GitHub. Run the following command in the VSCode terminal:

   ```
   ./scripts/submit.sh
   ```

   If you get any errors from this command, reach out to the course staff.

2. Submit to Gradescope:
   - Go to the corresponding assignment in Gradescope.
   - Create a submission, and choose the option to submit from GitHub. Choose the repository for
     this assignment.
     - One time only, you will have to grant Gradescope access to GitHub.
   - **Check on the autograder results** - if it produces an error, reach out to the course staff as
     soon as you can.

# Working on assignments

1. Click on the link posted to Moodle
1. Click "Accept this assignment"

Then, you can work on the project in one of two ways:

### 1. Open the project in a Codespace

A "Codespace" is a a cloud-based development environment. This means that a computer somewhere
in the cloud has all the development tools you need already installed, and you connect to it
through a browser-based version of VSCode (a code editor). The benefit of this approach is that
you only need a modern, mainstream browser - no local installations to wrestle with. The downside
is that you need an active internet connection to work on your project.

**To open a Codespace for a project, follow the assignment link from Moodle, then click on the
"Open in Codespaces" button.**

### 2. Local VSCode

**Option 1 is recommended. If you choose option 2, you are expected to be able to
follow the installation instructions independently.**

You can also develop locally on your computer. You will need an internet connection to check out the
starter code, but after that you can work offline. The benefit of this approach is that you don't
need an internet connection, but the downside is that you need to install some software on your
computer.

Do this once:

1. [Install git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
1. [Install VSCode](https://code.visualstudio.com/download)
1. Install the `Devcontainers` extension in VSCode
1. Install Docker Desktop
   - [Windows installation](https://docs.docker.com/desktop/install/windows-install/)
   - [Mac installation](https://docs.docker.com/desktop/install/mac-install/)
   - [Linux installation](https://docs.docker.com/desktop/install/linux-install/)

Do this once per PA:

1. Open VSCode
1. Open the command palette (Cmd-Shift-P / Ctrl-Shift-P), and type "Git: Clone", and select it. Paste in the URL
   to your repository (found from the GitHub Classroom assignment after you accept it).
1. Choose a location on your computer to put the code.
1. Follow the "each time you open the assignment" section below

Do this each time you open the assignment:

1. Open VSCode and open the folder you cloned into in the previous section.
1. After the project opens, you should see a prompt in the lower right to "Reopen in container".
   Choose this. If you don't see the prompt, you may not have Docker installed correctly, reach out
   to the course staff for help.

# VSCode help

VSCode is a powerful Integrated Development Environment (IDE). It has many more features than you will need in this class,
so it isn't important to explore or master everything. This section contains information on the parts you'll need to know throughout the course.

<details><summary>Expand to see details</summary>

Here are the main components of the screen:

<img src=".images/vscode_sections.png" width="600" />

Later in the course, assignments will include automated tests. These can be accessed from the testing tab on the left (it is only visible when tests exist).
You can run and debug tests with the various "play" buttons. Hover over them for more detail.

<img src=".images/testing_tab.png" width="600" />

## Running code

When you have a Java file open which includes a `main` method, you will see a play button in the top right corner. If you click on it, it will run the `main` method.

<img src=".images/running_code.png" width="600" />

</details>
<br/>
