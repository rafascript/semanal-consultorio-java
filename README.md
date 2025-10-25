# Sistema de Gerenciamento de Consultório Médico

## Descrição
Sistema desenvolvido em Java puro para gerenciamento de pacientes, endereços e consultas. Utiliza banco de dados MySQL para persistência dos dados e possui interface em terminal para interação.

## Funcionalidades
- Cadastro, edição, remoção e listagem de pacientes
- Cadastro, edição, remoção e listagem de endereços vinculados a pacientes
- Cadastro, edição, remoção, confirmação, reagendamento e cancelamento de consultas
- Integração com banco MySQL via JDBC
- Integração com API ViaCEP para preenchimento automático de endereço
- Monitoramento de alterações com triggers de auditoria no banco
- Agenda parametrizável via views no banco de dados

## Estrutura do Projeto
- `model/` - Classes modelo representando as entidades do sistema
- `dao/` - Classes para acesso ao banco via JDBC
- `util/` - Utilitários, incluindo conexão com banco
- `tui/` - Menus em terminal para manipulação dos dados
- `Main.java` - Ponto de entrada da aplicação

## Instalação e Execução
1. Configure um banco MySQL e execute o script `consultorioMedicoJava.sql` para criar as tabelas e triggers.
2. Configure as credenciais do banco na classe `DatabaseConnection.java`.
3. Compile o código Java.
4. Execute a aplicação e interaja via menus.

## Tecnologias Utilizadas
- Java 25+
- MySQL 8+
- JDBC
- API ViaCEP (consulta de CEP)

## Licença
Este projeto é apenas para fins acadêmicos.

---
