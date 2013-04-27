**GET user/suggest**
----
  Returns a json containing users you might be interested in

* **URL**

  /user/suggest?token=`token`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**   
    
    `token=[string]`

* **Success Response:**

  * **Content:** 
  
  ```
{
    "users": [{
            "id": 7,
            "username": "bond",
            "name": "james bond",
            "id_str": "7"
        }, {
            "id": 10,
            "username": "bot",
            "name": "Robot",
            "id_str": "10"
        }
    ]
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

  `/user/suggest?token=297e2147cf7b48a4acc98e7d78e15f8d`