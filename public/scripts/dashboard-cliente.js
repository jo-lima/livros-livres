import DashboardBase from "../scripts/dashboard-base.js";

class DashboardCliente extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.clientsTableElement = document.querySelector(
      ".dashboard__table--clients tbody"
    );
    this.newClientForm = document.querySelector("#new-client-form");

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
      const clientHtml = `
        <tr data-id="${client.clienteId}">
            <td>${client.cpf}</td>
            <td>${client.nome}</td>
            <td>${client.email}</td>
            <td>${client.endereco}</td>
            <td>${client.telefone}</td>
            <td>${client.senha}</td>
            <td>${client.ativo == true ? "Ativo" : "Inativo"}</td>
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

  setUpClientListeners() {
    document
      .querySelector("#new-client-button")
      .addEventListener("click", () =>
        this.showPopUp(".dashboard__popup--new-client")
      );

    document
      .querySelector("#new-client-submit")
      .addEventListener("click", (event) => this.submitNewClientForm(event));
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
