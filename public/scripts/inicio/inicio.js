const json = {
    nome:      "Luca Maia",
    descricao: "feliz :D",
    citacao:   "kpwqon dfbbfe",
    atio:      true
}

// Elementos
const authorNameElement = document.querySelector(".author-name")
const authorDescriptionElement = document.querySelector('.author-biography')
const authorQuoteElement = document.querySelector('.author-detail__descriptione')

function renderAuthor(json){
    authorNameElement.textContent = json.nome
    authorDescriptionElement.textContent = json.descricao
    authorQuoteElement.citacao = json.citacao
}


renderAuthor(json)