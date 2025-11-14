class Requests {
  SERVER_URL = "localhost:6969";
  DEBUG_TOKEN = "debug";
  USER_TOKEN = document.cookie.split('tokenUser=')[1]?.split(';')[0];

  // Base dos requests
  async sendPostRequest(url, body) {
    const response = await fetch(url, {
      method: "POST",
      body: JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
        token: this.USER_TOKEN,
      },
    });

    return await response.json();
  }

  async sendGetRequest(url) {
    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        token: this.USER_TOKEN,
      },
    });

    return await response.json();
  }

  // Request do autor
  async getAllAuthors() {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/autor/lista`);
  }

  async createAuthor(body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/autor/novo`, body);
  }

  async editAuthor(id, body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/autor/${id}/atualizar`, body);
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

  async disableAuthor(id) {
    return await this.changeAuthorStatus(id, "inativar");
  }

  async enableAuthor(id) {
    return await this.changeAuthorStatus(id, "ativar");
  }

  // Requests do livro
  async createBook(body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/livro/novo`, body);
  }

  async editBook(id, body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/livro/${id}/atualizar`, body);
  }

  async getAllBooks() {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/livro/lista`, {});
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

  async disableBook(id) {
    return await this.changeBookStatus(id, "inativar");
  }

  async enableBook(id) {
    return await this.changeBookStatus(id, "ativar");
  }

  // Cliente
  async createClient(body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/funcionario/novo-cliente`, body);
  }

  async editClient(id, body) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/cliente/${id}/atualizar`, body);
  }

  async getClient(id) {
    const response = await fetch(
      `http://${this.SERVER_URL}/cliente/${id}/busca`,
      {
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  async getAllClients() {
    return await this.sendGetRequest(`http://${this.SERVER_URL}/cliente/lista`, {});
  }

  async changeClientStatus(id, status) {
    const response = await fetch(
      `http://${this.SERVER_URL}/cliente/${id}/${status}`,
      {
        method: "POST",
        headers: {
          token: this.DEBUG_TOKEN,
        },
      }
    );

    return await response.json();
  }

  async disableClient(id) {
    return await this.changeClientStatus(id, "inativar");
  }

  async enableClient(id) {
    return await this.changeClientStatus(id, "ativar");
  }

  // Empréstimos
  async getAllEmprestimos() {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/emprestimo/lista`, {});
  }

  async getAllEmprestimosByClientId(id, status) {
    if (status === ""){status = null}
    return await this.sendPostRequest(`http://${this.SERVER_URL}/emprestimo/lista/cliente/${id}`, {"emprestimoStatus":status});
  }

  async getEmprestimoById(id) {
    return await this.sendGetRequest(`http://${this.SERVER_URL}/emprestimo/${id}/busca`, {});
  }

  async postAdiarEmprestimo(id, dataEstendidaDevolucao) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/emprestimo/${id}/adiar`, {"dataEstendidaDevolucao":dataEstendidaDevolucao});
  }

  async postCancelarEmprestimo(id) {
    return await this.sendPostRequest(`http://${this.SERVER_URL}/emprestimo/${id}/cancelar`, {});
  }

  // Clientes
  async getClienteById(id){
    return await this.sendGetRequest(`http://${this.SERVER_URL}/cliente/${id}/busca`)
  }
}

export default Requests;
