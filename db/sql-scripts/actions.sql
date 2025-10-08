-- QUERYS - FUNCIONÁRIA

-- VERIFICAR LIVROS QUE ESTÃO EMPRESTADOS - FUNCIONARIA
SELECT L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE UE.Emprestimo_Ativo = 1

-- VERIFICAR LIVROS QUE FORAM DEVOLVIDOS - FUNCIONARIA
SELECT L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE UE.Emprestimo_Ativo = 0 AND UE.DataDevolucao IS NOT NULL


-- MOSTRAR TODOS OS LIVROS
SELECT L.Nome, L.Estoque, idLivro, L.ISBN
FROM tbl_Livro L

-- ADICIONAR LIVRO SE JA EXISTENTE - FUNCIONARIA (Botão de adicionar +1 ao estoque)
UPDATE tbl_Livro
SET Estoque = Estoque + 1
WHERE idLivro = ?;

-- ADICIONAR LIVRO - FUNCIONARIA
INSERT INTO tbl_Livro(ISBN, Descricao, Nome, Data_publicacao, Genero, Paginas, Editora)
VALUES(?,?,?,?,?,?,?)

-- Necessário verificar a relação de IdAutor e IdLivro na tbl_LivroAutor
-- BUSCAR LIVROS DE UM AUTOR
SELECT A.Nome, L.Nome
FROM tbl_LivroAutor LA
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
INNER JOIN tbl_Autor A
ON A.idAutor = LA.idAutor
WHERE A.Nome LIKE ? ;

-- BUSCAR LIVRO - BARRA DE PESQUISA
SELECT A.Nome, L.Nome
FROM tbl_LivroAutor LA
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
INNER JOIN tbl_Autor A
ON A.idAutor = LA.idAutor
WHERE L.Nome LIKE ? ;

-- VERIFICAR LIVROS QUE ESTÃO PENDENTES DE COLETA NA BIBLIOTECA - FUNCIONARIA
SELECT UE. idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE UE.Emprestimo_Ativo = 0

-- USUARIO PEGOU O LIVRO
UPDATE tbl_emprestimo
SET Data_Coleta = CURRENT_TIMESTAMP,
    Emprestimo_Ativo = 1
WHERE idEmprestimo = ?;


-- MOSTRAR USUARIO POR ORDEM ALFABETICA
SELECT UE. idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
ORDER BY U.Nome ASC

-- MOSTRAR USUARIO DATA DE EMPRESTIMO - Mais antiga pra mais recente
SELECT UE. idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE UE.Emprestimo_Ativo = 1
ORDER BY DataPrevistaDevolucao ASC

-- MOSTRAR USUARIO DATA DE EMPRESTIMO - Mais recente pra mais antiga 
SELECT UE.idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE UE.Emprestimo_Ativo = 1
ORDER BY DataPrevistaDevolucao DESC

-- BUSCAR USUARIO PELO CPF
SELECT UE.idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE U.CPF LIKE ?;

-- BUSCAR USUARIO PELO NOME
SELECT UE.idEmprestimo, L.Nome, U.Nome, UE.DataPrevistaDevolucao, UE.Data_Coleta, UE.Data_SolicitacaoEmprestimo
FROM tbl_UsuarioEmprestimo UE
INNER JOIN tbl_Usuario U
ON U.idUsuario = UE.idUsuario
INNER JOIN tbl_Livro L
ON L.idLivro =  UE.idLivro
WHERE U.Nome LIKE ?;


-- QUERYS - CLIENTE

-- BUSCAR POR GENERO - FILTRO
SELECT A.Nome, L.Nome, L.Data_publicacao
FROM tbl_LivroAutor LA
INNER JOIN tbl_Autor A
ON  A.idAutor = LA.idAutor
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
WHERE Genero LIKE ?;

-- BUSCAR POR GENERO, ANO - FILTRO
SELECT A.Nome, L.Nome, L.Data_publicacao
FROM tbl_LivroAutor LA
INNER JOIN tbl_Autor A
ON  A.idAutor = LA.idAutor
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
WHERE Genero LIKE ? AND Data_publiccao LIKE ?;


-- BUSCAR LIVRO, ANO - FILTRO
SELECT A.Nome, L.Nome
FROM tbl_LivroAutor LA
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
INNER JOIN tbl_Autor A
ON A.idAutor = LA.idAutor
WHERE L.Nome LIKE ? AND Data_publicacao LIKE ?;

-- BUSCAR LIVRO, ANO, GENERO - FILTRO
SELECT A.Nome, L.Nome
FROM tbl_LivroAutor LA
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
INNER JOIN tbl_Autor A
ON A.idAutor = LA.idAutor
WHERE L.Nome LIKE ? AND Data_publicacao LIKE ? AND Genero LIKE ?;

-- BUSCAR ANO - FILTRO
SELECT A.Nome, L.Nome
FROM tbl_LivroAutor LA
INNER JOIN tbl_Livro L
ON L.idLivro = LA.idLivro
INNER JOIN tbl_Autor A
ON A.idAutor = LA.idAutor
WHERE  Data_publicacao LIKE ?;

-- MOSTRAR TODOS OS LIVROS
SELECT L.Nome, L.Estoque, idLivro, L.ISBN
FROM tbl_Livro L

-- LOGIN
SELECT U.email, U.senha
FROM tbl_Usuario U
WHERE U.email = ? AND U.senha = ?;

-- VERIFICAR SE JA EXISTE CPF CADASTRO
SELECT * FROM tbl_usuario
WHERE CPF = ?;

-- VERIFICAR SE JA EXISTE EMAIL CADASTRO
SELECT * FROM tbl_usuario
WHERE EMAIL = ?;

-- CADASTRO 
INSERT INTO tbl_Usuario(CPF, nome, endereco, telefone)
VALUES(?,?,?,?);

INSERT INTO tbl_UsuarioLogin(email, senha)
VALUES(?,?,?,?,?,?);

-- ATUALIZAR SENHA
UPDATE tbl_UsuarioLogin
SET senha = ?
WHERE idUsuario = ?

-- ATUALIZAR EMAIL
UPDATE tbl_UsuarioLogin
SET email = ?
WHERE idUsuario = ?


-- Podemos pegar a query de busca de livro para pegar o Id do livro
-- REALIZAR AVALIACAO DO LIVRO
INSERT INTO tbl_Avaliacao(Nota, Comentario, idLivro, idUsuario)
VALUES(?,?,?,?)

-- Realizar empréstimo 
-- Data de devolucao, Data_Coleta ficará inicialmente como nula
INSERT INTO tbl_UsuarioEmprestimo(idLivro, idUsuario, DataPrevistaDevolucao, DataDevolucao, Data_Coleta, Data_SolicitacaoEmprestimo, Emprestimo_Ativo)
VALUES(?,?,?,?,?,?, CURRENT_TIMESTAMP)

