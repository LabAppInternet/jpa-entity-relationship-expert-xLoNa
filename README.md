# JPA Mapping Exercise for Notes2425 Project

## Objective:
Your task is to add the necessary JPA mappings to the domain classes in the `notes2425` project to ensure proper interaction with the database.
This exercise will help you understand how to map Java objects to database tables using JPA annotations.

## What you have:
A working code base for a note-taking application that allows users to create, read, and update notes. Also, users can give permission to other
users to view or edit their notes.
In the resources folder, you have a `shema.sql` file that contains the database schema and
a `data.sql` file that contains some data to be inserted into the database when the application starts.

You want that JPA automatically creates the tables and the relationships between them in a way that all the `data.sql` file is still valid.
Note also that the `application.properties` file already configures the application to use an in-memory H2 database and instructs JPA to create the tables automatically as
well as to write the SQL queries to the console. You can use this to check if JPA creates the tables correctly.

## Instructions:
1. **Clone the Project**: Start by cloning the `Notes2425JPAtables` project from the provided repository. This project contains the solution and will serve as your reference.

2. **Analyze the Domain Classes**: Examine the classes in the domain package project. Pay special attention to relationships between entities such as `Note,` User, `Tag,` and `Permission.`

3. **Add JPA Annotations**: Based on your analysis, add the appropriate JPA annotations to the domain classes. Relationships are:
   - A `Note` can have multiple `Tag`s.
   - A `Tag` can be associated with multiple `Note`s, but we are not interested in this relationship.
   - A `User` can have multiple `Note`s.
   - A `Note` can have multiple `Permission`s.
   - A `User` can have multiple `Permission`s.
   - A `Permission` is associated with a `Note` and a `User`. So, a permission has a composed key of `note_id` and `user_id`.

This exercise will deepen your understanding of JPA and its role in mapping Java objects to relational database tables. Good luck!