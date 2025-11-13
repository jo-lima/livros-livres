class Base {
  SERVER_URL = "localhost:6969";
  DEBUG_TOKEN = "debug";

  // Base dos requests
  async sendPostRequest(url, body) {
    const response = await fetch(url, {
      method: "POST",
      body: JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
        token: this.DEBUG_TOKEN,
      },
    });

    return await response.json();
  }

  async sendGetRequest(url) {
    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        token: this.DEBUG_TOKEN,
      },
    });

    return await response.json();
  }

  // Request do autor
  async getAllAuthors() {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/autor/lista`);
  }

  async createAuthor(body) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/autor/novo`,
      body
    );
  }

  async editAuthor(id, body) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/autor/${id}/atualizar`,
      body
    );
  }

  async disableAuthor(id) {
    return await this.changeAuthorStatus(id, "inativar");
  }

  async enableAuthor(id) {
    return await this.changeAuthorStatus(id, "ativar");
  }

  async changeAuthorStatus(id, status) {
    const response = await fetch(
      `http://${this.SERVER_URL}/autor/${id}/${status}`,
      {
        method: "POST",
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  async getAuthor(id) {
    const response = await fetch(
      `http://${this.SERVER_URL}/autor/${id}/busca`,
      {
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  // Requests do livro
  async createBook(body) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/livro/novo`,
      body
    );
  }

  async editBook(id, body) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/livro/${id}/atualizar`,
      body
    );
  }

  async getAllBooks() {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/livro/lista`,
      {}
    );
  }

  async disableBook(id) {
    return await this.changeBookStatus(id, "inativar");
  }

  async enableBook(id) {
    return await this.changeBookStatus(id, "ativar");
  }

  async changeBookStatus(id, status) {
    const response = await fetch(
      `http://${this.SERVER_URL}/livro/${id}/${status}`,
      {
        method: "POST",
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  async getBook(id) {
    const response = await fetch(
      `http://${this.SERVER_URL}/livro/${id}/busca`,
      {
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  // Empréstimos
  async getAllEmprestimos() {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/emprestimo/lista`,
      {}
    );
  }

  async getAllEmprestimosByClientId(id, status) {
    if (status === ""){status = null}
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/emprestimo/lista/cliente/${id}`,
      {
        "emprestimoStatus":status
      }
    );
  }

  async getEmprestimoById(id) {
    return await this.sendGetRequest(
      `http://${this.SERVER_URL}/emprestimo/${id}/busca`,
      {}
    );
  }

  async postAdiarEmprestimo(id, dataEstendidaDevolucao) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/emprestimo/${id}/adiar`,
      {
        "dataEstendidaDevolucao":dataEstendidaDevolucao
      }
    );
  }

  async postCancelarEmprestimo(id) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/emprestimo/${id}/cancelar`,
      {}
    );
  }

  // Clientes
  async getClienteById(id){
    return await this.sendGetRequest(
      `http://${this.SERVER_URL}/cliente/${id}/busca`
    )
  }

}

export default Base;
