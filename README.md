# Chat Web App - https://am-shopwebapp.herokuapp.com/
<p> Features RESTful back-end with stateless authentication using JWT tokens and custom filters written in Java & Spring</p>
<p>The front-end was written in <strong>JavaScript</strong>, using the <strong>React.js</strong> framework.</p>
<p>Fully encrypted user passwords using the bcrypt hashing function provided by Spring Security</p>
<p>Almost real-time message refresh(no need for user refresh)</p>
<p>The web app was deployed to <strong>Heroku</strong> as two separate projects that communicate with each other securely via HTTPS</p>
<p>This GitHub repository contains the local version of the app</p>

# Back-end endpoints
<p> <strong>GET</strong>  /api/user -- returns all users(returns 401 if unauthenticated)</p>
<p><strong>GET</strong> /api/user/test -- tests current user(returns 401 if unauthenticated)</p>
<p><strong>POST</strong> /api/user -- registers a new user(returns error if it already exists)</p>
<p><strong>POST</strong> /api/login - logins user</p>
<p><strong>POST</strong> /api/message -- sends a message</p>
<p><strong>GET</strong>  /api/message (Query parameters : senderName & receiverName) -- returns conversation between 2 users(401 if unauthenticated,
					    403 if someone not part of the conversaation tries to access it)</p>

# App layout
![image](https://user-images.githubusercontent.com/14853367/109384904-a9beac00-78f8-11eb-8511-fe7fe264989a.jpeg)
![image](https://user-images.githubusercontent.com/14853367/109385008-5436cf00-78f9-11eb-91d4-c6fb467048a2.jpeg)
