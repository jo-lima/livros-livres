-- AUTORES
INSERT INTO `tbl_Autor` (`Ativo`, `Citacao`, `Descricao`, `Imagem`, `Nome`) VALUES
(1, 'A imaginação é mais importante que o conhecimento.', 'Escritor de ficção científica famoso por Fundação.', 'https://placehold.co/600x400?text=Isaac+Asimov', 'Isaac Asimov'),
(1, 'O sucesso vem para quem está ocupado demais.', 'Escritora de romances clássicos ingleses.', 'https://placehold.co/600x400?text=Jane+Austen', 'Jane Austen'),
(1, 'Ser ou não ser, eis a questão.', 'Maior dramaturgo da língua inglesa.', 'https://placehold.co/600x400?text=William+Shakespeare', 'William Shakespeare'),
(1, 'A melhor maneira de prever o futuro é inventá-lo.', 'Escritor de ficção científica visionário.', 'https://placehold.co/600x400?text=Arthur+C.+Clarke', 'Arthur C. Clarke'),
(1, 'Todos os animais são iguais, mas alguns são mais iguais.', 'Escritor e jornalista britânico.', 'https://placehold.co/600x400?text=George+Orwell', 'George Orwell'),
(1, 'Nada é tão assustador quanto a ignorância em ação.', 'Escritor alemão de faustos.', 'https://placehold.co/600x400?text=Goethe', 'Goethe'),
(1, 'A única coisa que sabemos é que nada sabemos.', 'Filósofo grego clássico.', 'https://placehold.co/600x400?text=Platao', 'Platão'),
(1, 'Penso, logo existo.', 'Filósofo e matemático francês.', 'https://placehold.co/600x400?text=Descartes', 'Descartes'),
(1, 'Apressa-te a viver bem.', 'Filósofo estoico romano.', 'https://placehold.co/600x400?text=Seneca', 'Sêneca'),
(1, 'O insucesso é oportunidade para recomeçar.', 'Empresário e inventor americano.', 'https://placehold.co/600x400?text=Henry+Ford', 'Henry Ford'),
(1, 'A arte existe porque a vida não basta.', 'Escritora brasileira contemporânea.', 'https://placehold.co/600x400?text=Clarice+Lispector', 'Clarice Lispector'),
(1, 'A persistência é o caminho do êxito.', 'Escritor realista brasileiro.', 'https://placehold.co/600x400?text=Machado+de+Assis', 'Machado de Assis');

