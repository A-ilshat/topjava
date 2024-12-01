get: curl -X GET http://localhost:8080/topjava/rest/meals/100009

getAll: curl -X GET http://localhost:8080/topjava/rest/meals

getBetween: curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00&endDate=2020-01-31&endTime=23:00

delete: curl -X DELETE http://localhost:8080/topjava/rest/meals/100009

update: curl -X PUT http://localhost:8080/topjava/rest/meals/100009 \
-H "Content-Type: application/json" \
-d '{"name": "Updated Meal", "calories": 500, "dateTime": "2024-12-01T12:30:00"}'

create: curl -X POST http://localhost:8080/topjava/rest/meals \
-H "Content-Type: application/json" \
-d '{"name": "New Meal", "calories": 500, "dateTime": "2024-12-01T12:30:00"}'

