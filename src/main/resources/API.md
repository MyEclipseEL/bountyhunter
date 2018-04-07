# API

###任务列表
```
 GET /bountyhunter/assignment/list
 ```
 参数
 
 ```
 无
 ```
 
 返回
 
```
{
    "code" : 0,
    "msg"  : 成功,
    "data" :[
          {
              "name" : "跑腿",
               "type" : 101,
               "assignment":[
                    {
                        "id":"123456",
                        "name":"取快递",
                        "owner":[
                            {
                                "id":"12345678911"
                                "name": "hunter"
                             }
                        ],
                        "reward":25.5,
                        "description":"一个跑腿的任务",
                        "icon":http://xxx.jpg,
                        "position":"松北区黑科技是十四舍"
                        
                    }
              ]
              
              "name" : "XXX"
              
          }
    ]
}

```

### 任务详情列
```
GET /bountyhunter/assignment/detail
```
参数
```
"accountId":"12345678911"
"assignmentId":"123456"
```
返回

```
{
    "code":0,
    "msg":"成功".
    "data":[
        {
            "id":"123456",
            "name":"取快递",
            "owner":[
                {
                    "id":"12345678911",
                    "name": "hunter",
                    "email":?
                    "detail":[
                        {
                            "icon":"http://xxx.jpg",
                        }
                    ],
                }
            ],
            "reward":25.5,
            "description":"一个跑腿的任务",
            "icon":"http://xxx.jpg",
            "position":"松北区黑科技是十四舍"
        }
    
    ]


}
```