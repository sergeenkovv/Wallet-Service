# Wallet-Service

### Application for recording money transactions of players.

## Content
- [Tech stack](#tech-stack)
- [Functionality](#functionality)
- [Database structure](#database-structure)
- [API Endpoints](#api-endpoints)
- [Startup instructions](#startup-instructions)
- [Contact me](#contact-me)

## Tech stack

- Java 17
- Spring Boot 3.2.4
- Spring Data Jpa
- Spring Web
- Spring Security 6.2.3
- Spring Validation
- PostgreSQL
- Hibernate
- Liquibase
- JWT
- Lombok
- MapStruct
- JUnit 5
- AssertJ
- Mockito
- Docker

## Functionality

- Player registration
- Player authorization
- Execution of a credit transaction by a player
- Execution of a debit transaction by a player
- Getting the player's balance
- Getting the player's transaction history

## Database structure

### `player`

| Column   | Type           | Comment                                    |
|----------|----------------|--------------------------------------------|
| id       | BIGSERIAL      | Unique transaction identifier, primary key |
| login    | VARCHAR(64)    | Player login                               |
| password | VARCHAR(256)   | Player password                            |
| balance  | DECIMAL(10, 2) | Player balance                             |

### `transaction`

| Column    | Type           | Comment                                                |
|-----------|----------------|--------------------------------------------------------|
| id        | BIGSERIAL      | Unique transaction identifier, primary key             |
| type      | VARCHAR(24)    | Transaction type                                       |
| amount    | DECIMAL(10, 2) | Transaction value                                      |
| player_id | BIGINT         | Used to communicate with the player table and id field |

## API Endpoints

**POST:** `/api/auth/registration` — Player registration.

```json
{
  "login": "vanya",
  "password": "1234"
}
```
___
**POST:** `/api/auth/authorization` — Player authorization.

```json
{
  "login": "vanya",
  "password": "1234"
}
```
___
**POST:** `/api/players/transactions/credit` — Credit transaction.

```json
{
  "playerLogin": "vanya",
  "amount": "560"
}
```
___
**POST:** `/api/players/transactions/debit` — Debit transaction.

```json
{
  "playerLogin": "vanya",
  "amount": "50"
}
```
___
**GET:** `/api/players/balance?login=vanya` — Get player balance.
___
**GET:** `/api/players/history?login=vanya` — Get player's transaction history.

## Startup instructions
1. Start docker container with database. Run the command in the terminal in the root directory of the
   project: ` docker compose up `.
2. Run the application: ` WalletServiceApplication `.

## Contact me
+ Email: [itproger181920@gmail.com](https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&to=itproger181920@gmail.com) 📬
+ Telegram: [@itproger181920](https://t.me/itproger181920) ✈️
+ LinkedIn: [Ivan Sergeenkov](https://www.linkedin.com/in/ivan-sergeenkov-553419294?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app) 🌊