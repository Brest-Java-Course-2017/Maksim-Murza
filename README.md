# Hardware catalog
### cURL requests to REST:

**get all models:**	    \
*curl -v localhost:8088/models* \
**get model by id:** \
*curl -v localhost:8088/model/id/1* \
**get model by name:** \
*curl -v "localhost:8088/model/name?modelName=Intel+Pentium+G4560+Kabylake"* \
**add new model:** \
*curl -H "Content-Type: application/json" -X POST -d '{"modelId":null,"modelName":"TestName","modelType":"CPU","releaseDate":"2014-09-03"}' -v localhost:8088/model* \
**update model:**    \
*curl -H "Content-Type: application/json" -X PUT -d '{"modelId":"2","modelName":"editedName","modelType":"CPU","releaseDate":"2012-03-03"}' -v localhost:8088/model* \
**delete model:**		    \
*curl -v -X DELETE localhost:8088/model/id/25* \
**get models by period:** \
*curl -v localhost:8088/models/2014-05-05/2016-05-05* \
**get all types:** \
*curl -v localhost:8088/types* \
**get type by id:**			    \
*curl -v localhost:8088/type/id/1* \
**get type by name:**   \
*curl -v "localhost:8088/type/name?typeName=CPU"* \
**add new type:**		      \
*curl -H "Content-Type: application/json" -X POST -d '{"typeName":"newType"}' -v localhost:8088/type* \
**update type:**	      \
*curl -H "Content-Type: application/json" -X PUT -d '{"typeId":"6","typeName":"editedName"}' -v localhost:8088/type* \
**delete type:**  \
*curl -v -X DELETE localhost:8088/type/id/7* \
