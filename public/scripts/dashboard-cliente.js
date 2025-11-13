import DashboardBase from "../scripts/dashboard-base.js";

class DashboardCliente extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.clientsTableElement = document.querySelector(
      ".dashboard__table--clients tbody"
    );
    this.newClientForm = document.querySelector("#new-client-form");
    this.editClientSubmit = document.querySelector("#edit-client-submit");
    this.editClientForm = document.querySelector("#edit-client-form");

    // Execução
    this.initialize();
  }

  renderAllClients(clientData) {
    // TODO - Lidar
    if (clientData.statusCode == 404) {
      this.displayMessage(clientData);
      return;
    }

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
    // TODO - Lidar
    if (clientData.statusCode == 404) {
      this.displayMessage(clientData);
      return;
    }

    // Clientes registrados
    document.querySelector(".dashboard__card--clients-amount").textContent =
      clientData.body.length;
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
    const response = await this.editClient(
      this.editClientSubmit.dataset.idClient,
      body
    );

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
    if (
      target.classList.contains("disable-client-button") ||
      target.classList.contains("enable-client-button")
    ) {
      json = target.classList.contains("disable-client-button")
        ? await this.disableClient(rowId)
        : await this.enableClient(rowId);

      this.displayMessage(json);
      this.listRenderClients();
    }

    // Exibir formulário para editar cliente
    if (target.classList.contains("edit-client-button")) {
      const response = await this.getClient(rowId);

      document.querySelector(".dashboard__popup-input--cpf").value =
        response.body.cpf;
      document.querySelector(".dashboard__popup-input--name").value =
        response.body.nome;
      document.querySelector(".dashboard__popup-input--email").value =
        response.body.email;
      document.querySelector(".dashboard__popup-input--adress").value =
        response.body.endereco;
      document.querySelector(".dashboard__popup-input--phone").value =
        response.body.telefone;
      document.querySelector(".dashboard__popup-input--image").value =
        response.body.imagem;

      this.editClientSubmit.dataset.idClient = rowId;
      this.showPopUp(".dashboard__popup--edit-client");
    }
  }

  setUpClientListeners() {
    document
      .querySelector("#new-client-button")
      .addEventListener("click", () =>
        this.showPopUp(".dashboard__popup--new-client")
      );

    document
      .querySelector("#new-client-submit")
      .addEventListener("click", (event) => this.submitNewClientForm(event));

    this.clientsTableElement.addEventListener("click", (event) =>
      this.handleClientActions(event)
    );

    this.editClientSubmit.addEventListener("click", (event) =>
      this.submitEditClientForm(event)
    );
  }

  async listRenderClients() {
    this.getAllClients().then((json) => {
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
