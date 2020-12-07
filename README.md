# Desafio Técnico Generation.org #


😀 Thiago Ribeiro Filadelfo - thiago@arrayof.io  
📅 Entregue em 04/12/2020

---

## Minhas considerações

Para a execução deste desafio técnico foram feitas as seguintes escolhas:

- [x] Desenvolvi a aplicação utilizando [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [x] Desenvolvi a API RESTful utilizando o framework [Spring Boot](https://spring.io/projects/spring-boot) na sua versão 2.4.1-SNAPSHOT.
    - Adotei o gerenciador de tarefas Gradle por estar mais ambientado por conta do Android, mas o Maven também não mudaria em nada essa aplicação.
- [x] Para armazenamento dos dados adotei um banco de dados [MySQL 5.7](https://dev.mysql.com/downloads/mysql/5.7.html) proposto pelo desafio.
- [x] Para os testes unitário utilizei o [JUnit](https://junit.org/junit5).
    - Deixei um script (postman_collection.json, para local ou [remoto](https://www.getpostman.com/collections/57e979be4a54590c6a2d)) do aplicativo [Postman](https://www.postman.com) que utilizei para testar
- [x] Optei por usar JPA e Hibernate como implementador desta interface ORM:
    - Tive um supresa quanto a mapeamento reverso (one to many e many to one), que o objeto estava realizando uma [recursividade](https://www.youtube.com/watch?v=oxsVZSlJfM4), que tirou um pouco do tempo.
- Montei uma imagem Docker para a execução da aplicação
- Observações / Melhorias anotadas:
    1. Paginação para a listagem dos dados.
    2. Ainda no mesmo assunto de paginação, remodelar o retorno para que seja melhorado o desempenho, pensando e um sistema com grande quantidade de registro e dispositivos com conexão limitada e performace do
       sistema.
    3. Isolar melhor as partes do sistema, criando mais camadas como por exemplo: (controller - service - repositório), essa abordagem se faz mais presente quando o sistema é manipulado por equipes maiores e
       há uma separação de frentes que trabalha especificamente sob um assunto específico dentro do servidor.
    4. No mesmo assunto isolar, implementar o padrão DTO (Data Transfer Object), para expor somente os dados necessários, bastante interessante quando pensamos em LGPD e também trafegar somente informações
       relevanes aos clientes que consome o serviço.
    5. Na visão DER realizar a normalização das tabelas (3FN).
- De fato, me diverti bastante e me fez aumentar bastante conceitos novos.


## Como executar?

Aqui estão as instruções de como colocar o serviço em execução:



💻 Caso opte por executar via **container** os comandos são:

_Servidor RESTful:_

```
docker image build -f Dockerfile -t trfiladelfo . 
docker run --rm --name servidor -p 8080:8080 trfiladelfo
```



💻 Caso opte por executar via **console** os comandos são:

_Servidor RESTful:_

```
# Preparar o ambiente

docker-compose up

# Instalar Java SDK 11

java -jar desafio-0.0.1-SNAPSHOT

```


## Como testar?

Após colocar o serviço em execução, pode ser feita as chamadas conforme essa documentação:

🕸 **Documentação da API RESTful**

https://documenter.getpostman.com/view/11527/TVmPBHCA#5a617a1a-ff74-4bce-8c3d-2f247152b5a2



## Obrigado!

Agradeço muito pela oportunidade e ficarei muito grato pelo retorno de vocês
acerca da minha execução independentemente do resultado final, isso nos engrandece
como pessoa e mais ainda como um melhoramento profissional.



### Material consultado ###

[**Spring Boot DevTools**](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#using-boot-devtools)

[**Spring Data JPA**](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)

[**Spring Web**](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)

[**Baeldung**](https://www.baeldung.com/rest-with-spring-series)

