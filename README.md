# Mutuus TESTE - API

O objetivo do teste era ver a capacidade do candidato de trabalhar com Orientação à Objetos, Manipulação de JSON e utilização de Frameworks Web(como Spring no meu caso).
Minha API é extremamente simples, a qual retorna um Obj. Pokemon contendo as possíveis evoluções(até mesmo as 8 evoluções da Eevee) e mapeia o nível minimo para evolução, sendo aqueles que necessitam items, com valor padrão de 0.

# Porque adotei Spring?
Adotei SPRING por ser a framework a qual a Mutuus já utiliza, pelo que pude notar, e por ser uma das frameworks teoricamente mais robustas e sólidas do mercado com Java, utilizada até mesmo pela Netflix.

# Classes

PokemonController - RestController que mapeia todos Get Requests em relação à /apidatabase/pokemon/{ID} e /apidatabase/pokemon/pokedex/ key FROM e key TO.
Seus métodos principais são getPokemonData, a qual faz todo trabalho mais pesado em relação á manipulação e criação de um novo objeto com os dados da JSON.
Ela utiliza de alguns métodos acessórios, como getEvolutionChain(a qual retorna um objeto EvolutionChain, objeto que mapeia a linha evolutiva) IDParser.parseEvolution(o qual retorna apenas o ID da linha evolutiva, de toda URL).

userService - RestController que mapeia todos os Get Requests em relação ao /userService/pokemon, o qual segue a mesma lógica do PokemonController.
Seus métodos são apenas os esperando um GET REQUEST, e chamam por um RestTemplate, o link da /apidatabase/....

# Classes Auxiliares - Como pensei?

Classes como EvolutionChain, Pokemon, PokemonList, PokemonListInterface, PokemonListPag, Chain.

Meu maior desafio por mapear o JSON que retornava de pokeapi/.../evolution-chain em um objeto.
Minha solução foi criar uma classe dominante - EvolutionChain, com um objeto Chain dentro dela.
Este objeto Chain por sua vez, se instancia recursivamente, assim solucionando o problema das listas de evolução. Evolve_to -> 0 -> ...

PokemonListInterface foi minha solução para conseguir ter 2 tipos de retorno, um a qual tem um valor padrão, a PokeDex por inteira. E outra, apenas o valor especificado entre a diferença do primeiro índice, do segundo(To).

Pokemon foi a classe que utilizei para transformar o JSON de pokeapi/.../pokemon/{id} em Objeto, e contem todas evoluções, nome, e URL.

# Em relação a Performance e Ciclo de Vida.
Tenho pouquíssima experiência em criação de API e arquitetura de software. Tentei fazer uma maneira a qual, não dificultasse tanto a implementação de novos métodos, e funções.
Eu poderia, e deveria talvez ter utilizado mais interfaces para dinamizar a modificação do projeto.
Ponto 1 :  Performance questionável para valores acima de 10~15 itens.
Ponto 2 :  Algumass funções são repetitivas, e portanto desaceleram o código
Ponto 3 : Talvez, o método pelo qual retorno um EvolutionChain poderia ser melhorado, e portanto, daria um grande avanço na performance, visto que ele é chamado a cada item,
e tem uma execução longa.

# Funcionamento

/userService/pokemon/{id} = retorna um JSON com Pokemon, evolução, nome, url e Tipo(Grass, Water, Dragon...)
/userService/pokemon/pokedex/?from=x&to=y ; sendo x > 1 e y < 898 : retorna uma JSON paginada com os pokemons nestes indices. A função chama o link interno para guarda o cache.
O tamanho da lista é baseado na diferença entre x e y.
/userService/pokemon/pokedex/ : as keys não são obrigatórias, e caso não especificadas retornará a pokedex inteira.

# Tempo o qual Dediquei

Comecei no Sábado em torno das 16:00 e fui até 00:40. Dormi pensando como eu faria pra pegar as evoluções.
Acordei Domingo as 7:30, voltei ao projeto 8:30 e acabo de terminar ele em torno de 23:50.
Descansei para almoçar e surfar as 12:00 até 13:00 e 17:00 as 18:30.

Ou seja, em torno de 20 horas em 2 dias.

# Comentários

Gostaria demais de agradecer pela oportunidade de ter um desafio desses. Nunca havia trabalhado com nenhuma API, nunca tinha manipulado JSON, e muito menos com protocolos HTTP, então logo no início quando olhei o teste, achei que seria impossível. 
Reconheço que, pela minha falta de conhecimento em relação aos termos da Ciências da Computação posso ter cometido alguns e/ou vários erros. Estou decidindo mandar o código assim mesmo, porque gosto que vejam como eu trabalho.
E também, que minha arquitetura talvez esteja abaixo do desejado, mas sigo estudando e vou levar esse teste e as ideias dele pra minha rotina de estudos!

Este fora meu primeiro teste técnico com Desenvolvimento, e sinto que saio satisfeito dele.

Abraços,

Lucas Weis Polesello.

# Obrigado Mutuus 

