**GET auth/login**
----
  Returns a JSON which contains the id, the token and the screen of the user if the authentification was successful

* **URL**

  /auth/login?username=`username`&password=`password`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `username=[string]`
   `password=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
  {
      "id": [id],
      "token": [token],
      "username": [username]
  }
  ``` 
  
* **Error Response:**

  * **Code:** 1 Account Not Found <br />
    **Content:** 
    
    ```
    {
        "error":"Account Not Found",
        "code":1
    }
    ```

  * **Code:** 400 Bad Request <br />
    **Content:** 
    
    ```
    {
        "error":"Bad Request",
        "code":400
    }
    ```
	
	
  * **Code:** 401 Unauthorized <br />
    **Content:** 
    
    ```
    {
        "error":"Unauthorized",
        "code":401
    }
    ```
	
  * **Code:** 999 Unexpected Error <br />
    **Content:** 

    ```
    {
        "error":"Unexpected Error",
        "code":999
    }
    ```

* **Sample Call:**

  `/auth/login?username=sherlock&password=1234`