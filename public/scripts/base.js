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

  // Cliente
  async getAllClients() {
    const response = await fetch(`http://${this.SERVER_URL}/cliente/lista`, {
      headers: {
        token: this.DEBUG_TOKEN,
      },
    });

    return await response.json();
  }

  async createClient(body) {
    return await this.sendPostRequest(
      `http://${this.SERVER_URL}/funcionario/novo-cliente`,
      body
    );
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
}

export default Base;
