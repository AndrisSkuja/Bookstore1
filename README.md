## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
# Bookstore

In order to run the code, the postgres jar file needs to be added to the classpath for the java.sql import.
Throughout the code, whenever the database server is accessed, a connection address, user, and password is required. The password and user present in the code would need to be changed to a password and user that has access to your own database.

once these fixes have been made, application can be run and used though the terminal.