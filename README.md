# product

product search

* environment
  * openjdk 11
  * docker
  * maven
* How to Build
  * mvn clean install docker:build
* How to run
  * 运行 docker run -p 8080:8080 products:0.0.1 或者运行 docker-compose -f  install.yaml up
  * http://localhost:8080/all
  * http://localhost:8080/query/三
* add/insert data
```
curl -X POST \
'http://124.221.74.86:9200/db/_doc/2?pretty=true' \
-H 'Content-Type: application/json' \
-d '{"content":"商品搜索"}'
```
