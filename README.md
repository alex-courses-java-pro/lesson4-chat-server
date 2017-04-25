# Chat server

This is backend of chat made with Java servlets.





### Authentication

All requests have to include basic authorization header. "Authorization":"Basic base64encodedString"

### Endpoints

```
GET /chat?from=n 
```
>Retrieving messages for user from n index

```
POST /chat
```
>Accepting json: {"name":"yourRoomName"} where yourRoomName - room you would like to create or join(if its already exist).
```
PUT /chat
```
>Accepting json: {"date": "formated date","from":"sender","to":"receiver","text":"message"}
receiver should be "all" if its regular message to chat
receiver should be "username" if its private message to user with name "username"
```
GET /status?name=username
```
>Retrieving status of user with name "username" 
```
PUT /status
```
>Accepting json: {"status":"onlineStatus"}. 
Where onlineStatus could only be: OFFLINE, ONLINE, AFK.
```
GET /users
```
>Retreiving user's names list
