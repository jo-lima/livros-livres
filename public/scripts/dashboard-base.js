// Pop-up
const popUpElement = document.querySelector(".dashboard__popup-container");

const hidePopUp = () => popUpElement.classList.add("hidden");
const showPopUp = () => popUpElement.classList.remove("hidden");

// Fechar pop-up no botão 'X'
document
  .querySelector(".dashboard__popup-close")
  .addEventListener("click", hidePopUp);

// Fechar pop-up no espaço de fora
popUpElement.addEventListener("click", function (event) {
  if (!event.target.classList.contains("dashboard__popup-container")) return;
  hidePopUp();
});
