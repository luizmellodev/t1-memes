**A aplicação:**
- Java 11 com SpringBoot
- Maven

**Como rodar:**
- Sugerida a utilização da IDE IntelliJ;
- Importar como um projeto Maven;
- Baixar dependências;
- Garantir que nas configurações do projeto esteja configurado Java 11;
- Garantir que um banco de dados Postgres esteja rodando;
- Executar o método Main classe OrientacaoBucalBackendApplication;
- Acessar em http://localhost:8080

**Banco de Dados:**
- No arquivo *main/resources/application.properties* há configuração para banco postgres e a porta da aplicação spring
- Para rodar o local: utilizar o docker compose ou instalar o postgres na sua maquina e garantir passar o porta e usuario correto no application.properties

**Observações:**
- Já adicionadas dependências para uso de: Postgresql, [Lombok][1], [Junit 4.12][2]

[1]: https://projectlombok.org/api/lombok/package-summary.html
[2]: https://junit.org/junit4/javadoc/latest/
