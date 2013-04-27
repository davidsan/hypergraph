**GET status/destroy**
----
  Returns an empty JSON if the status was succesfully removed

* **URL**

  /status/destroy?token=`token`&id=`id`

* **Method:**

  `GET`
  
* **URL Params**

   **Required:**
   
   `token=[string]`
   `id=[int]`

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
  * **Code:** 403 Forbidden <br />
    **Content:** 
    
    ```
    {
        "error":"Forbidden",
        "code":403
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

  `/status/destroy?token=12020d065be4447c9395506cc2278c8f&id=514cc526da060ebf8d1547aa`