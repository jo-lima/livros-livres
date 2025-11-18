import Requests from "./requests.js";
import "./nav-bar.js";

class Base extends Requests {
  constructor() {
    super();

    // Pop-up
    this.popUpOverlay = document.querySelector(".dashboard__popup-overlay");

    // Mensagem
    this.messageElement = document.querySelector(".dashboard__message");
    this.messageTimer;

    // check logged in user type
    this.userType = document.cookie.split('userType=')[1]?.split(';')[0];
    if(this.userType == "CLIENT") {
      // check if client still has permissions of itself
      this.getClient(document.cookie.split('userId=')[1]?.split(';')[0]).then(json => {
        if(json.statusCode != 200){
          // clear all cokies
          const cookies = document.cookie.split("; ");
          for (let cookie of cookies) {
              const name = cookie.split("=").shift();
              document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
          }
          location.reload();
        }
      })
    } else if (this.userType == "FUNCIONARIO") {
      this.getClient(1).then(json => {
        if(json.statusCode != 200){
          // clear all cokies
          const cookies = document.cookie.split("; ");
          for (let cookie of cookies) {
              const name = cookie.split("=").shift();
              document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
          }
          location.reload();
        }
      })
    }

    // Setup dos EventListeners
    this.setUpListeners();
  }

  // Mensagem
  displayMessage(json) {
    this.messageElement.textContent = json.message;
    this.messageElement.style.backgroundColor = json.statusCode == 200 ? "var(--text-color--green--regular)" : "red";

    if (this.messageElement.classList.contains("dashboard__message--hidden")) this.messageElement.classList.remove("dashboard__message--hidden");

    // Para o timeout anterior
    this.messageTimer && clearTimeout(this.messageTimer);

    this.messageTimer = setTimeout(() => {
      this.messageElement.classList.add("dashboard__message--hidden");
    }, 4000);
  }

  // Pop-Up
  showPopUp(popUpClass) {
    document.querySelector(popUpClass).classList.remove("hidden");
    this.popUpOverlay.classList.remove("hidden");
  }

  hidePopUp() {
    const popUps = document.querySelectorAll(".dashboard__popup");
    popUps.forEach((popUp) => popUp.classList.add("hidden"));
    this.popUpOverlay.classList.add("hidden");
  }

  // Métodos úteis
  today() {
    return new Date().toJSON().slice(0, 10).replace(/-/g, "/");
  }

  cleanForm(form) {
    const inputs = form.querySelectorAll("input, textarea, select");
    inputs.forEach((input) => (input.value = ""));
  }

  formDataObject(form) {
    const data = {};
    new FormData(form).forEach((value, key) => (data[key] = value));
    return data;
  }

  // Setup dos EventListeners
  setUpListeners() {
    // Fechar pop-up no espaço de fora
    this.popUpOverlay.addEventListener("click", (event) => {
      if (!event.target.classList.contains("dashboard__popup-overlay")) return;
      this.hidePopUp();
    });

    // Fechar pop-up no botão 'X'
    document
      .querySelectorAll(".dashboard__popup-close")
      .forEach((button) =>
        button.addEventListener("click", () => this.hidePopUp())
      );
  }
}

export default Base;
