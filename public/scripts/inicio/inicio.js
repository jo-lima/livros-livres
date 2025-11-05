

// Elementos
const authorNameElement = document.querySelector(".author-name")
const authorDescriptionElement = document.querySelector('.author-biography')
const authorQuoteElement = document.querySelector('.author-detail__description')

function renderAuthor(json){
    authorNameElement.textContent = json.nome
    authorDescriptionElement.textContent = json.descricao
    authorQuoteElement.citacao = json.citacao
}



const params =  new URLSearchParams(document.location.search);
let id = params.get("id");

console.log(id)

// renderAuthor(json)

headers = {
    'Access-Control-Allow-Origin' : '*',
    // 'Access-Control-Allow-Credentials': 'true'
}

async function a(){
    const response = await fetch(`http://localhost:6969/autor/${id}/busca` , {headers: headers, method: 'GET'})
    const json = await response.json()
    console.log(json.body)
    renderAuthor(json.body)
}


const json = a()
console.log(json)