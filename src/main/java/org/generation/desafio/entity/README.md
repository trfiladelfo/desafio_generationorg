# Finalidade

Este pacote concentra todas classes que são uma representação da tabela do banco de dados, que chamamos de entidades.

# Quais APIs atua nestas entidades?

Visualmente temos as anotações da interface da JPA (Java Persistente API) que estão definidadas pela JSR 338. Toda interface requer uma implementação das funções, ou seja o funcionamento interno, portanto a implementação escolhida para a interface JPA é o Hibernate.

O Hibertante é um framework ORM (Object Relacional Mapping), mapeamento objeto relacional; ele que faz a mágica da representação de uma classe para uma entidade (tabela do banco de dados).
Não existe somente ele, mas o Hibernate é o mais conhecido e amplamente usado, temos outro por exemplo: Sormula, Eclipselink e etc.

# É obrigado usar?

Não é obrigatório, mas diminue bem a carga de desenvolvimento, deixa para o programador o foco somente em codificar as regras de negócio.

Podemos usar JDBC, que é a coração de todos os ORM, basicamente é realizar os selects na forma mais pura possivel.
Há um cenário também que podemos utilizar servidores de aplicação, por exemplo: Glassfish e JBoss, esses servidores diferentemente dos webcontainer oferece para o desenvolvedor algumas facilidades maiores tais como tem dentro dele um gerenciador de pool de conexão com a base dados, compartilhando com todas as aplicações nele hospedado.

Neste exemplo vou me concentrar em um servidor simples (webcontainer) e utilizar o JPA e a implemntação do Hibernate
