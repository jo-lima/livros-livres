# Running the project

## Front-end

Simply host a webserver at [public/](public/).

## Back-end
### Requisitos:
- MySql ou MariaDB
- JDK mais recente (atualmente a 21)
- Maven

### Configurando as Dependencias:
### MySQL
Para configurar incialmente o MySQL ou MariaDB, primeiro é preciso executar o comando no terminal para fazer a configuração incial. Esse comando se chama mariadb_secure_instalation ou mysql_secure_instalation. Ambos se comportam da mesma forma. (Caso esteja no linux deve rodar como sudo)
Neste arquivo voce deve colocar que SIM (y) você quer configurar uma senha. A senha não precisa ser necessáriametne boa, mas deve ser secreta.
Depois de configurar a senha voce pode dar enter e deixar as configurações padrão.

Após isso é preciso rodar o server e criar a database. (Rodar o servidor é preciso para toda vez que vai rodar o projeto, mas criar a databse somenten desta vez.).
A database pode ter qualquer nome, mas eu recomendo criar como livrosLivres que é o padrão.


### Configurando o Projeto:
Após baixar as dependencias listadas e clonar o projeto, deve-se abrir o arquivo [.env](.env) para configurar as variáveis de ambiente. Lá você coloca a senha do banco e o nome da database.


### Rodando o projeto:
Simplesmente execute o server-runner.sh, caso no windows, tente rodar pelo git bash (que vem por padrão junto com o git).