-- LIVROS
INSERT INTO `tbl_Livro` (`Ativo`, `DataPublicacao`, `Descricao`, `Editora`, `Estoque`, `Genero`, `Imagem`, `ISBN`, `Nome`, `Paginas`, `idAutor`) VALUES
(1, '1951-01-01', 'Série sobre império galáctico.', 'Aleph', 50, 'Ficção Científica', 'https://placehold.co/600x400?text=Fundacao', '978-8535902774', 'Fundação', 320, 1),
(1, '1813-01-28', 'Romance sobre amor e sociedade.', 'Penguin', 30, 'Romance', 'https://placehold.co/600x400?text=Orgulho+Preconceito', '978-8572321449', 'Orgulho e Preconceito', 424, 2),
(1, '1603-01-01', 'Tragédia sobre poder e traição.', 'Penguin', 25, 'Teatro', 'https://placehold.co/600x400?text=Hamlet', '978-8572321456', 'Hamlet', 289, 3),
(1, '1968-01-01', 'Missão espacial à Jupiter.', 'Aleph', 40, 'Ficção Científica', 'https://placehold.co/600x400?text=2001+Odisseia', '978-8535902781', '2001: Uma Odisseia no Espaço', 336, 4),
(1, '1949-06-08', 'Sátira sobre totalitarismo.', 'Companhia Letras', 35, 'Distopia', 'https://placehold.co/600x400?text=1984', '978-8535902798', '1984', 328, 5),
(1, '1832-01-01', 'Drama filosófico alemão.', 'Editora 34', 20, 'Drama', 'https://placehold.co/600x400?text=Fausto', '978-8573261234', 'Fausto', 432, 6),
(1, '0380-01-01', 'Diálogos filosóficos gregos.', 'Edipro', 15, 'Filosofia', 'https://placehold.co/600x400?text=A+Republica', '978-8572831234', 'A República', 480, 7),
(1, '1637-01-01', 'Tratado filosófico moderno.', 'Martins Fontes', 18, 'Filosofia', 'https://placehold.co/600x400?text=Discurso+Metodo', '978-8533612345', 'Discurso do Método', 128, 8),
(1, '0065-01-01', 'Reflexões sobre a vida.', 'Penguin', 22, 'Filosofia', 'https://placehold.co/600x400?text=Cartas+Morais', '978-8572321487', 'Cartas Morais a Lucílio', 256, 9),
(1, '1922-01-01', 'Sobre eficiência industrial.', 'Editora Saraiva', 28, 'Negócios', 'https://placehold.co/600x400?text=Minha+Vida', '978-8502050675', 'Minha Vida e Minha Obra', 320, 10),
(1, '1977-01-01', 'Romance introspectivo brasileiro.', 'Rocco', 45, 'Romance', 'https://placehold.co/600x400?text=A+Hora+Estrela', '978-8532501234', 'A Hora da Estrela', 96, 11),
(1, '1899-01-01', 'Clássico da literatura brasileira.', 'Companhia Letras', 38, 'Romance', 'https://placehold.co/600x400?text=Dom+Casmurro', '978-8572321494', 'Dom Casmurro', 256, 12),
(1, '1952-01-01', 'Segundo livro da série Fundação.', 'Aleph', 42, 'Ficção Científica', 'https://placehold.co/600x400?text=Fundacao+Imperio', '978-8535902804', 'Fundação e Império', 352, 1),
(1, '1953-01-01', 'Terceiro livro da série Fundação.', 'Aleph', 39, 'Ficção Científica', 'https://placehold.co/600x400?text=Segunda+Fundacao', '978-8535902811', 'Segunda Fundação', 304, 1),
(1, '1811-01-01', 'Romance sobre irmãs inglesas.', 'Penguin', 26, 'Romance', 'https://placehold.co/600x400?text=Razao+Sensibilidade', '978-8572321500', 'Razão e Sensibilidade', 384, 2),
(1, '1600-01-01', 'Comédia romântica shakespeariana.', 'Penguin', 31, 'Teatro', 'https://placehold.co/600x400?text=Sonho+Noite+Verao', '978-8572321517', 'Sonho de Uma Noite de Verão', 192, 3),
(1, '1972-01-01', 'Sequência de 2001.', 'Aleph', 33, 'Ficção Científica', 'https://placehold.co/600x400?text=Odisseia+Dois', '978-8535902828', '2010: Odisseia Dois', 384, 4),
(1, '1945-01-01', 'Sátira política animal.', 'Companhia Letras', 47, 'Distopia', 'https://placehold.co/600x400?text=Revolucao+Bichos', '978-8535902835', 'A Revolução dos Bichos', 152, 5),
(1, '1808-01-01', 'Parte dois de Fausto.', 'Editora 34', 19, 'Drama', 'https://placehold.co/600x400?text=Fausto+II', '978-8573261241', 'Fausto II', 448, 6),
(1, '2010-01-01', 'Edição moderna de A República.', 'Edipro', 24, 'Filosofia', 'https://placehold.co/600x400?text=Republica+Especial', '978-8572831241', 'A República - Edição Especial', 512, 7),
(1, '2015-01-01', 'Nova tradução do Discurso.', 'Martins Fontes', 21, 'Filosofia', 'https://placehold.co/600x400?text=Discurso+Critica', '978-8533612352', 'Discurso do Método - Edição Crítica', 144, 8),
(1, '2018-01-01', 'Seleção das melhores cartas.', 'Penguin', 29, 'Filosofia', 'https://placehold.co/600x400?text=Cartas+Seneca', '978-8572321524', 'Cartas de Sêneca', 288, 9),
(1, '1950-01-01', 'Biografia de Henry Ford.', 'Editora Saraiva', 23, 'Biografia', 'https://placehold.co/600x400?text=Ford+Biografia', '978-8502050682', 'Ford: O Homem e a Máquina', 416, 10),
(1, '1960-01-01', 'Coletânea de contos brasileiros.', 'Rocco', 36, 'Contos', 'https://placehold.co/600x400?text=Lacos+Familia', '978-8532501241', 'Laços de Família', 224, 11),
(1, '1881-01-01', 'Romance realista brasileiro.', 'Companhia Letras', 41, 'Romance', 'https://placehold.co/600x400?text=Memorias+Bras+Cubas', '978-8572321531', 'Memórias Póstumas de Brás Cubas', 288, 12),
(1, '1955-01-01', 'Livro de contos de Asimov.', 'Aleph', 34, 'Ficção Científica', 'https://placehold.co/600x400?text=Eu+Robo', '978-8535902842', 'Eu, Robô', 256, 1),
(1, '1815-01-01', 'Romance sobre Emma Woodhouse.', 'Penguin', 27, 'Romance', 'https://placehold.co/600x400?text=Emma', '978-8572321548', 'Emma', 448, 2),
(1, '1606-01-01', 'Tragédia do rei britânico.', 'Penguin', 32, 'Teatro', 'https://placehold.co/600x400?text=Rei+Lear', '978-8572321555', 'Rei Lear', 320, 3),
(1, '1982-01-01', 'Terceiro livro da série Odisseia.', 'Aleph', 37, 'Ficção Científica', 'https://placehold.co/600x400?text=Odisseia+Tres', '978-8535902859', '2061: Odisseia Três', 368, 4);

-- UPDATE SIZE
UPDATE `tbl_Livro` SET `Imagem` = REPLACE(`Imagem`, '600x400', '320x480');