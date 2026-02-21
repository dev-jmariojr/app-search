# Search API
API para pesquisa de notícias de assuntos diversos, por meio de integração com a API NewsAPI, que fornece notícias de várias fontes confiáveis.

# Endpoints
## GET /search
### Descrição
Este endpoint permite realizar uma busca por notícias com base em um termo de pesquisa fornecido. Ele retorna uma lista de notícias relevantes que correspondem ao termo de pesquisa.
### Parâmetros de Consulta
* `subject` (obrigatório): O termo de pesquisa para o qual as notícias devem ser buscadas. Exemplo: `Carnaval`, `Lula`, `Trump`. 
* `startDate` (opcional): A data de início para filtrar as notícias. Formato: `YYYY-MM-DD`. Exemplo: `2025-12-01`.
* `endDate` (opcional): A data de término para filtrar as notícias. Formato: `YYYY-MM-DD`. Exemplo: `2025-12-31`.

### Resposta
A resposta é uma lista de objetos JSON, onde cada objeto representa uma notícia. Cada notícia contém os seguintes campos:
* `title`: O título da notícia.
* `description`: Uma breve descrição da notícia.
* `url`: O link para a notícia original.
* `publishedAt`: A data e hora em que a notícia foi publicada.
* `source`: O nome da fonte da notícia.
* `author`: O nome do autor da notícia (se disponível).
* `content`: O conteúdo completo da notícia (se disponível).
* `urlToImage`: O link para a imagem associada à notícia (se disponível).


### Tecnologias Utilizadas
* Java 17
* Spring Boot
* Maven
* Feign Client

### Execução (Ambiente Local)
Para executar a API, siga os passos abaixo:
1. Clone o repositório do projeto.
2. Navegue até o diretório do projeto.
3. Execute o comando `mvn spring-boot:run` para iniciar a aplicação, com parâmetros de ambiente configurados para acessar a API NewsAPI.(ATENÇÃO: editar o `application.yml` com o valor de app.newsapi.apiKey).
4. A API estará disponível em `http://localhost:8989`.
5. Faça uma requisição GET para o endpoint `/search` com os parâmetros de consulta desejados para obter as notícias.

### Observações
* Certifique-se de ter uma chave de API válida para acessar a NewsAPI, e configure-la corretamente no arquivo `application.yml` para que a integração funcione corretamente.
* A API pode retornar um grande número de resultados dependendo do termo de pesquisa e do período especificado, portanto, é recomendado usar filtros de data para refinar os resultados.