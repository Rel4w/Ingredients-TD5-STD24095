# DefaultApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**dishesGet**](DefaultApi.md#dishesGet) | **GET** /dishes | Retourne la liste de tous les plats |
| [**dishesIdGet**](DefaultApi.md#dishesIdGet) | **GET** /dishes/{id} | Retourne un plat par son identifiant |
| [**dishesIdIngredientsGet**](DefaultApi.md#dishesIdIngredientsGet) | **GET** /dishes/{id}/ingredients | Retourne la liste des ingrédients composant un plat avec filtres optionnels |
| [**dishesIdIngredientsPut**](DefaultApi.md#dishesIdIngredientsPut) | **PUT** /dishes/{id}/ingredients | Met à jour la liste des ingrédients d&#39;un plat |
| [**ingredientsGet**](DefaultApi.md#ingredientsGet) | **GET** /ingredients | Retourne la liste de tous les ingrédients |
| [**ingredientsIdGet**](DefaultApi.md#ingredientsIdGet) | **GET** /ingredients/{id} | Retourne un ingrédient par son identifiant |
| [**ingredientsIdStockGet**](DefaultApi.md#ingredientsIdStockGet) | **GET** /ingredients/{id}/stock | Retourne la valeur du stock d&#39;un ingrédient à un instant donné |


<a id="dishesGet"></a>
# **dishesGet**
> List&lt;Dish&gt; dishesGet()

Retourne la liste de tous les plats

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<Dish> result = apiInstance.dishesGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#dishesGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Dish&gt;**](Dish.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des plats récupérée avec succès |  -  |

<a id="dishesIdGet"></a>
# **dishesIdGet**
> Dish dishesIdGet(id)

Retourne un plat par son identifiant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | 
    try {
      Dish result = apiInstance.dishesIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#dishesIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**|  | |

### Return type

[**Dish**](Dish.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Plat trouvé |  -  |
| **404** | Plat non trouvé |  -  |

<a id="dishesIdIngredientsGet"></a>
# **dishesIdIngredientsGet**
> List&lt;Ingredient&gt; dishesIdIngredientsGet(id, ingredientName, ingredientPriceAround)

Retourne la liste des ingrédients composant un plat avec filtres optionnels

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | 
    String ingredientName = "tomate"; // String | Filtre les ingrédients dont le nom contient cette valeur (insensible à la casse)
    Double ingredientPriceAround = 1200D; // Double | Filtre les ingrédients dont le prix est dans un intervalle de ±50 autour de cette valeur
    try {
      List<Ingredient> result = apiInstance.dishesIdIngredientsGet(id, ingredientName, ingredientPriceAround);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#dishesIdIngredientsGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**|  | |
| **ingredientName** | **String**| Filtre les ingrédients dont le nom contient cette valeur (insensible à la casse) | [optional] |
| **ingredientPriceAround** | **Double**| Filtre les ingrédients dont le prix est dans un intervalle de ±50 autour de cette valeur | [optional] |

### Return type

[**List&lt;Ingredient&gt;**](Ingredient.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients du plat récupérée avec succès |  -  |
| **404** | Plat non trouvé |  -  |

<a id="dishesIdIngredientsPut"></a>
# **dishesIdIngredientsPut**
> String dishesIdIngredientsPut(id, ingredientInput)

Met à jour la liste des ingrédients d&#39;un plat

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | 
    List<IngredientInput> ingredientInput = Arrays.asList(); // List<IngredientInput> | 
    try {
      String result = apiInstance.dishesIdIngredientsPut(id, ingredientInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#dishesIdIngredientsPut");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**|  | |
| **ingredientInput** | [**List&lt;IngredientInput&gt;**](IngredientInput.md)|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ingrédients mis à jour avec succès |  -  |
| **400** | Corps de requête manquant ou vide |  -  |
| **404** | Plat non trouvé |  -  |

<a id="ingredientsGet"></a>
# **ingredientsGet**
> List&lt;Ingredient&gt; ingredientsGet()

Retourne la liste de tous les ingrédients

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<Ingredient> result = apiInstance.ingredientsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#ingredientsGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Ingredient&gt;**](Ingredient.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients récupérée avec succès |  -  |

<a id="ingredientsIdGet"></a>
# **ingredientsIdGet**
> Ingredient ingredientsIdGet(id)

Retourne un ingrédient par son identifiant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | 
    try {
      Ingredient result = apiInstance.ingredientsIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#ingredientsIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**|  | |

### Return type

[**Ingredient**](Ingredient.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ingrédient trouvé |  -  |
| **404** | Ingrédient non trouvé |  -  |

<a id="ingredientsIdStockGet"></a>
# **ingredientsIdStockGet**
> StockValue ingredientsIdStockGet(id, at, unit)

Retourne la valeur du stock d&#39;un ingrédient à un instant donné

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | 
    OffsetDateTime at = OffsetDateTime.parse("2024-01-01T00:00Z"); // OffsetDateTime | 
    String unit = "KG"; // String | 
    try {
      StockValue result = apiInstance.ingredientsIdStockGet(id, at, unit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#ingredientsIdStockGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**|  | |
| **at** | **OffsetDateTime**|  | |
| **unit** | **String**|  | [enum: KG, L, PIECE, UNIT] |

### Return type

[**StockValue**](StockValue.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Valeur du stock récupérée avec succès |  -  |
| **400** | Paramètre at ou unit manquant |  -  |
| **404** | Ingrédient non trouvé |  -  |

