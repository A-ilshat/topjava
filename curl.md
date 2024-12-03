### **Get one meal:**

```
curl -X GET http://localhost:8080/topjava/rest/meals/100009
```

### **Get all meals:**

```
curl -X GET http://localhost:8080/topjava/rest/meals
```

### **Get meals by dateTime:**

```
curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00&endDate=2020-01-31&endTime=23:00
```

### **Delete meal:**

```
curl -X DELETE http://localhost:8080/topjava/rest/meals/100009
```

### **Update meal:**

```
curl -X PUT http://localhost:8080/topjava/rest/meals/100009 \
-H "Content-Type: application/json" \
-d '{"name": "Updated Meal", "calories": 500, "dateTime": "2024-12-01T12:30:00"}'
```

### **create new meal:**

```
curl -X POST http://localhost:8080/topjava/rest/meals \
-H "Content-Type: application/json" \
-d '{"name": "New Meal", "calories": 500, "dateTime": "2024-12-01T12:30:00"}'
```