**GET user/create**
----
  Returns an empty json if the creation of the user was successful

* **URL**

  /user/create?username=`username`&password=`password`&name=`name`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**   
    
    `username=[string]`
    `password=[string]` 
    `name=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
      {}
  ```
 
* **Error Response:**

  * **Code:** 1 Login Already Taken <br />
    **Content:** 
    
    ```
    {
        "error":"Login Already Taken",
        "code":1
    }
    ```

  
  * **Code:** 400 Bad Request <br />
    **Content:** 
    
    ```
    {
    	"error":"Bad Request",
    	"code":
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

  `/user/create?username=sherlock&password=1234&name=Sherlock Holmes`