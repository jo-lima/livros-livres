# LivrosLivres
Um projeto funcional de biblioteca, escrito na linguagem Java e Javascript.

# Rodando o projeto
As instruções para rodar o projeto estão no arquivo [running-README.md](/running-README.md)

## Imagens do projeto e seu funcionamento:
### Usuário não cadastrado:
Como um usuário não cadastrado, é possível apenas visualizar os livros em estoque e fazer pesquisas. Além e claro, de fazer o seu cadastro do site.
| Página do acervo | Página de detalhamento de um livro |
|---|---|
| ![main-page](/readme-src/main-page.png) | ![borrow](/readme-src/borrow.png) |

### Fazendo o cadastro:
Ao fazer o cadastro de um novo usuário no sistema é preciso fazer uma verificação do seu email, sistema usado também na funcionalidade de "esqueci minha senha".
| Durante a verificação de email | Após verificação de email |
|---|---|
| ![verify-email-page](/readme-src/verify-email-page.png) | ![cadastro](/readme-src/cadastro.png) |

**Email recebido:**
![verify-mail](/readme-src/verify-mail.png)

### Usuários logados:
Ao logar-se como um usuário comum, é possível fazer o pedido de empréstimo de livros (que devem ser aprovador por um administrador), conferir seus empréstimos, visualizar o histórico de empréstimos, dentre outras.

| Página de login | Formulário de pedido de empréstimo  |
|---|---|
| ![login](/readme-src/login.png) | ![borrow-form](/readme-src/borrow-form.png) |

### Administradores:
Administradores tem o poder total sobre o sistema. Podem acessar uma página de "dashboard", com várias informações e insights sobre a biblioteca.
Além de cadastrar e editar qualquer informações do sistema, como livros, autores e clientes. Aqui também é onde os administradores gerenciam os empréstimos.

| Pagina Principal do Dashboard | Edição de um livro  |
|---|---|
| ![main-dashboard-page](/readme-src/main-dashboard-page.png) | ![dashboard-edit](/readme-src/dashboard-edit.png)  |