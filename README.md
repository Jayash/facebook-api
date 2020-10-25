# Facebook-API
Application URL - https://friend-facebook.herokuapp.com

# Rest APIs

| Method | URL | Description |
| ------ | ------ | ------ |
| POST | /api/users/signup | To register user |
| GET | /api/users | To get all the users |
| POST | /api/profiles/create | To create user profile |
| POST | /api/profiles/{profile ID}/addFriend/{friend profile ID} | To add friend |
| POST | /api/profiles/{profile ID}/unfriend/{friend profile ID} | To unfriend |
| GET | /api/profiles/{profile ID}/friends | To list all friends |
| GET | /api/profiles/{profile ID}/friends/{distance} | To list all friends within distance |

| Method | JSON body |
| ------ | ------ |
| /api/users/signup | { "username" : "username", "password" : "password"} |
| /api/profiles/create | {"firstname" : "name","lastname" : "name", "address" : "address", "primaryPhone" : "123456789","gender" : "MALE", "user" :   10 } |

# Database Design

![image](https://user-images.githubusercontent.com/7610065/97116663-9f12c100-1724-11eb-974f-3349ce4a2cf0.png)


