# Registration Service

The Registration Service is an API that handles user registration with admin approval. It provides endpoints for creating registration tokens, retrieving and managing registration requests, and user authentication.

---

## Admin endpoints

### Create Registration Token

`POST /registration/requests`

Creates a new registration token.

#### Response

- Status Code: 200 OK
- Response Body:
```json
{
  "registrationToken": "some registration token"
}
```
### Retrieves a list of requests that are pending a decision.

`GET /registration/requests`

#### Response

- Status Code: 200 OK
- Response Body:
```json
[
  {
    "registrationToken": "some token",
    "dateRequest": "some date request",
    "userEmail": "some email",
    "decision": "WAITING",
    "dateDecision": "some date decision"
  },
  ...
]
```

### Retrieves all requests

`GET /registration/requests/all`

#### Response

- Status Code: 200 OK
- Response Body:
```json
[
  {
    "registrationToken": "some token",
    "dateRequest": "some date request",
    "userEmail": "some email",
    "decision": "WAITING/ACCEPT/DECLINE",
    "dateDecision": "some date decision"
  },
  ...
]
```
### Retrieves a request by its token.

`GET /registration/requests/{registrationToken}`

#### Response

- Status Code: 200 OK if the request is found
- Status Code: 404 Not Found if the request is not found
- Response Body:
```json
{
  "registrationToken": "some token",
  "dateRequest": "some date request",
  "userEmail": "some email",
  "decision": "WAITING/ACCEPT/DECLINE",
  "dateDecision": "some date decision"
}
```
### Accept Request

`PUT /registration/requests/{registrationToken}`

#### Response

- Status Code: 200 OK if the request is successfully accepted
- Status Code: 404 Not Found if the request is not found or a decision has already been made
- Response Body:
```json
{
  "registrationToken": "some token",
  "dateRequest": "some date request",
  "userEmail": "some email",
  "decision": "ACCEPT",
  "dateDecision": "some date decision"
}
```
  
### Decline Request

`DELETE /registration/requests/{registrationToken}`

#### Response

- Status Code: 200 OK if the request is successfully declined
- Status Code: 404 Not Found if the request is not found or a decision has already been made
- Response Body:
```json
{
  "registrationToken": "some token",
  "dateRequest": "some date request",
  "userEmail": "some email",
  "decision": "DECLINE",
  "dateDecision": "some date decision"
}
```
---

## User endpoints

### Authentication

`POST /login`

#### Request

- Request Body: JSON object with the `email` and `password` fields.

#### Response

- Status Code: 200 OK if the authentication is successful
- Status Code: 401 Unauthorized if the authentication fails

### Verifies the authentication status and role of the user.

`GET /registration/test`

#### Response

- Status Code: 200 OK if the authentication is successful
- Status Code: 401 Unauthorized if the authentication fails
- Response body for role USER:
```json
{
  "message": "Hello, <user email>"
}
```
- Admin response body for role ADMIN:
```json
{
  "message": "Hello, ADMIN"
}
```
---
## Anonymous endpoints

### Registration

`POST /registration`

- Response Body:
```json
{
  "registrationToken": "some token", 
  "userEmail": "email",
  "password": "password" 
}
```

#### Response

- Status Code: 200 OK if the registration is successful
- Status Code: 404 Not Found if the registration token is invalid

### Retrieves the decision for a request by its token

`GET /registration/{registrationToken}`

#### Response

- Status Code: 200 OK if the request is found
- Status Code: 404 Not Found if the request is not found
- Response Body:
```json
{
  "registrationToken": "some token",
  "dateRequest": "some date request",
  "userEmail": "some email",
  "decision": "WAITING/ACCEPT/DECLINE",
  "dateDecision": "some date decision"
}
```