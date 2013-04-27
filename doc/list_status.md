**GET status/list**
----
  Returns a json containing the statuses of an user

* **URL**

  /status/list?token=`token`&id=`id`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**   
    
    `token=[string]`
    `id=[int]`
    
* **Success Response:**

  * **Content:** 
  
  ```
{
    "contacts_only": 0,
    "author": "2",
    "results": [{
        "author": {
            "author_username": "sherlock",
            "author_name": "Sherlock Holmes",
            "author_id_str": "2",
            "author_id": 2,
            "contact": false
        },
        "text": "You know my methods, Watson.",
        "_id": "51730e823004bdb8c037147e",
        "score": 0,
        "created_at": 1366494850153
    }],
    "query": "",
    "date": 1366654345407
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


  * **Code:** 999 Unexpected Error <br />
    **Content:** 

    ```
    {
    	"error":"Unexpected Error",
    	"code":999
    }
	```
* **Sample Call:**

  `/status/list?token=297e2147cf7b48a4acc98e7d78e15f8d&id=2`