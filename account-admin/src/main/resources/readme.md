# 接口详细
## /user/login.do
**描述:** 用户登陆 取得Token  
**请求类型:** POST  
**请求头设置:** 
> Content-Type   application/json  

**请求参数:**  
> {"username": "xxx", "password": "xxx"}  

**请求参数描述:**  
> username  string 用户名  长度<32 任意字符 唯一  
> password  string 密码    公钥加密后进行Base64编码  

**公钥:** 
>MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLBQvE8TmoqojcIvjxVFjFOWDqpZc04R8kINo9u1YsNATQwgVANr1cU0f7YBo45bkgmqozMx62gO2bUIp77Q9I0e8MKpiFKrZ5YJAwIutqUxdwADZ7i0ZkRnV1/0MvMe0/tUus5uNteUfot6DMDORTSZK/s9CH9Z1bbhLKMchp4wIDAQAB  

**请求结果:**  
>{"code":200,"data":"","msg":""}  

**请求结果描述:**  
> code 请求code  
> data  String  token  
> msg   String  请求出错信息  

**Demo:** 
+ 发送登陆请求:  
> http://localhost:8011/admin/login.do  
> {"username": "dw_chenglei", "password": "MDcxNGM1MTAzZDcwMzQ0YTY5NmIzYjgzYjFkNmE2MTAwYjE0ZTBkMWQwNmJjZGU2ZWU0YTY0OTIwMTg5YTc0MDk0NDIzMWZmNmVjMzkxYTFlYzMzODBjMGQzNGE0NGUwMTFmYjA5ZWM1OGI4YjE4MmFkMjc0YzZiZmMwMzVmM2NkMzViM2E4NmU3N2E1YmM0OWNiYzVhMzg0ZjIzMjgyNDEwMTQ5OTI3YzIwYzVhMmI4NDE2ZDljNDE5N2QzYmZjNjJmZmI4NzkwMDY5ZTVhYzM1YjAxMWVmZWQ4ZjE1MmI2MmM2M2VkMTViYmYxYzViZjQ0ZTMwZGU2MjA2YTZiZA=="}  
> 用户密码明文是: 123456  
+ 成功的请求结果:  
> {"code":200,"data":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJvcmcuYWNjb3VudC5jbCIsInN1YiI6ImR3X2NoZW5nbGVpIiwiaWF0IjoxNjA0NjQzMjU2LCJleHAiOjE2MDQ2NDUwNTZ9.luXUfgP6WLSjty4b6KFjumWBayPGvoCdC_v1OfHQmLrgSxJMymJxq8okVlgT8vERbGyAJVa2skh2VpahMuZ_OQ","msg":""}  

## 其它请求均需要权限才能正常访问 否则403
1. application.type=win 用于取消权限check  
2. 携带权限  
> 设置请求头  
> Authorization
> 内容为: account ${TOKEN}

## /admin/user/add.do
> 1. 添加用户信息
> 2. 使用于管理员添加用户的信息, 用户注册时调用
> 3. 请求为post请求, 参数为username和password
> 4. username 必须 字符任意 长度 < 32 不能是被使用过的
> 5. password 必须 对明文使用公钥加密并且base64编码 明文任意
> 6. 添加成功data会为true否则为false其它错误看msg

## /admin/user/update.do
> 1. 修改用户的信息
> 2. 对于用户来说修改自己的密码 对于管理员来说可以修改其状态等 其它参数用来更多的管理用户
> 3. 请求为put请求, 参数为 username oldPassword password enable expired locked credentialsExpired
> 4. username 必须 字符任意 长度 < 32 不能是被使用过的 其它参数均可为null
> 5. 当修改密码时 需要指定旧密码 oldPassword和新密码 password 密码是对明文使用公钥加密并且base64编码 明文任意
> 6. enable 用户是否可用  expired 用户是否过期 locked 用户是否被锁 credentialsExpired 用户凭证是否过期  true 是  false否 这些参数都是boolean类型
> 7. 修改成功data会为true否则为false其它错误看msg

## /admin/user/delete/{username}.do
> 1. 根据用户名 删除用户信息
> 2. 管理员操作使用
> 3. 请求为delete请求, 参数为 username
> 4. 第一次删除 只是修改用户状态为不可用  第二次删除才会从表中删除
> 5. 删除成功data会为true否则为false其它错误看msg
