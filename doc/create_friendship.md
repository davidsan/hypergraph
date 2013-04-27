**GET friendship/create**
----
  Returns an empty JSON if the friendship was successfully created

* **URL**

  /friendship/create?token=`token`&requesteeId=`requesteeId`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `token=[string]`
   `requesteeId=[int]`

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

  `/friendship/create?token=12020d065be4447c9395506cc2278c8f&requesteeId=1`