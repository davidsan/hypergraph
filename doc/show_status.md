**GET status/show**
----
  Returns a JSON which contains the status

* **URL**

  /status/show?id=`id`

* **Method:**

  `GET`
  
* **URL Params**

   **Required:**
 
   `id=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
  {
      "result": {
          "text": "\"hello world\"",
          "_id": "514cc526da060ebf8d1547aa",
          "token": "12020d065be4447c9395506cc2278c8f",
          "author_username": "sherlock",
          "created_at": "Sun Apr 4 12:00:00 CET 1965",
          "author_id": 1 "author_id_str": "1"
      }
  }
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

  `/status/show?id=514cc526da060ebf8d1547aa`