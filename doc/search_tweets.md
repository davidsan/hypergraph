**GET search/tweets**
----
  Returns a JSON which contains the result of the query

* **URL**

  /search/tweets?token=`[token]`&query=`[query]`&friends=`{0,1}`

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `friends={0,1}`
   
   **Optional:**
   
   `token=[string]`
   `query=[string]`
   

* **Success Response:**

  * **Content:**
  
  ```
{
    "contacts_only": 0,
    "author": "",
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
    }, {
        "author": {
            "author_username": "david",
            "author_name": "David San",
            "author_id_str": "1",
            "author_id": 1,
            "contact": false
        },
        "text": "just setting up my twttr",
        "_id": "51730ced30048c53ee5c130f",
        "score": 0,
        "created_at": 1366494445976
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
  
Show all tweets from everyone :
  
  `/search/tweets?friends=0`
  
Show all tweets from your friends :
  
  `/search/tweets?token=12020d065be4447c9395506cc2278c8f&query=&friends=1`
  

Show all tweets with the word "books" :

  `/search/tweets?query=books&friends=0`
  
Show all tweets from your friends with the word "books" :

  `/search/tweets?token=12020d065be4447c9395506cc2278c8f&query=books&friends=1`
  
