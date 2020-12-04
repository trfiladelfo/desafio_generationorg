# Finalidade

Este pacote concentra todas as interfaces para acessar os dados no banco de dados.

# Mágica
Com a adoção do padrão JPA (JSR 335) para persistencia de objetos, ou seja estamos utilizando 
uma técnica chamada ORM (Object Relational Mapper), que faz a relação de objetos com os dados 
que vamos persistir em uma tabela do banco de dados.

Ao escolher puro e simplesmente o JPA não acontece nada, o que a JPA fornece é a interface 
comum mas quem o implementa que faz a mágica acontecer, neste caso é o Hibernate que consegue 
interpretar de acordo com parametrizações quais os comando SQL (Structured Query Language) aquele
banco possui, se formor comparar ele é aquele interprete de libras em eventos que tem uma preocupação 
com acessibilidade.

# Existe só hibernate
Não, o hibernate é o mais famoso no mercado, mas existem outros por exemplo, eclipselink.   