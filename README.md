# Read Me First
This application is a simple example of how to generate and validate a RSA JWT token using spring boot

Step1:
Initialize the project with required dependencies from pom.xml

Step1: Generate a RSA key pair using the following command (or use an existing one): You can use putty or Git Bash or any tool you like.
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -outform PEM -out public.pem

Step2: Copy the public key to the resources folder of the project

Step3: Run the application and hit the following endpoint to generate a token
http://localhost:8080/generateToken with request body to send claims. scope maps to the authorities in the token.
```json
{
    "sub": "1234567890",
    "name": "John Doe",
    "scope": "create read update delete all"
}
```

Step4: Use attached postman collection src/test/JwtAuthorization.postman_collection.json for your tests




