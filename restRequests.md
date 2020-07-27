##Requests for Postman
{
	"info": {
		"_postman_id": "09a2848b-189a-4d81-b9a1-2cfee01dbf90",
		"name": "TopJava Meals RestAPI",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "getAllMeals",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8080/topjava/rest/meals",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals"
					]
				},
				"description": "gets list of all meals"
			},
			"response": []
		},
		{
			"name": "getMealById",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8080/topjava/rest/meals/{{meal_id}}",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals",
						"{{meal_id}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "createMealForLoggedInUser",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n  \"dateTime\" : \"2016-03-16T13:56:39.492\",\r\n  \"description\":\"newMeal\",\r\n  \"calories\":500\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "localhost:8080/topjava/rest/meals",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals"
					]
				}
			},
			"response": []
		},
		{
			"name": "deleteMealById",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "localhost:8080/topjava/rest/meals/{{meal_id}}",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals",
						"{{meal_id}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "updateMealById",
			"request": {
				"method": "PUT",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"dateTime\":\"2019-03-16T13:56:39.492\",\r\n    \"description\":\"updated\",\r\n    \"calories\":100\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "localhost:8080/topjava/rest/meals/{{meal_id}}",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals",
						"{{meal_id}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "getMealsBetweenTimeAndDate",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8080/topjava/rest/meals?startDate=2016-03-16&startTime=00:00:00.000&endDate=2025-03-16&endTime=23:55:55.000",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"topjava",
						"rest",
						"meals"
					],
					"query": [
						{
							"key": "startDate",
							"value": "2016-03-16"
						},
						{
							"key": "startTime",
							"value": "00:00:00.000"
						},
						{
							"key": "endDate",
							"value": "2025-03-16"
						},
						{
							"key": "endTime",
							"value": "23:55:55.000"
						}
					]
				}
			},
			"response": []
		}
	],
	"protocolProfileBehavior": {}
}