# Java CLI Task Manager

A simple command-line task manager written in Java.
Tasks are stored in a local `tasks.json` file.

## Features

* Add tasks
* List tasks
* Update task descriptions
* Remove tasks
* Mark tasks as `todo`, `in-progress`, or `done`
* Filter tasks by status

## How to Run

Compile the project:

```
javac CLI/*.java
```

* Run commands for different tasks :

Add task :

```
java CLI.Main add "Buy groceries"
```

List tasks :

```
java CLI.Main list
```
```
java CLI.Main list-in-progress
```
```
java CLI.Main list-done
```
Update task :

```
java CLI.Main update 1 "Buy vegetables"
```

Remove task :

```
java CLI.Main remove 1
```

Mark task status :

```
java CLI.Main mark 1 done
```
```
java CLI.Main mark-done 1
```
```
java CLI.Main mark-in-progress 1
```

## Project Repository

https://roadmap.sh/projects/task-tracker
