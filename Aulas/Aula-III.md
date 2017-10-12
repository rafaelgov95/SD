# Escalabilidade

## Problemas SD para redes locais. 
### Comunicação sincrona: 
#### Problema  
- A parte que requista o serviço fica bloqueada até a mensagem seja enviada de volta.
  - HTTP Requisição e Resposta
#### Solução
- Requisição assicrona para HTTP 

### Cliente X Servidor
- Tirar responsabilidade do servidor.

### Técnicas de distribuição
    - Dividr o SD em componentes, em partes e distribuir estas partes pelo sistema
        - Ex: DNS (Domain Name Server) 
            - Sistema de Nomes de Domínio da Internet
            - Organizada em hierarquia em uma árvore
            -  Dividade em zonas sem sobreposçao
            - Os nomes são manipulados por um único servidor de nomes.
            - Com objetivo de resolver um endereço de rede do hospedeiro.
    - Replicar os componentes dos servidores.
        - Cache (Forma especial de replicação)
            - Cópia de recurso próxima ao cliente
            - Acontece Sob demanda.
        - Já a replicação pode ser planejada antecipadamente.
        - Desvantagens:
            - se uma for modificada, logo ficará diferente das outras.
                - resultando em problemas de consistência.
                - Até que ponto são toleráveis as inconsistências
                - (mim?, seg?)
                    - Depende da utilização do recurso
            - Forte consistência
                - Atualização deve ser imediatamente propagada para as outras cópias.
                    - Atualização ao mesmo tempo: em ordem.
