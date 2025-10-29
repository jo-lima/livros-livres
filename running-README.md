# Running the project
## Front-end
Simply host a webserver at [public/](public/).

## Back-end
### Requirements:
- MySql (or MariaDB, it is compatible to both)
- VsCode (used only for running with the ambient variables. You may run with other methods if have thoose configured)
- Latest Java version (Currently on XX)
- Maven

### Configuring the project (first time only):
The enviromnent variables are located on [.vscode/launch.json](.vscode/launch.json), at the "env" entry. If you are not running on vscode you may configure thoose variables in another form in your IDE or when building the project.
If on vscode: You'll probably don't need to change anything besides the database name, user and password.
When switching from dev to prod on "ACTIVE_PROFILE" the only changes are the server port that it listens to and some debug APIs are disabled.

### Running the project:
You can run the project by compiling the file at [Server/src/main/java/com/livros_livres/Server/ServerApplication.java](Server/src/main/java/com/livros_livres/Server/ServerApplication.java).
To run the file on vscode install the "Java" extension and click on "Run Java" with that file opened. (I tried with Code Runner and it did not worked).
The first time you'll run the project it will download all dependencies with Maven, that you need to have installed before. Otherwise it will throw an error.
