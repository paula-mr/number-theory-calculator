# number-theory-calculator

Projeto para a matéria de Testes de Software da UFMG

## Membros

O grupo deste trabalho é composto pelos seguintes membros:

- Luiz Augusto Dias Berto
- Matheus Ibrahim
- Paula Mara Ribeiro
- Victor Gabriel Ferreira Moraes

## Tecnologias

O sistema foi desenvolvido por meio da linguagem de programação `Java`, na sua `versão 14`, em conjunto com o framework `Spring Boot` para facilitar a configuração do projeto. Além disso, foi utilizado o `Maven` como ferramenta para automatização do build e gerenciamento das dependencias. Por fim, o projeto utiliza o framework `JUnit` para realização dos testes automatizados.
O programa criado implementa diferentes "calculadoras", cada uma com uma função específica. As calculadoras são: 
 - Fibonacci, que recebe a posição *N* na sequência de Fibonacci(1, 1, 2, 3, 5...) e retorna qual o número que se encontra naquela posição;
 - Fibonorial, que funciona de forma similar à calculadora de Fibonacci, mas para a sequência de Fibonorial, que calcula a sequência como a posição *N* recebendo o produto dos *N* primeiros elementos da sequência de Fibonacci;
 - Triangle, que recebe três números *a, b e c*, e define se os três números são válidos para a construção de um triângulo;
 - Unique Digit, que recebe um número *N* e um número *K*, e calcula o valor da Raíz Digital de *N*, que é calculado a partir de um processo de soma iterativo, sendo realizadas *K* iterações para calcular o valor.