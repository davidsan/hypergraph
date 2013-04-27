**GET user/show**
----
  Returns a json containing stats about the user

* **URL**

  /user/show?token=`token`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**   
    
    `token=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
  {
      "id": 1,
      "friends_count": 13,
      "statuses_count": 37,
      "followers_count": 42
  }
  ```
 
* **Error Response:**

  
  * **Code:** 400 Bad Request <br />
    **Content:** 
    
    ```
    {
    	"error":"Bad Request",
    	"code":
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

  `/user/show?token=297e2147cf7b48a4acc98e7d78e15f8d`