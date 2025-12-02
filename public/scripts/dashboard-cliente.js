import Base from "./base.js";

class DashboardCliente extends Base {
  constructor() {
    super();

    // Elementos
    this.clientsTableElement = document.querySelector(".dashboard__table--clients tbody");
    // Novo cliente
    this.newClientForm = document.querySelector("#new-client-form");
    this.newClientButton = document.querySelector("#new-client-button");
    // Editar cliente
    this.editClientForm = document.querySelector("#edit-client-form");

    // Execução
    this.initialize();
  }

  renderAllClients(clientData) {
    this.clientsTableElement.innerHTML = "";

    clientData.body.forEach((client) => {
      let buttonHtml;

      if (client.ativo) {
        buttonHtml = `
          <button class="disable-client-button dashboard__action-button">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M18.364 18.364A9 9 0 0 0 5.636 5.636m12.728 12.728A9 9 0 0 1 5.636 5.636m12.728 12.728L5.636 5.636" />
            </svg>
          </button>`;
      } else {
        buttonHtml = `
          <button class="enable-client-button dashboard__action-button">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
            </svg>
          </button>`;
      }

      const clientHtml = `
        <tr data-id="${client.clienteId}">
            <td>${client.cpf}</td>
            <td>${client.nome}</td>
            <td>${client.email}</td>
            <td>${client.endereco}</td>
            <td>${client.telefone}</td>
            <td>${client.senha}</td>
            <td>${client.ativo == true ? "Ativo" : "Inativo"}</td>
            <td>
            <div class="dashboard__action-buttons">
              ${buttonHtml}
              <button class="edit-client-button dashboard__action-button">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                </svg>
              </button>
            </div
          </td>
        </tr>`;

      this.clientsTableElement.insertAdjacentHTML("beforeend", clientHtml);
    });
  }

  updateCards(clientData) {
    // Clientes registrados
    document.querySelector(".dashboard__card--clients-amount").textContent = clientData.body.length;
  }

  async submitNewClientForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.newClientForm);
    const response = await this.createClient(body);

    this.displayMessage(response);
    this.cleanForm(this.newClientForm);
    this.hidePopUp();
    this.listRenderClients();
  }

  async submitEditClientForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.editClientForm);
    const response = await this.editClient(this.editClientForm.dataset.idClient, body);

    this.displayMessage(response);
    this.cleanForm(this.editClientForm);
    this.hidePopUp();
    this.listRenderClients();
  }

  async handleClientActions(event) {
    const target = event.target.closest(".dashboard__action-button");

    if (target == null) return;

    const rowId = target.closest("tr").dataset.id;

    let json;

    // Inativar/Ativar autor
    if (target.classList.contains("disable-client-button") || target.classList.contains("enable-client-button")) {
      json = target.classList.contains("disable-client-button") ? await this.disableClient(rowId) : await this.enableClient(rowId);

      this.displayMessage(json);
      this.listRenderClients();
    }

    // Exibir formulário para editar cliente
    if (target.classList.contains("edit-client-button")) {
      const response = await this.getClient(rowId);

      this.editClientForm.querySelectorAll('input').forEach(field => field.value = response.body[field.name]);

      this.editClientForm.dataset.idClient = rowId;
      this.showPopUp("#edit-client-form-popup");
    }
  }

  setUpClientListeners() {
    this.clientsTableElement.addEventListener("click", event => this.handleClientActions(event));
    // Novo cliente
    this.newClientForm.addEventListener("submit", event => this.submitNewClientForm(event));
    this.newClientButton.addEventListener("click", () => this.showPopUp("#new-client-form-popup"));
    // Editar cliente
    this.editClientForm.addEventListener("submit", event =>this.submitEditClientForm(event));
  }

  async listRenderClients() {

    // Caso usuario NAO esteja logado OU usuario NAO seja um cliente, nao deixa entrar
    if(document.cookie.split('userToken=')[1]?.split(';')[0] == null || document.cookie.split('userId=')[1]?.split(';')[0] != null) {
      document.querySelector(".dashboard__container").innerHTML = "<h1>Você não tem permissão para acessar esta página!</h1>"
      return
    }

    this.getAllClients().then(json => {
    if (json.message == 'Nenhum cliente encontrado.') {
      this.displayMessage(json);
      return;
    }

      this.renderAllClients(json);
      this.updateCards(json);
    });
  }

  async initialize() {
    this.listRenderClients();
    this.setUpClientListeners();
  }
}

const dashboardCliente = new DashboardCliente();
