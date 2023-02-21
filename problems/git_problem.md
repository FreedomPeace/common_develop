## question-1

`$ git pull -r
kex_exchange_identification: Connection closed by remote host
Connection closed by 20.205.243.166 port 22
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.`

solution: 修改项目的.git文件夹下的config 配置   
    git@github.com:FreedomPeace/common_develop.git  -》 
                    https://github.com/FreedomPeace/common_develop.git