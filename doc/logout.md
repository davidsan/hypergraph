**GET auth/logout**
----
  Returns an empty JSON if the session was succesfully exited

* **URL**

  /auth/logout?token=`token`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `token=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
  {}
  ``` 
  
* **Error Response:**


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

  `/auth/logout?token=12020d065be4447c9395506cc2278c8f