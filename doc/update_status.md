**GET status/update**
----
  Returns an empty JSON if the status was successfully updated.

* **URL**

  /status/update?token=`token`&text=`text`

* **Method:**

  `GET`
  
* **URL Params**

   **Required:**
 
   `token=[string]`
   `text=[string]`

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
	
	
  * **Code:** 999 Unexpected Error <br />
    **Content:** 

    ```
    {
    	"error":"Unexpected Error",
    	"code":999
    }
    ```

* **Sample Call:**

  `/status/update?token=12020d065be4447c9395506cc2278c8f&text=good morning`