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
GET /bountyhunter/category/detail
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

#### 发布任务
```
POST /bountyhunter/assignment/issue
```
参数

```
"name":"root",
"type":101,
"reward":"20.0",
"description":这是一个跑腿任务",
"icon":"http://xxxx.jpg",
"position":"14舍"
```
返回
```
{
    "code":"0",
    "msg":"成功",
    "data":[
        
    ]
}
```

#### 任务分类

```
GET /bountyhunter/category/list
```
参数

```
"categoryType":"101",
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "name": "跑腿",
            "type": 101,
            "assignments": [
                {
                    "id": "1523103556512107696",
                    "name": "取快递",
                    "owner": {
                        "account": "17745140138",
                        "name": "root",
                        "email": "1228537086@qq.com",
                        "detail": {
                            "detailId": "123456",
                            "userPhone": "17745140138",
                            "userAddress": "黑科技",
                            "userIcon": "http://xxx.jpg",
                            "userBirthday": "1998-1-15",
                            "updateTime": 1523098998000
                        }
                    },
                    "reward": 10,
                    "description": "去小区5号楼",
                    "icon": "http://xxx.jpg",
                    "position": "14"
                }
            ]
        }
    ]
}
```